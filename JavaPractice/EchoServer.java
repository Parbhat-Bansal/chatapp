package JavaPractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class EchoServer extends Thread {
    Socket socket;
    Queue<String> messageQueue = new LinkedList<>();

    public EchoServer(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        System.out.println("Echo server run for socket" + socket);
        try (OutputStream os = socket.getOutputStream();
             PrintWriter pw = new PrintWriter(os, true); // autoFlush set to true
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                System.out.println("Waiting for incoming message");
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("read message: " + line);
                messageQueue.add(line) ;
                System.out.println("Queued element is : " +messageQueue.poll());
                pw.println(line);
            }
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
