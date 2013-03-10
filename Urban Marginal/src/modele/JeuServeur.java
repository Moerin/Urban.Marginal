package modele;

import java.util.ArrayList;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;
import java.util.Hashtable;

public class JeuServeur extends Jeu implements Global {
	
	private ArrayList<Mur> lesmurs = new ArrayList<Mur>();
	private ArrayList<Joueur> lesjoueursdanslordre = new ArrayList<Joueur>();
	private Hashtable<Connection, Joueur> lesjoueurs = new Hashtable<Connection, Joueur>();
	
	public JeuServeur(Controle controle){
		super.controle = controle;
		Label.nbLabel = 0;
	}
	
	public void constructionMurs(){
		for(int k = 0; k < NBMURS; k++){
			lesmurs.add(new Mur());
			controle.evenementModele(this, "ajout mur", lesmurs.get(k).getLabel().getJLabel());
		}
	}
	
	public void envoi(Object info){
		for (Connection uneconnection : lesjoueurs.keySet()){
			super.envoi(uneconnection, info);
		}
	}
	
	public void nouveauLabelJeu(Label label){
		controle.evenementModele(this, "ajout joueur", label.getJLabel());
	}
	
	@Override
	public void deconnection(Connection connection) {
		lesjoueurs.get(connection).departJoueur();
		lesjoueurs.remove(connection);
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String)info).split(SEPARE);
		switch(Integer.parseInt(infos[0])){
			case PSEUDO :
				controle.evenementModele(this, "envoi panel murs", connection);
				for(Joueur unjoueur : lesjoueursdanslordre){
					super.envoi(connection, unjoueur.getLabel());
					super.envoi(connection, unjoueur.getMessage());
					super.envoi(connection, unjoueur.getBoule());
				}
				lesjoueurs.get(connection).initPerso(infos[1], Integer.parseInt(infos[2]), lesjoueurs, lesmurs);
				this.lesjoueursdanslordre.add(this.lesjoueurs.get(connection));
				String unephrase = "*** "+lesjoueurs.get(connection).getPseudo()+" vient de se connecter ***";
				controle.evenementModele(this, "ajout phrase", unephrase);
				break;
			case CHAT :
				unephrase = lesjoueurs.get(connection).getPseudo()+" > "+infos[1];
				controle.evenementModele(this, "ajout phrase", unephrase);
				break;
			case ACTION :
				if(!lesjoueurs.get(connection).estMort()){
					lesjoueurs.get(connection).action(Integer.parseInt(infos[1]), lesjoueurs, lesmurs);
				}
				break;
		}
	}

	@Override
	public void setConnection(Connection connection) {
		lesjoueurs.put(connection, new Joueur(this));// j ai rajoute des parametres dans le constructeur
	}

}
