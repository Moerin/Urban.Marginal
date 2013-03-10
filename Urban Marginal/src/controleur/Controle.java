package controleur;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import modele.Label;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;

public class Controle implements Global{
	
	// --- Propriete ---
	private EntreeJeu frmEntreeJeu;
	private Jeu lejeu;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	private Connection connection;
	
	//--- Getter sur la propriete lejeu ---
	public Jeu getLeJeu(){
		return this.lejeu;
	}
	
	private void evenementEntreeJeu(Object info){
		if((String)info == "serveur"){
			new ServeurSocket(this, PORT);
			lejeu = new JeuServeur(this);
			frmEntreeJeu.dispose();
			frmArene = new Arene(this, "serveur");
			((JeuServeur)lejeu).constructionMurs();
			frmArene.setVisible(true);
		}else{
			if((new ClientSocket((String)info, PORT, this)).getEtatConnexion()){
				lejeu = new JeuClient(this);
				lejeu.setConnection(connection);
				frmArene = new Arene(this,"client");
				frmEntreeJeu.dispose();
				frmChoixJoueur = new ChoixJoueur(this);
				frmChoixJoueur.setVisible(true);
			}
		}
	}
	
	private void evenementChoixJoueur(Object info){
		((JeuClient)lejeu).envoi(info);
		frmChoixJoueur.dispose();
		frmArene.setVisible(true);
	}
	
	private void evenementArene(Object info){
		((JeuClient)lejeu).envoi(info);
	}
	
	// --- Constructeur ---
	public Controle(){
		frmEntreeJeu = new EntreeJeu(this);
		frmEntreeJeu.setVisible(true);
	}
	
	public void evenementVue(Object uneFrame, Object info){
		if(uneFrame instanceof EntreeJeu){
			evenementEntreeJeu(info);
		}
		if(uneFrame instanceof ChoixJoueur){
			evenementChoixJoueur(info);
		}
		if(uneFrame instanceof Arene){
			evenementArene(info);
		}
	}
	
	public void evenementModele(Object unjeu, String ordre, Object info){
		if(unjeu instanceof JeuServeur){
			evenementJeuServeur(ordre, info);
		}
		if(unjeu instanceof JeuClient){
			evenementJeuClient(ordre, info);
		}
	}
	
	public void evenementJeuServeur(String ordre, Object info){
		if(ordre == "ajout mur"){
			frmArene.ajoutMur((JLabel)info);
		}
		if(ordre == "envoi panel murs"){
			((JeuServeur)lejeu).envoi((Connection)info, frmArene.getJpnMur());
		}
		if(ordre == "ajout joueur"){
			frmArene.ajoutJoueur((JLabel)info);
		}
		if(ordre =="ajout phrase"){
			frmArene.ajoutChat((String)info);
			((JeuServeur)lejeu).envoi(frmArene.getTxtChat().getText());
		}
	}
	
	public void evenementJeuClient(String ordre, Object info){
		if(ordre == "ajout panel murs"){
			frmArene.ajoutPanelMurs((JPanel)info);
		}
		if(ordre == "ajout joueur"){
			frmArene.ajoutModifJoueur(((Label)info).getNumLabel(), ((Label)info).getJLabel());
		}
		if(ordre == "remplace chat"){
			frmArene.remplaceChat((String)info);
		}
		if(ordre == "son"){
			frmArene.joueSon((Integer)info);
		}
	}
	
	public void setConnection(Connection connection){
		this.connection = connection;
		if(lejeu instanceof JeuServeur){
			lejeu.setConnection(connection);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Controle();
	}

}
