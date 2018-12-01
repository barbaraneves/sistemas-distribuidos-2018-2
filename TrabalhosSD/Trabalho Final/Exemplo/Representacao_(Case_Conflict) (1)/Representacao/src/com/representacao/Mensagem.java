package com.representacao;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.representacao.Person.Modelo;
import com.representacao.Person.Pessoa;
import com.representacao.Person.Resultado;

public class Mensagem {
	public static int menssagemId = 0;
	public byte[] doOperation (String methodId, byte[] arguments) throws Exception{
		Modelo.Builder modelo = Modelo.newBuilder();
		ClienteTCP cliente = new ClienteTCP();

		modelo.setRequestId(menssagemId++);
		modelo.setObjectReference("AgendaRemota");
		modelo.setMethodId(methodId);
		modelo.setMessageType(0);
		ByteString bs = ByteString.copyFrom(arguments);
		modelo.setArguments(bs);

		Modelo mod = modelo.build();
		cliente.mandaMensagem(mod.toByteArray());	

		Modelo resposta = dESserializaMensagemDeResposta(cliente.recebeResposta());
		if ( resposta.getRequestId() == menssagemId-1 &&
				resposta.getObjectReference().equals("Cliente")) {
			return resposta.getArguments().toByteArray();
		} 
		else //seria o caso de reenviar?
			return null;

	}
	public static Modelo dESserializaMensagemDeResposta(byte[] mensagem) throws InvalidProtocolBufferException{
		Modelo resposta = null;
		resposta = Modelo.parseFrom(mensagem);
		return resposta;
	}
	void ImprimeMensagemDeResposta(byte[] mensagem) throws InvalidProtocolBufferException{
		Resultado resultado = null;
		resultado = Resultado.parseFrom(mensagem);
		System.out.println("FROM SERVER: "+resultado.getResultado());

	}
	void ImprimeObjetoDeResposta(byte[] pessoa) throws InvalidProtocolBufferException{
		Pessoa person = null;
		person = Pessoa.parseFrom(pessoa);
		System.out.println("FROM SERVER: ");
		System.out.println(person.getNome());
		System.out.println(person.getEndereco());
		System.out.println(person.getNumeroDeTelefone());
	}
}
