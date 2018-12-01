package com.representacao;

import java.io.IOException;
import com.representacao.Person.Pessoa;
import com.representacao.Person.Resultado;

public class AgendaRemota {
	static Pessoa[] person = new Pessoa[20];

	public byte[] adicionar(Pessoa pessoa) throws IOException{
		Resultado.Builder result = Resultado.newBuilder();


		for(int x = 0; x < person.length; x++){
			if(person[x] == null){
				person[x] = pessoa;
				break;
			}
		}
		result.setResultado("Adicionado");
		return result.build().toByteArray();
	}

	public byte[] deletar(Pessoa pessoa) throws IOException{
		Resultado.Builder resultado = Resultado.newBuilder();
		for(int x = 0; x < person.length; x++){
			if(pessoa.getNome().equalsIgnoreCase(person[x].getNome())){
				person[x] = null;
				break;
			}
		}
		resultado.setResultado("Deletado");
		return resultado.build().toByteArray();
	}
	
	public byte[] listar(Pessoa pessoa) throws IOException{
		Pessoa.Builder people = Pessoa.newBuilder();			
		for(int x = 0; x < person.length; x++){
			if(person[x].getNome().equalsIgnoreCase(pessoa.getNome())){
				people.setNome(person[x].getNome());
				people.setEndereco(person[x].getEndereco());
				people.setNumeroDeTelefone(person[x].getNumeroDeTelefone());
				break;
			}
		}
		return people.build().toByteArray();
	}
}

