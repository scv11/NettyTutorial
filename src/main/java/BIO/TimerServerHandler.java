package BIO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimerServerHandler implements Runnable {

    private Socket socket;

    public TimerServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));   //获得来自客户端的输入流
            out = new PrintWriter(this.socket.getOutputStream(), true); //发送给客户端的输出流
            String currentTime = null;
            String body = null;
            while (true) {
                body = in.readLine();   //获取客户端的输入
                if (body == null) {
                    break;
                }
                System.out.println("The time server receive order : " + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString()
                        : "BAD ORDER";
                out.println(currentTime);
            }
        } catch (Exception e) { //出现异常后关闭资源
            if (in != null) {
                try {
                    in.close();
                } catch (Exception el) {
                    el.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (Exception el) {
                    el.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
