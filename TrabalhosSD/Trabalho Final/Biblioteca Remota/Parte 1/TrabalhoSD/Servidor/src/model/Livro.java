package model;

public class Livro {
	private String isbn;
	private String titulo;
	private String autor;
	private String editora;
	private int anoPublicacao;
	private int edicao;
	private String status;
	private Pessoa cliente; 
	
	public Livro(String isbn, String titulo, String autor, String editora, int anoPublicacao, int edicao, String status) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.editora = editora;
		this.anoPublicacao = anoPublicacao;
		this.edicao = edicao;
		this.status = status;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public String getEditora() {
		return editora;
	}


	public void setEditora(String editora) {
		this.editora = editora;
	}


	public int getAnoPublicacao() {
		return anoPublicacao;
	}


	public void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}


	public int getEdicao() {
		return edicao;
	}


	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}

	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public Pessoa getCliente() {
		return cliente;
	}


	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}


	@Override
	public String toString() {
		return "Livro: Titulo= " + titulo + ", Autor= " + autor + ", Editora= " + editora
				+ ", Ano de Publicação= " + anoPublicacao + ", Edição=" + edicao + ", Status= " + status + "]";
	}


	
	
	
	
	
	

}
