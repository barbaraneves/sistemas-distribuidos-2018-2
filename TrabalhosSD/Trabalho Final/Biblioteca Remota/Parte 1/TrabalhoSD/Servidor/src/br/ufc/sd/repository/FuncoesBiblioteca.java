package br.ufc.sd.repository;

import model.Livro;
import model.Pessoa;

public interface FuncoesBiblioteca {
	
	public Livro emprestar(Livro livro, Pessoa pessoa);
	
	public Livro reservar(Livro livro, Pessoa pessoa);
		
	public Livro devolver(Livro livro, Pessoa pessoa);
	
}
