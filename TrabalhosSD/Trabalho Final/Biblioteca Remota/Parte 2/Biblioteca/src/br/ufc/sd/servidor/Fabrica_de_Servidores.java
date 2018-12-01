package br.ufc.sd.servidor;

import java.io.IOException;
import java.net.ServerSocket;

public class Fabrica_de_Servidores {
	
	private ServerSocket server;
	public ServerSocket novaConexao(){

		try {
			//CRIA UM SERVIDOR NA PORTA 4444
			server = new ServerSocket(4444);
			System.out.println("Servidor rodando...");
		
		} catch (IOException e) {
		e.printStackTrace();
	}
		return server;
	}


}
