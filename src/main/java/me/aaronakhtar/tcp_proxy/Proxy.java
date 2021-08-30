package me.aaronakhtar.tcp_proxy;

import me.aaronakhtar.tcp_proxy.threads.ProxyServer;
import me.aaronakhtar.tcp_proxy.threads.handlers.ProxyClientHandler;

import java.net.InetAddress;
import java.util.Date;

public class Proxy {
    public static String target_host;
    public static int target_port;

    public static void main(String[] args) throws InterruptedException{

        if (args.length != 3){
            System.out.println("Params: java -jar proxy.jar <tcp_server_host> <tcp_server_port> <proxy_port>");
            return;
        }

        target_host = args[0];
        target_port = Integer.parseInt(args[1]);
        final int proxy_port = Integer.parseInt(args[2]);

        new ProxyServer(proxy_port).start();

        String lc = "N/A";
        while(true){
            try {
                if (ProxyClientHandler.connectionLogs.size() > 10){
                    ProxyClientHandler.connectionLogs.clear();
                    lc = ProxyClientHandler.sdf.format(new Date());
                }
                System.out.println(Utilities.Colour.CLEAR.get());
                System.out.println();

                System.out.println(Utilities.Colour.BRIGHT_MAGENTA.get() + "  [Aaron Akhtar - " + Utilities.Colour.CYAN.get() + "iProxy" + Utilities.Colour.BRIGHT_MAGENTA.get() + "] [" + Utilities.Colour.BLUE.get() + ProxyClientHandler.sdf.format(new Date()) + Utilities.Colour.BRIGHT_MAGENTA.get() + "]");
                System.out.println(Utilities.Colour.BRIGHT_MAGENTA.get() + "     [connected_clients=" + Utilities.Colour.BLUE.get() + ProxyClientHandler.totalCons + Utilities.Colour.BRIGHT_MAGENTA.get() + "] [running_threads=" + Utilities.Colour.BLUE.get() + Thread.activeCount() + Utilities.Colour.BRIGHT_MAGENTA.get() + "]");
                System.out.println();
                System.out.println(Utilities.Colour.BRIGHT_MAGENTA.get() + "  [target=" + Utilities.Colour.CYAN.get() + target_host + ":" + target_port + Utilities.Colour.BRIGHT_MAGENTA.get() + "]");
                System.out.println(Utilities.Colour.BRIGHT_MAGENTA.get() + "  [proxy_info=" + Utilities.Colour.CYAN.get() + InetAddress.getLocalHost().getHostAddress() + ":" + proxy_port + Utilities.Colour.BRIGHT_MAGENTA.get() + "]");
                System.out.println();
                System.out.println();
                System.out.println(Utilities.Colour.BRIGHT_MAGENTA.get() + " [access_logs_" + Utilities.Colour.BLUE.get() + "(cached)" + Utilities.Colour.BRIGHT_MAGENTA.get() + "] (last_cleared=" + Utilities.Colour.BLUE.get() + lc + Utilities.Colour.BRIGHT_MAGENTA.get() + ")");
                System.out.println();
                for (String s : ProxyClientHandler.connectionLogs) {
                    System.out.println(Utilities.Colour.WHITE.get() + s);
                }
                Thread.sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
        }





    }


}
