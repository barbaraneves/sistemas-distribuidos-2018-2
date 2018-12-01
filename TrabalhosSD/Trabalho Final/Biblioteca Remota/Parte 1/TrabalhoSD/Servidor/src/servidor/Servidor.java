package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Controller.Biblioteca;
import model.Livro;
import model.Pessoa;

public class Servidor extends Thread{
	Socket cliente;
	private ServerSocket serverSocket;
	private Biblioteca biblioteca = new Biblioteca();
	private List<Pessoa> usuarios = new ArrayList<Pessoa>();
	private String[] separador;
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
					System.out.println("CLIENTE CONECTADO "+cliente.getInetAddress().getHostName());
					Servidor novocliente = new Servidor(serverSocket, this.cliente);
					novocliente.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	
				System.out.println("Esperando por uma Mensagem! \n");
				mensagem = receber.readLine();
				if(mensagem == null){
					System.out.println("Mensagem Nula! \n");
					enviar.println("Mensagem Nula!");
					
				}
				
				System.err.println("Mensagem Recebida: " + mensagem);
				separador = mensagem.split("-");	// parametro1 - parametro2 - parametro3 - etc..	
				
				switch (separador[0]) {
					// String: login LOGIN SENHA
					case "login":
						System.out.println("Processo de Login! \n");
						boolean usuario_cadastrado = false;
						
						for(Pessoa usuario: usuarios) {
							if(usuario.getLogin().equals(separador[1]) && usuario.getSenha().equals(separador[2]) ) {
								usuario_cadastrado = true;
							}
						}
						
						if (usuario_cadastrado) {
							enviar.print("LoginRealizado");	
						}else {
							enviar.print("ErroLogin");					
						}
						break;
						
					case "cadastro":
						Long n_id = 1L;
						if(usuarios.size() > 0) {
							n_id = usuarios.get(usuarios.size()-1).getId();
						}
						
						Pessoa novo = new Pessoa(n_id, separador[1], separador[2], separador[3], separador[4], separador[5], new Date());
						if (!usuarios.contains(novo)){
							usuarios.add(novo);
							enviar.print("CadastroRealizado");
						}else {
							enviar.print("UsuarioJaExiste");
						}
						break;
							
					case "listarLivros":
						String listados = biblioteca.listarLivros();
						enviar.print(listados);
						break;
					
					case "emprestarLivro":
						String res = "";
						Livro livro = biblioteca.getLivro(separador[1], separador[2]);
						Pessoa cliente = getCliente(separador[3]);
						if(livro != null) {
							res = biblioteca.emprestrarLivro(livro, cliente);
						}
						enviar.print(res);
						break;
						
					case "reservarLivro":
						res = "";
						livro = biblioteca.getLivro(separador[1], separador[2]);
						cliente = getCliente(separador[3]);
						if(livro != null) {
							res = biblioteca.reservarLivro(livro, cliente);
						}
						enviar.print(res);
						break;
			
					case "devolverLivro":
						res = "";
						livro = biblioteca.getLivro(separador[1], separador[2]);
						cliente = getCliente(separador[3]);
						if(livro != null) {
							res = biblioteca.devolverLivro(livro, cliente);
						}
						
						enviar.print(res);
						break;
									
					default:
						enviar.print("ComandoDesconhecido");
						break;
					}
			}
			
		
		
		
		
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
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