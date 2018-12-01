package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pessoa {
	private Long id;
	private String login;
	private String senha;
	private String nome;
	private String cpf;
	private String endereco;
	private Date dataNascimento;
	List<Livro> livros;
	
	

	public Pessoa(Long id, String nome, String login, String senha, String cpf, String endereco, Date dataNascimento) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.dataNascimento = dataNascimento;
		this.livros = new ArrayList<Livro>();
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Livro> getLivros() {
		return livros;
	}
	
	public void setLivros(List<Livro> livros2) {
		this.livros = livros2;
	}
	
	
	@Override
	public String toString() {
		return "Pessoa [Nome= " + nome + ", CPF= " + cpf + ", Endereço= " + endereco + ", Data de Nascimento= "
				+ dataNascimento + "]";
	}

}
