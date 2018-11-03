package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPClient{
    public static void main(String args[]){
        // args give message contents and destination hostname
        DatagramSocket aSocket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Qual calculadora deseja utilizar? \n \n" +
                    "0 - Calculadora de operações artiméticas. \n" +
                    "1 - Calculadora fatorial. \n" +
                    "2 - Sair.");
            String s = scanner.nextLine();
            String equacao ="";

            if(s.equals("0")){
                System.out.println("\n-- Calculadora Aritmética -- \n" +
                        "Por favor, insira 'número operação número'.\n" +
                        "Ex: -25 * 44" );
                equacao = scanner.nextLine();
            }else if(s.equals("1")){
                System.out.println("\n-- Calculadora Fatorial -- \n" +
                        "Por favor, insira o numero que voçê deseja calcular o fatorial.\n" +
                        "Ex: 25");
                equacao = scanner.nextLine();
            }else{
                System.out.println("\nPrograma terminado!");
                return;
            }

            s += " " + equacao;

            aSocket = new DatagramSocket();
            byte [] m = s.getBytes();
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6788;
            DatagramPacket request = new DatagramPacket(m,  s.length(), aHost, serverPort);
            aSocket.send(request);
            byte[] buffer = new byte[s.length() * 2];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);

            String [] resposta = new String(reply.getData()).split("\\$");

            System.out.println("\nResposta: " + resposta[0] + ".");
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
}
