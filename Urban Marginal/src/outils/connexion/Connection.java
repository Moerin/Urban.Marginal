package outils.connexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Connection extends Thread{
	
	// --- Propriete ---
	private Object lerecepteur;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	// --- Constructeur ---
	public Connection(Socket socket, Object lerecepteur){
		this.lerecepteur = lerecepteur;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("erreur creation canal sortie :"+e);
			System.exit(0);
		}
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("erreur creation canal entree :"+e);
			System.exit(0);
		}
		start();
		((controleur.Controle)lerecepteur).setConnection(this);
	}
	
	//--- Run : écoute les messages entrants provenant de l'ordi distant ---
	public void run(){
		Boolean inOK = true;
		Object reception;
		while(inOK){
			try {
				reception = in.readObject();
				((controleur.Controle)lerecepteur).getLeJeu().reception(this, reception);
			} catch (ClassNotFoundException e) {
				System.out.println("erreur : Classe non trouve "+e);
				System.exit(0);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "L'ordinateur distant s'est deconnecte");
				((controleur.Controle)lerecepteur).getLeJeu().deconnection(this);
				inOK = false;
				try {
					in.close();
				} catch (IOException e1) {
					System.out.println("erreur : Fermeture canal "+e);
				}
			}
		}
	}
	
	public synchronized void envoi(Object unobjet){
		try {
			this.out.reset();
			this.out.writeObject(unobjet);
			this.out.flush();
		} catch (IOException e) {
			System.out.println("erreur : objet out ne fonctionne pas "+e);
		}
	}
	
}
