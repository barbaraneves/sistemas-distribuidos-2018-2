package br.ufc.sd.controller;

import java.util.ArrayList;
import java.util.List;

import br.ufc.sd.model.Livro;
import br.ufc.sd.model.Pessoa;
import br.ufc.sd.service.FuncoesService;

public class Biblioteca {
	
	private List<Livro> livros;
	private List<Livro> livros_reservados;
	private FuncoesService funcoes;
	
	public Biblioteca() {
		inicializarBiblioteca();
		this.livros_reservados = new ArrayList<Livro>();
		this.funcoes = new FuncoesService();
	}
	

	public boolean adicionarLivro(Livro livro) {
		if(!livros.contains(livro)) {
			livros.add(livro);
			return true;
		}
		
		return false;
	}
	
	public String emprestrarLivro(Livro livro, Pessoa cliente) {
		
		Livro emprestado = funcoes.emprestar(livro, cliente);
		
		if( emprestado == null) {
			return "Livro indisponível no momento!!!";
		}

		livros.set(livros.indexOf(livro), emprestado);
		return "Livro emprestado!";
	}
	
	
	public String reservarLivro(Livro livro, Pessoa cliente) {
		
		Livro reservado = funcoes.reservar(livro, cliente);
		if( reservado == null) {
			if(livro.getStatus().equals("disponivel") && !cliente.getLivros().contains(livro)) {
				return "Não foi possivel fazer a reserva, pois o livro está disponível!!!";
			}else if(livro.getStatus().equals("emprestado") && cliente.getLivros().contains(livro)) {
				return "Não foi possivel fazer a reserva, pois você já pegou esse livro!!!";
			}else {
				return "Não foi possivel realizar a reserva!!!";
			}
		}else {
			livros_reservados.add(reservado);
			return "Livro reservado!";
		}
		
		
		
	}
	
	public String devolverLivro(Livro livro, Pessoa cliente){
		
		Livro devolver = null;
		
		if(livros.contains(livro)) {
			for (Livro l : livros) {
				if(l.equals(livro)) {
					devolver = funcoes.devolver(livro, cliente);
					if(devolver != null) {
						boolean flag = false;
						for (Livro livro2 : livros_reservados) {
							if(livro2.getTitulo().equals(devolver.getTitulo()) && 
									livro2.getIsbn().equals(devolver.getIsbn()) &&
									livro2.getCliente().equals(cliente)) {
								flag = true;
								break;
							}
						}
						
						if(flag) {
							int indice = livros_reservados.indexOf(devolver);
							devolver.setStatus("reservado");
							devolver.setCliente(livros_reservados.get(indice).getCliente());
							livros_reservados.remove(devolver);
							livros.set(livros.indexOf(livro), devolver);
							return "Livro devolvido!";
						}else {
							livros.set(livros.indexOf(livro), devolver);							
							return "Livro devolvido!";
						}
					}
				}
			}
		}
		return "Este livro não pertence a biblioteca";
	}
	
	
	public String buscarLivro(String titulo, String isbn) {
		Livro livro = null;
		for (Livro l : livros) {
			if(l.getTitulo().equals(titulo) && l.getIsbn().equals(isbn)) {
				livro = l ;
			}
		}
		
		if(livro != null) {
			return "O livro " + livro.getTitulo() + " pertence a nossa biblioteca";
		}
		
		return "Este livro não pertence a nossa biblioteca";
		
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
	
	public String listarLivros() {
		String livros = "";
		for (Livro livro : this.livros) {
			livros += livro.toString();
		}
		
		return livros;
	}
	
	
	private void inicializarBiblioteca() {
		
		livros = new ArrayList<Livro>();

		Livro l1 = new Livro("123br", "Origem", "Dan Brown", "Arqueiro", 2017, 1, "disponivel");	
		Livro l2 = new Livro("123bruau", "Contato", "Carl Sagan", "Companhia de Bolso", 1985, 1, "disponivel");
		Livro l3 = new Livro("123bmnbmj", "O Demônio e a Stra. Prym", "Paulo Coelho", "Objetiva", 2000, 1, "disponivel");
		Livro l4 = new Livro("12rersf3br", "Introdução à Teoria da Computação", "Michael Sipser", "CENGAGE", 1996, 2, "disponivel");
		Livro l5 = new Livro("134uio23br", "Os Contos de Beedle, o Bardo", "J. K. Rownling", "Rocco", 2007, 3, "disponivel");
		
		
		livros.add(l1);
		livros.add(l2);
		livros.add(l3);
		livros.add(l4);
		livros.add(l5);
		
	}

	public Livro getLivro(String titulo, String isbn) {
		for (Livro livro : livros) {
			if(livro.getTitulo().equals(titulo) && livro.getIsbn().equals(isbn)) {
				return livro;
			}
		}
		return null;
	}
	

}
