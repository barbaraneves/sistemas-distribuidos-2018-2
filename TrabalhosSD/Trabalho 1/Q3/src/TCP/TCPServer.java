package TCP;

import Connection.Connection;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TCPServer {
    public static void main (String args[]) {

        try{
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("O servidor está pronto!");

            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
                c.start();
            }
        } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
}

