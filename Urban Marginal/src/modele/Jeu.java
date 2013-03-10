package modele;

import outils.connexion.Connection;
import controleur.Controle;

public abstract class Jeu {
	
	//--- Propriete ---
	protected Controle controle;
	
	//--- Setter ---
	public abstract void setConnection(Connection connection);
	
	//--- Methode ---
	public abstract void reception(Connection connection, Object info);
	
	
	public void envoi(Connection connection, Object info){
		connection.envoi(info);
	}
	
	public abstract void deconnection(Connection connection);
}
