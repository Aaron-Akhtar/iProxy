package me.aaronakhtar.tcp_proxy.threads.handlers;

import me.aaronakhtar.tcp_proxy.Proxy;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProxyClientHandler implements Runnable {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    public static final List<String> connectionLogs = new ArrayList<>();

    public static volatile int totalCons = 0;
    private volatile boolean running = true;

    private Socket socket;
    public ProxyClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean isF = false;
        try(Socket socket = this.socket;
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){

            try(Socket target = new Socket(Proxy.target_host, Proxy.target_port);
                BufferedWriter tWr = new BufferedWriter(new OutputStreamWriter(target.getOutputStream()));
                InputStream tIn = target.getInputStream()){

                socket.setSoTimeout(15000);
                target.setSoTimeout(15000);


                connectionLogs.add("["+ sdf.format(new Date()) + "] New Proxy Client Established: @" + socket.getInetAddress().getHostAddress() + ":"+socket.getPort()+"  -> [@"+target.getInetAddress().getHostAddress()+"]");
                totalCons++;
                isF = true;
                //start reading input concurrently in new thread.
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            while (running) {
                                try {
                                    String f;
                                    while ((f = clientReader.readLine()) != null) {
                                        tWr.write(f + "\n");
                                        tWr.flush();
                                    }
                                    if (clientReader.read() == -1) break;
                                }catch (SocketTimeoutException socketTimeoutException){
                                    if (!running) break;
                                }
                            }
                        } catch (Exception e){
                        }
                        if (running) running = false;
                    }
                }.start();

                String f;
                while(running) {
                    try {
                        final byte[] targetArray = new byte[tIn.available()];
                        int x = tIn.read(targetArray);
                        if (x == -1) break;
                        f = new String(targetArray);
                        if (!f.isEmpty()) {
                            clientWriter.write(f);
                            clientWriter.flush();
                            continue;
                        }
                        //Thread.sleep(20);
                    }catch (SocketTimeoutException timeoutException){
                        if (!running) break;
                    }
                }

            }

        }catch (Exception e){
        }
        if (running) running = false;
        if (isF) totalCons--;
        connectionLogs.add("["+ sdf.format(new Date()) + "] Proxy Client Disconnected: @" + socket.getInetAddress().getHostAddress() + " -> [@"+Proxy.target_host+"]");


    }
}

