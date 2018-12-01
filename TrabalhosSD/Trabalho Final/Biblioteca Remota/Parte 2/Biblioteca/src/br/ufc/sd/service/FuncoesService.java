package br.ufc.sd.service;

import java.util.List;

import br.ufc.sd.model.Livro;
import br.ufc.sd.model.Pessoa;
import br.ufc.sd.repository.FuncoesBiblioteca;

public class FuncoesService implements FuncoesBiblioteca {
	
	@Override
	public Livro emprestar(Livro livro, Pessoa pessoa) {
		if(livro.getStatus().equals("disponivel") && !pessoa.getLivros().contains(livro)) {
			
			livro.setStatus("emprestado");
			livro.setCliente(pessoa);

			List<Livro> livros = pessoa.getLivros();
			livros.add(livro);
			
			pessoa.setLivros(livros);
			
			return livro;
		}
		return null;
	}

	@Override
	public Livro reservar(Livro livro, Pessoa pessoa) {

		if(!pessoa.getLivros().contains(livro)) {
			if(livro.getStatus().equals("emprestado") || livro.getStatus().equals("reservado")) {
				Livro reservado = new Livro(livro.getIsbn(), livro.getTitulo(), livro.getAutor(), livro.getEditora(), 
						livro.getAnoPublicacao(), livro.getEdicao(), "reservado");
				reservado.setCliente(pessoa);
				return reservado;			
			}
		}
		return null;
	}

	@Override
	public Livro devolver(Livro livro, Pessoa pessoa) {
		
		if(livro.getStatus().equals("emprestado") && livro.getCliente().equals(pessoa)) {
			
			List<Livro> livros = pessoa.getLivros();
			livros.remove(livro);
			
			
			pessoa.setLivros(livros);
			
			livro.setStatus("disponivel");
			livro.setCliente(null);
			
			return livro;
			
		}
		
		return null;
	}

}
