package outils.connexion;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;



public class ClientSocket {
	
	// --- Propriete ---
	private boolean connexionOK;
	
	// --- Constructeur ---
	public ClientSocket(String ip, int port, Object lerecepteur){
		connexionOK = false;
		Socket socket;
		try {
			socket = new Socket(ip,port);
			System.out.println("connexion au serveur reussi");
			connexionOK =true;
			new Connection(socket, lerecepteur);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "erreur : serveur indisponible");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "erreur : verifier l'ip");
		}
	}
	
	//--- Getter sur la propriete connexionOK ---
	public boolean getEtatConnexion(){
		return connexionOK;
	}
}
