package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer{
    public static void main(String args[]){
        DatagramSocket aSocket = null;
        try{
            System.out.println("O servidor está pronto!");
            aSocket = new DatagramSocket(6788);
            // create socket at agreed port
            byte[] buffer = new byte[1000];
            while(true){
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                String s = new String(request.getData(), 0, request.getLength());
                String [] as = s.split(" ");

                Double result = 0.;

                if(as[0].equals("0")){
                    if(as[2].equals("+")){
                        result =  Double.parseDouble(as[1]) + Double.parseDouble(as[3]);
                    }else if (as[2].equals("-")){
                        result =  Double.parseDouble(as[1]) - Double.parseDouble(as[3]);
                    }else if (as[2].equals("*")){
                        result =  Double.parseDouble(as[1]) * Double.parseDouble(as[3]);
                    }else{
                        result =  Double.parseDouble(as[1]) / Double.parseDouble(as[3]);
                    }
                }else if(as[0].equals("1")){
                    int numero = Integer.parseInt(as[1]);

                    for(int i = numero - 1; i > 0; i--){
                        numero *= i;
                    }

                    result = (double) numero;

                }else{
                    System.out.println("Programa terminado!");
                }
//                System.out.println(result);
                String respost = result.toString();
                respost += "$";
                byte[] resposta = respost.getBytes();

                request.setData(resposta, 0, resposta.length);

                DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
                aSocket.send(reply);
            }
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
}