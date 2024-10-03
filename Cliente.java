package Clases;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente {

	private JFrame frame;
	
	int id;
	int jugadas;
	int numPremios;
	boolean hayPremios;
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		try {
			Cliente window = new Cliente();
			window.frame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public Cliente() throws UnknownHostException, IOException, ClassNotFoundException {
		
		//CLIENTE
		String host = "localhost";
		int puerto = 8007;
				
		System.out.println("CLIENTE INICIADO...");
		Socket cliente = new Socket(host, puerto);
		
		//FLUJO DE ENTRADA AL CLIENTE
		DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
		
		//FLUJO SALIDA AL SERVIDOR
		DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
		
		//Recibo los datos del servidor
		id = flujoEntrada.readInt();
		jugadas = flujoEntrada.readInt();
		hayPremios = flujoEntrada.readBoolean();
		
		//CREO LA INTERFAZ
		//--------------JFrame-----------------------
		frame = new JFrame();
		frame.setBounds(100, 100, 587, 318);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
				
		//--------------JLabel ID-----------------------
		JLabel ID = new JLabel("ID:");
		ID.setBounds(22, 23, 46, 14);
		Font textoBasic = new Font("Arial",Font.PLAIN,17);
		ID.setFont(textoBasic);
		frame.getContentPane().add(ID);
		
		//----------TextField ID-----------------------
		JTextField IDtextField = new JTextField();
		IDtextField.setBounds(10, 48, 46, 20);
		IDtextField.setEditable(false);
		frame.getContentPane().add(IDtextField);
		IDtextField.setColumns(10);
		
		//--------------JLabel FILA-----------------------
		JLabel FILA = new JLabel("FILA");
		FILA.setBounds(123, 25, 46, 14);
		FILA.setFont(textoBasic);
		frame.getContentPane().add(FILA);
		
		//----------TextField FILA-----------------------
		JTextField FILAtextField = new JTextField();
		FILAtextField.setColumns(10);
		FILAtextField.setBounds(107, 42, 75, 33);
		frame.getContentPane().add(FILAtextField);
		
		//--------------JLabel COLUMNA-----------------------
		JLabel COLUMNA = new JLabel("COLUMNA");
		COLUMNA.setBounds(247, 24, 92, 12);
		COLUMNA.setFont(textoBasic);
		frame.getContentPane().add(COLUMNA);
		
		//----------TextField COLUMNA-----------------------
		JTextField COLUMNAtextField = new JTextField();
		COLUMNAtextField.setColumns(10);
		COLUMNAtextField.setBounds(247, 42, 75, 33);
		frame.getContentPane().add(COLUMNAtextField);
		
		//----------JTextArea-----------------------
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 100, 369, 168);
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);
		
		//--------------JLabel JUGADAS-----------------------
		JLabel JUGADAS = new JLabel("<html><b>Jugadas:</b></html>");
		Font texto2 = new Font("Arial",Font.PLAIN,14);
		JUGADAS.setFont(texto2);
		JUGADAS.setBounds(398, 142, 67, 20);
		frame.getContentPane().add(JUGADAS);
				
		//----------TextField JUGADAS-----------------------
		JTextField JUGADAStextField = new JTextField();
		JUGADAStextField.setBounds(475, 138, 45, 24);
		JUGADAStextField.setEditable(false);
		frame.getContentPane().add(JUGADAStextField);
		JUGADAStextField.setColumns(10);
				
		//--------------JLabel PREMIOS-----------------------
		JLabel PREMIOS = new JLabel("<html><b>Premios:</b></html>");
		PREMIOS.setFont(new Font("Arial", Font.PLAIN, 14));
		PREMIOS.setBounds(398, 175, 67, 20);
		frame.getContentPane().add(PREMIOS);
				
		//----------TextField PREMIOS-----------------------
		JTextField PREMIOStextField = new JTextField();
		PREMIOStextField.setColumns(10);
		PREMIOStextField.setBounds(475, 177, 45, 24);
		PREMIOStextField.setEditable(false);
		frame.getContentPane().add(PREMIOStextField);
		
		//----------Boton SALIR-----------
		JButton SALIR = new JButton("SALIR");
		SALIR.setBounds(428, 70, 92, 33);
		frame.getContentPane().add(SALIR);
		
		//----------Boton ENVIAR-----------
		JButton ENVIAR = new JButton("ENVIAR");
		ENVIAR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int fila = Integer.parseInt(FILAtextField.getText());
				int columna = Integer.parseInt(COLUMNAtextField.getText());
					
				FILAtextField.setText("");
				COLUMNAtextField.setText("");
					
				//Envio la fila y columna al servidor

				new Thread(() -> {
					try {
						if((fila >= 0 && fila < 3) && (columna >=0 && columna < 4)) {
							if(jugadas > 0 && hayPremios == true) {
								//Envia la fila y la columna
								flujoSalida.writeInt(fila);
								flujoSalida.writeInt(columna);
								flujoSalida.flush(); 
									
								//Manda al servidor que fila y columna a introducido el cliente
								flujoSalida.writeUTF("El cliente " + id + " ha enviado fila: " + fila + " columna: " + columna);
								textArea.append("\n" + "Usted introdujo --> Fila : " + fila + " Columna: " + columna);
									
								//Recibe del servidor si hay premio o no
								String respuesta = flujoEntrada.readUTF();
								textArea.append("\n" + respuesta);
									
								//Actualiza las jugadas
								jugadas = flujoEntrada.readInt();
					            JUGADAStextField.setText(String.valueOf(jugadas));
					                
					            numPremios = flujoEntrada.readInt();
					            PREMIOStextField.setText(String.valueOf(numPremios));
					            
					            //Si se han acabado las jugadas
					            if(jugadas == 0) {
					            	ENVIAR.setEnabled(false);
					            	SALIR.setEnabled(true);
					                textArea.append("\nYa no tienes mas jugadas disponibles.");  	
					            }
							}
									
						}else {
								textArea.append("\nFila o columna incorrectas");
						}
							
					} catch (IOException e1) {
						e1.printStackTrace();
					}
						
				}).start();
			}
		});
		ENVIAR.setBounds(428, 21, 92, 33);
		frame.getContentPane().add(ENVIAR);
		
		SALIR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jugadas == 0) {
					try {
						flujoSalida.writeUTF("cliente " + id + " ha ganado: " + PREMIOStextField.getText() + " premio/s");
						flujoSalida.writeUTF("cliente desconectado por falta de intentos -> " + id); 
						System.exit(0);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	                
				}else if(hayPremios == false) {
					try {
						flujoSalida.writeUTF("cliente desconectado -> " + id + " por falta de premios");
						System.exit(0);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		
//------------------------------------------------------------------------------------------------------------		
		//Establezco valores a los textField
		IDtextField.setText(String.valueOf(id));
		JUGADAStextField.setText(String.valueOf(jugadas));
		PREMIOStextField.setText("0");
		SALIR.setEnabled(false);
		textArea.setText("Se puede jugar, aun quedan premios");
		
		if(hayPremios == false) {
			textArea.setText("Ya no quedan premios. Desconectando...");
			ENVIAR.setEnabled(false);
            SALIR.setEnabled(true); 
		}
	}
}