package JavaPractice;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;

public class ForwardServer extends Thread {
    private Socket socket;
    private Queue<String> inQueue; // message from client
    private Queue<String> outQueue; // message to client

    public ForwardServer(Socket s, Queue<String> queueA, Queue<String> queueB) {
        inQueue = queueA;
        outQueue = queueB;
        this.socket = s;
    }

    @Override
    public void run() {
        System.out.println("Forward Server is Running: " + socket);
        try {
//            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true); // autoFlush set to true
            InputStream Is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(Is));
            socket.setSoTimeout(1000); // Set timeout to 10 seconds
            System.out.println("Conversation Started ---> ");
            while (true) {
                try {
                    String incomingMessage = br.readLine();
                    if (incomingMessage == null) {
                        break;
                    }
                    this.inQueue.offer(incomingMessage);
                } catch (SocketTimeoutException e) {
                    if (socket.isClosed() || !socket.isConnected()) {
                        break;
                    }
                }
                while (!this.outQueue.isEmpty()) {
                    String outgoingMessage = this.outQueue.poll();
                    pw.println(outgoingMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
