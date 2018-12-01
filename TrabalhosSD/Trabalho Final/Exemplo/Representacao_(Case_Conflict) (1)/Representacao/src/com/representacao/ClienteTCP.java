package com.representacao;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteTCP {
    Socket cs = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    public ClienteTCP(){
        try {
            cs = new Socket("localhost", 6788);
        } catch (UnknownHostException ex) {
            System.out.println("Endereco nao encontrado.");
        } catch (IOException ex) {
            System.out.println("--Erro na criação do socket (Conexão).\n--Certifique-se que o servidor está rodando.");
        }

        try {
            in = new DataInputStream(cs.getInputStream());
        } catch (IOException ex) {
            System.out.println("Erro na criação do InputStream");
        }
        
        try {
            out = new DataOutputStream(cs.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Erro na criação do OutputStream");
        }
    }

    void mandaMensagem(byte[] mArray) {
        try {
            out.write(mArray);
        } catch (IOException ex) {
            System.out.println("Erro no out.write().");
        }
    }
    byte[] recebeResposta(){
        int a = 0;
        do{
            try {
                a = in.available();
            } catch (IOException ex) {
                System.out.println("Erro no in.available().");
            }
        }while(a<=0);
        byte[] b = new byte[a];
        try {
            in.read(b);
        } catch (IOException ex) {
            System.out.println("Erro no in.read(b).");
        }
        return b;
    }

    public void encerra(){
        try {
            cs.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        in = null;
        out = null;
        cs = null;
    }

}
