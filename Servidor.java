package Clases;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Servidor{

	public static void main(String[] args) throws IOException {
		int puerto = 8007;
		ServerSocket servidor = new ServerSocket(puerto);
		
		String[][] premios = new String[3][4];
		premios[0][0] = "Crucero";
		premios[1][2] = "Entradas";
		premios[2][0] = "Masaje";
		premios[2][3] = "1000€";
		
		System.out.println("Servidor iniciado");
		System.out.println("Posiciones de los premios: " + Arrays.deepToString(premios));
		System.out.println("Esperando al cliente.....");
		
		
		int id=1;
		
		while(true) {
			
			Socket cliente = servidor.accept();
			
			System.out.println("Cliente conectado ->" + id);
			
			HiloClientes hiloClientes = new HiloClientes(cliente, id, premios);
			
			hiloClientes.start();
				
			id++;
			
		}
		
	}
	
}