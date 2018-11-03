package UDP;

import java.net.*;
import java.io.*;
public class UDPClient{
    public static void main(String args[]){
        // args give message contents and destination hostname
        DatagramSocket aSocket = null;
        try {
            String s = "Hello World";
            aSocket = new DatagramSocket();
            byte [] m = s.getBytes();
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6788;
            DatagramPacket request = new DatagramPacket(m,  s.length(), aHost, serverPort);
            aSocket.send(request);
            byte[] buffer = new byte[11];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            System.out.println("Reply: " + new String(reply.getData()));
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
}