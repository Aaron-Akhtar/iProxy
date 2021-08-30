package me.aaronakhtar.tcp_proxy.threads;

import me.aaronakhtar.tcp_proxy.threads.handlers.ProxyClientHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer extends Thread {

    private int port;

    public ProxyServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(this.port)){

            while(true){
                final Socket socket = serverSocket.accept();
                new Thread(new ProxyClientHandler(socket)).start();
                Thread.sleep(50);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
