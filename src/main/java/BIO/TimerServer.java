package BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimerServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {

            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //采用默认值
            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);    //启动一个客户端
            System.out.println("The time is start in port : " + port);
            Socket socket = null;

            //循环监听
            while (true) {
                socket = server.accept();   //接受客户端连接
                new Thread(new TimerServerHandler(socket)).start(); //启动一个线程处理客户端请求
            }
        } finally {     //处理资源

            if (server != null) {
                System.out.println("The time server close!");
                server.close();
                server = null;
            }
        }
    }
}
