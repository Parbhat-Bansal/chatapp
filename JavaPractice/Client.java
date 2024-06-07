package JavaPractice;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Client {
    static Queue<String> messgaequeue1 = new LinkedList<>() ;
    public static void main(String[] args) {
        String host = "localhost"; // Define the host
        int port = 8008 ;// Define the port number

        Scanner sc = new Scanner(System.in);
        try (Socket socket = new Socket(host, port);
        OutputStream os = socket.getOutputStream();
             PrintWriter pw = new PrintWriter(os, true); // autoFlush set to true
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (true) {
                String input = sc.nextLine();
                messgaequeue1.offer(input); 
                if (input.isEmpty() || input.equals("QUIT")) {
                    break;
                }
                System.out.println("Sending message: " + input);

                pw.println(input); // Send the message to the server

                String serverMessage = in.readLine(); // Read the message from the server
                System.out.println("Server: " + serverMessage); // Print the server message
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
