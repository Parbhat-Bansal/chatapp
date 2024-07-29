package JavaPractice;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
    public static void main(String[] args) {
        boolean sw = false ; 
        int port = 8008;  // Define the port number
        Queue<String> queueA = new LinkedList<>();
        Queue<String> queueB = new LinkedList<>();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started.....");
            while (true) {
                try {
                    System.out.println("Waiting for clients...");
                    Socket socket = serverSocket.accept();
                    sw = !sw ;
                    ForwardServer fs;
                    if (sw) {
                        fs = new ForwardServer(socket, queueA,queueB);
                    } else {
                        fs = new ForwardServer(socket, queueB,queueA);
                    }
                    fs.start();
                    System.out.println("Client attached");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

