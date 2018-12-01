package br.ufc.sd.repository;

import br.ufc.sd.model.Livro;
import br.ufc.sd.model.Pessoa;

public interface FuncoesBiblioteca {
	
	public Livro emprestar(Livro livro, Pessoa pessoa);
	
	public Livro reservar(Livro livro, Pessoa pessoa);
		
	public Livro devolver(Livro livro, Pessoa pessoa);
	

}
