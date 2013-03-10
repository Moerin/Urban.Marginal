package outils.connexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurSocket extends Thread {
	
	//--- Propriete ---
	private Object lerecepteur;
	private ServerSocket serversocket;
	
	//--- Constructeur ---
	public ServeurSocket(Object lerecepteur, int port){
		this.lerecepteur = lerecepteur;
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("erreur creation socket serveur :"+e);
			System.exit(0);
		}
		start();
	}
	
	//--- Run : à l'écoute de l'arrivée d'un nouveau client ---
	public void run(){
		Socket socket;
		while(true){
			try {
				System.out.println("le serveur attend");
				socket = this.serversocket.accept();
				System.out.println("un client s'est connecte");
				new Connection(socket, this.lerecepteur);
			} catch (IOException e) {
				System.out.println("erreur socket :"+e);
				System.exit(0);
			}
		}
	}
}
