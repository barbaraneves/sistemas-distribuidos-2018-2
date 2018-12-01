package com.representacao;

import java.io.*;
import java.net.*;
import java.lang.reflect.*;
import com.google.protobuf.ByteString;
import com.representacao.Person.Modelo;
import com.representacao.Person.Pessoa;

public class ServidorTCP {
	static DataOutputStream outToClient = null;
	static DataInputStream in = null;
	static Modelo.Builder modelo = Modelo.newBuilder();
	static byte[] resultado = null;
	static Modelo requisicao = null;

	public static byte[] getRequest() throws IOException{
		Pessoa pessoa;
		ServerSocket welcomeSocket = new ServerSocket(6788);  

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			in = new DataInputStream(connectionSocket.getInputStream());
			while (in.available() == 0){

			}

			byte[] fluxobytes = new byte[in.available()];
			in.read(fluxobytes);
			requisicao = Modelo.parseFrom(fluxobytes);

			byte [] temp = requisicao.getArguments().toByteArray();
			pessoa = Pessoa.parseFrom(temp);

			String objeto = requisicao.getObjectReference();
			String nomeMetodo = requisicao.getMethodId();

			try {
				Class argumento = Pessoa.class;
				Class classe = Class.forName("com.representacao." + objeto);
				Object objet = classe.newInstance();
				Method meth = classe.getMethod(nomeMetodo,argumento); 

				Object retobj = meth.invoke(objet, pessoa);
				resultado = (byte[]) retobj;

			} catch (Throwable e) {
				System.err.println(e);
			}

			sendReply(resultado);
		}

	}
	public static void sendReply (byte[] reply) throws IOException{
		ByteString bs = ByteString.copyFrom(reply);
		modelo.setArguments(bs);
		modelo.setMessageType(1);
		modelo.setRequestId(requisicao.getRequestId());
		modelo.setObjectReference("Cliente");
		modelo.setMethodId("doOperation");
		outToClient.write(modelo.build().toByteArray());
	}
	public static void main(String[] args) throws IOException {
		getRequest();
	}
}
