package JavaPractice;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        String host = "localhost"; // Define the host
        int port = 8008; // Define the port number

        Scanner sc = new Scanner(System.in);
        try (Socket socket = new Socket(host, port);
             OutputStream os = socket.getOutputStream();
             PrintWriter pw = new PrintWriter(os, true); // autoFlush set to true
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            boolean readingServerMessage = true;

            while (readingServerMessage) {
                // Check for incoming messages
                while (bufferedReader.ready() ) {
                    String serverMessage = bufferedReader.readLine(); // Read the message from the server
                    if (serverMessage != null) {
                        System.out.println("Incoming Message: " + serverMessage); // Print the server message
                    } else {
                        readingServerMessage = false; // Exit loop if server has closed the connection
                        break;
                    }
                }
                if (System.in.available() > 0) {
                    String input = sc.nextLine();
                    if (input.equalsIgnoreCase("QUIT")) {
                        readingServerMessage = false; // Exit the loop and close the connection
                    } else {
                        System.out.println("Sending message: " + input);
                        pw.println(input); // Send the message to the server
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

}
