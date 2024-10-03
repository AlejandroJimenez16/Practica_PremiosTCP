package Clases;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
	
	
public class HiloClientes extends Thread{
	
	private Socket cliente;
	private int id;
	private int jugadas = 4;
	private String[][] premios;
	private int numPremios = 0;
	boolean hayPremios;
		
	public HiloClientes(Socket cliente, int id,String premios[][]) {
		this.cliente = cliente;
		this.id = id;
		this.premios = premios;
	}
		
	public void run() {
		try {
			//FLUJO SALIDA
			DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
			//FLUJO ENTRADA
			DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
			
			//Envio los datos al cliente
			flujoSalida.writeInt(id);
			flujoSalida.writeInt(jugadas);
			
			hayPremios = hayPremios(premios);
			flujoSalida.writeBoolean(hayPremios);
			
			while((jugadas > 0 && hayPremios == true)) {
				try {
					//Recibe la fila y la columna
					int fila = flujoEntrada.readInt();
					int columna = flujoEntrada.readInt();
						
					String envioCliente = flujoEntrada.readUTF();
					System.out.println(envioCliente);
					
					//Comprueba si hay premio o no
					synchronized(premios) {
						if(premios[fila][columna] != null) {
							flujoSalida.writeUTF("HAS GANADO EL PREMIO: " + premios[fila][columna]);
							System.out.println("Alguien ya gano el premio: " + premios[fila][columna]);
							premios[fila][columna] = null;
							numPremios++;
						}else {
							flujoSalida.writeUTF("No hay premio en esa posicion, pruebe otra vez");
						}
					}
					
					jugadas--;
					flujoSalida.writeInt(jugadas);
					flujoSalida.writeInt(numPremios);
					
					if(jugadas == 0) {
						//Recibe cuantos premios a ganado el cliente con la id
						String premiosObtenidos = flujoEntrada.readUTF();
						System.out.println(premiosObtenidos);
						// Recibe mensaje de desconexion
						String cadenaRecibida = flujoEntrada.readUTF();
						System.out.println(cadenaRecibida);
						
						if(numPremios == 4) {
							hayPremios = false;
						}
					}
					
					if(hayPremios == false) {
						String desconexionFaltaPremios = flujoEntrada.readUTF();
						System.out.println(desconexionFaltaPremios);	
					}
						
				} catch (IOException e) {}		
			}
						
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	public static boolean hayPremios(String[][] premios ) {
		for(int i=0; i<premios.length;i++) {
			for(int j=0;j<premios.length;j++) {
				if(premios[i][j] != null) {
					return true;
				}
			}
		}
		return false;
	}
}