package JavaPractice;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ForwardServer extends Thread {
    Socket socket;
//    Queue<String> messageQueue = new LinkedList<>();
    Queue<String> inQueue; // message from client
    Queue<String> outQueue; // message to client

    public ForwardServer(Socket s, Queue<String> queueA, Queue<String> queueB) {
        inQueue = queueA;
        outQueue = queueB;
        this.socket = s;
    }

    @Override
    public void run() {
        System.out.println("Forward Sever is Running: " + socket);
        try {
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true); // autoFlush set to true
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                System.out.println("Waiting for incoming message: ");
                // OutputStream outputStream = socket.getOutputStream();
                String incomingMessage = br.readLine();
                this.inQueue.offer(incomingMessage);
                while (!this.outQueue.isEmpty()) {
                    String outgoingMessage = this.outQueue.poll();
                    pw.println(outgoingMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
