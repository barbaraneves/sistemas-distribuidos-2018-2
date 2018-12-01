package br.ufc.sd.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.ufc.sd.controller.Biblioteca;
import br.ufc.sd.model.Livro;
import br.ufc.sd.model.Pessoa;


public class Servidor extends Thread{
	Socket cliente;
	private ServerSocket serverSocket;
	private Biblioteca biblioteca = new Biblioteca();
	private List<Pessoa> usuarios = new ArrayList<Pessoa>();
	private String separador;
	private String mensagem;
	private BufferedReader receber;
	private PrintStream enviar;
	
	public Servidor(ServerSocket serverSocket, Socket cSocket) {
		this.serverSocket = serverSocket;
		this.cliente= cSocket;
	}
	
	public void Iniciar_conexao(){
		while (true){ 
			try { //VEREFIQUE PARA SEMPRE SE ALGUEM ESTA DE CONECTANDO NO SERVIDOR
				cliente=serverSocket.accept();
				if(cliente.isConnected()){
					System.out.println("Cliente Conectado");
					Servidor novocliente = new Servidor(serverSocket, this.cliente);
					novocliente.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		try {
			
			receber = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			enviar = new PrintStream (cliente.getOutputStream());			
			
			while(true){
	
				System.out.println("Esperando por uma requisição... \n");
				mensagem = receber.readLine();
				
				JsonObject jmensagem = new JsonParser().parse(mensagem).getAsJsonObject();
				JsonParser jresposta = new JsonParser();
				
				if(mensagem == null){
					System.out.println("Mensagem Nula! \n");
					enviar.print(jresposta.parse("{\"resposta\": \"Mensagem Nula!\"}").getAsJsonObject());
					
				}
				
				System.err.println("Mensagem Recebida: " + mensagem);
				separador = jmensagem.getAsJsonObject().get("acao").getAsString();	

				switch (separador) {

					case "login":
						System.out.println("Processo de Login! \n");
						boolean usuario_cadastrado = false;
						String login = jmensagem.getAsJsonObject().get("login").getAsString();
						String senha = jmensagem.getAsJsonObject().get("senha").getAsString();
						
						for(Pessoa usuario: usuarios) {
							if(usuario.getLogin().equals(login) && usuario.getSenha().equals(senha) ) {
								usuario_cadastrado = true;
							}
						}
						
						if (usuario_cadastrado) {
							enviar.print(jresposta.parse("{\"resposta\": \"Login Realizado!\"}").getAsJsonObject());
							System.out.println("Login Realizado!");
						}else {
							enviar.print(jresposta.parse("{\"resposta\": \"Erro Login!\"}").getAsJsonObject());
							System.out.println("Erro Login!");
						}
						break;
						
					case "cadastro":
						System.out.println("Processo de Cadastro! \n");
						Long n_id = 1L;
						
						login = jmensagem.getAsJsonObject().get("login").getAsString();
						senha = jmensagem.getAsJsonObject().get("senha").getAsString();
						String nome = jmensagem.getAsJsonObject().get("nome").getAsString();
						String cpf = jmensagem.getAsJsonObject().get("cpf").getAsString();
						String endereco = jmensagem.getAsJsonObject().get("endereco").getAsString();;
						
						if(usuarios.size() > 0) {
							n_id = usuarios.get(usuarios.size()-1).getId()+1;
						}
						
						Pessoa novo = new Pessoa(n_id, nome, login, senha, cpf, endereco);
						if (!usuarios.contains(novo)){
							usuarios.add(novo);
							enviar.print(jresposta.parse("{\"resposta\": \"Cadastro Realizado!\"}").getAsJsonObject());
							System.out.println("Cadastro Realizado! \n");
						}else {
							enviar.print(jresposta.parse("{\"resposta\": \"Usuario Ja Existe!\"}").getAsJsonObject());
							System.out.println("Usuario já existe!");
						}
						break;
							
					case "listarLivros":
						System.out.println("Processo Listar Livros\n");
						String listados = biblioteca.listarLivros();						
						enviar.print(jresposta.parse("{\"resposta\": \""+listados+"\"}").getAsJsonObject());
						break;
					
					case "emprestarLivro":
						System.out.println("Processo de Emprestimo! \n");
						String res = "";
						String titulo = jmensagem.getAsJsonObject().get("titulo").getAsString();
						String isbn = jmensagem.getAsJsonObject().get("isbn").getAsString();
						nome = jmensagem.getAsJsonObject().get("nome").getAsString();
						
						Livro livro = biblioteca.getLivro(titulo, isbn);
						Pessoa cliente = getCliente(nome);
						if(livro != null) {
							res = biblioteca.emprestrarLivro(livro, cliente);
						}
						enviar.print(jresposta.parse("{\"resposta\": \""+res+"\"}").getAsJsonObject());
						break;
						
					case "reservarLivro":
						System.out.println("Processo de Reserva! \n");
						res = "";
						titulo = jmensagem.getAsJsonObject().get("titulo").getAsString();
						isbn = jmensagem.getAsJsonObject().get("isbn").getAsString();
						nome = jmensagem.getAsJsonObject().get("nome").getAsString();
						
						livro = biblioteca.getLivro(titulo, isbn);
						cliente = getCliente(nome);
						if(livro != null) {
							res = biblioteca.reservarLivro(livro, cliente);
						}
						enviar.print(jresposta.parse("{\"resposta\": \""+res+"\"}").getAsJsonObject());
						break;
			
					case "devolverLivro":
						System.out.println("Processo de Devolução! \n");
						res = "";
						titulo = jmensagem.getAsJsonObject().get("titulo").getAsString();
						isbn = jmensagem.getAsJsonObject().get("isbn").getAsString();
						nome = jmensagem.getAsJsonObject().get("nome").getAsString();
						
						livro = biblioteca.getLivro(titulo, isbn);
						cliente = getCliente(nome);
						if(livro != null) {
							res = biblioteca.devolverLivro(livro, cliente);
						}
						
						enviar.print(jresposta.parse("{\"resposta\": \""+res+"\"}").getAsJsonObject());
						break;
						
					case "buscarLivro":
						System.out.println("Processo de Busca! \n");
						res = "";
						titulo = jmensagem.getAsJsonObject().get("titulo").getAsString();
						isbn = jmensagem.getAsJsonObject().get("isbn").getAsString();
						
						livro = biblioteca.getLivro(titulo, isbn);
						res = biblioteca.buscarLivro(titulo, isbn);
						enviar.print(jresposta.parse("{\"resposta\": \""+res+"\"}").getAsJsonObject());
						
						break;
									
					default:
						enviar.print(jresposta.parse("{\"resposta\": \"ComandoDesconhecido\"}").getAsJsonObject());
						break;
					}
			}	
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}	
		super.run();
		}

	private Pessoa getCliente(String nome) {
		for (Pessoa cliente : usuarios) {
			if(cliente.getNome().equals(nome)) {
				return cliente;
			}
		}
		return null;
	}
}
