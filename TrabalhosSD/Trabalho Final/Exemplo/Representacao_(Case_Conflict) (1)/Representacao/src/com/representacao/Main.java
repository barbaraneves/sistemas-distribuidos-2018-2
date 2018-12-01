package com.representacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws Exception {
		FuncoesDaAgenda invoca = new FuncoesDaAgenda();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); 
		int opcao;
		do{	
			System.out.println("1-adicionar\n2-listar\n3-deletar ");
			opcao = Integer.valueOf(stdin.readLine());
			switch(opcao){													

			case 1:
				invoca.adicionar();
				break;

			case 2:
				invoca.listar();
				break;

			case 3:
				invoca.deletar();
				break;
			}
		}while(opcao < 4);
		
	}

}
