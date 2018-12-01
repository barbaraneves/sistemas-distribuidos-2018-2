package com.representacao;

import com.representacao.Person.Pessoa;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class FuncoesDaAgenda {  
	static Pessoa paraCompletar;
	BufferedReader stdin = 
		new BufferedReader(new InputStreamReader(System.in)); 
	PrintStream stdout = System.out;	
	public Mensagem mensagem = new Mensagem();
	
	public void adicionar() throws Exception{
		Pessoa.Builder pessoa = Pessoa.newBuilder();

		stdout.print("Nome: ");
		pessoa.setNome(stdin.readLine());

		stdout.print("Endereco: ");
		pessoa.setEndereco(stdin.readLine());

		stdout.print("Telefone: ");
		pessoa.setNumeroDeTelefone(stdin.readLine());
		paraCompletar = pessoa.build();
		byte[] mensage = mensagem.doOperation("adicionar", pessoa.build().toByteArray());
		mensagem.ImprimeMensagemDeResposta(mensage);
		
	}

	public void listar() throws Exception{
		Pessoa.Builder pessoa = Pessoa.newBuilder();

		stdout.print("Nome: ");
		pessoa.setNome(stdin.readLine());
		pessoa.setEndereco("listar");
		pessoa.setNumeroDeTelefone("listar");
		
		byte[] mensage = mensagem.doOperation("listar", pessoa.build().toByteArray());
		mensagem.ImprimeObjetoDeResposta(mensage);
	}
	public void deletar() throws Exception{
		Pessoa.Builder pessoa = Pessoa.newBuilder();
		
		stdout.print("Nome: ");
		pessoa.setNome(stdin.readLine());

		stdout.print("Endereco: ");
		pessoa.setEndereco(stdin.readLine());

		stdout.print("Telefone: ");
		pessoa.setNumeroDeTelefone(stdin.readLine());
		byte[] mensage = mensagem.doOperation("deletar", pessoa.build().toByteArray());
		mensagem.ImprimeMensagemDeResposta(mensage);
	}
}

