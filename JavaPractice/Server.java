package JavaPractice;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 8008;  // Define the port number

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    EchoServer es = new EchoServer(socket);
                    es.start();  
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
