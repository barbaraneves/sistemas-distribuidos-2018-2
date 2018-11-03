package TCP;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {
    public static void main (String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        try{

            boolean continuar = true;
            Scanner scanner = new Scanner(System.in);
            String contato = "Servidor: ";
            int serverPort = 7896;

            System.out.println("O servidor está pronto! Insira suas mensagens, ou escreva '!!' para sair.");

            do{
                s = new Socket("localhost", serverPort);
                DataInputStream in = new DataInputStream( s.getInputStream());
                DataOutputStream out = new DataOutputStream( s.getOutputStream());

                String string = scanner.nextLine();
                if(string.equals("!!")){
                    break;
                }

                out.writeUTF(string);
                System.out.println(contato + in.readUTF());

            }while (continuar);

        }catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        }catch (IOException e){System.out.println("readline:"+e.getMessage());
        }finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
    }
}