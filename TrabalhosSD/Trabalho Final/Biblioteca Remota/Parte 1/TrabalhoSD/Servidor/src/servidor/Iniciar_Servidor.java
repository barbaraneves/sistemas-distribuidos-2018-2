package servidor;


public class Iniciar_Servidor {
public static void main(String[] args) {
	
		Fabrica_de_Servidores sfc = new Fabrica_de_Servidores();
		Servidor server = new Servidor(sfc.novaConexao(), null);
		server.Iniciar_conexao();
}
}
