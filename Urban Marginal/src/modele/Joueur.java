package modele;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import outils.connexion.Connection;

import controleur.Global;

public class Joueur extends Objet implements Global {

	private String pseudo;
	private int numperso;
	private Label message;
	private JeuServeur jeuserveur;
	private int vie; //vie restante du joueur
	private int orientation; //tourne vers la gauche (0) ou vers la droite (1)
	private int etape; //numero d'etape dans l'animation
	private Boule boule;
	private static final int MAXVIE = 10;
	private static final int GAIN = 1;
	private static final int PERTE = 2;
	
	private boolean toucheJoueur(Hashtable<Connection, Joueur> lesjoueurs){
		for(Joueur unjoueur : lesjoueurs.values()){
			if(!unjoueur.equals(this)){
				if(super.toucheObjet(unjoueur)){
					return true;
				}
			}
			
		}
		return false;
	}
	
	private boolean toucheMur(ArrayList<Mur> lesmurs){
		for(Mur unmur : lesmurs){
			if(super.toucheObjet(unmur)){
				return true;
			}
		}
		return false;
	}
	
	private void premierePosition(Hashtable<Connection, Joueur> lesjoueurs, ArrayList<Mur> lesmurs){
		label.getJLabel().setBounds(new Rectangle(0, 0, L_PERSO, H_PERSO));
		do {
			super.posX = (int)Math.round(Math.random() * (L_ARENE - L_PERSO)) ; // on enleve les dimensions du perso pour ne pas qu'il sorte de l'arene
			super.posY = (int)Math.round(Math.random() * (H_ARENE - H_PERSO - H_MESSAGE)) ; // ici en plus en prend en consideration la hauteur du message sous le perso
		}while(toucheJoueur(lesjoueurs)|| toucheMur(lesmurs));
	}
	
	private int deplace(int action, int position, int orientation, int lepas, int max, Hashtable<Connection, Joueur> lesjoueurs, ArrayList<Mur> lesmurs){
		this.orientation = orientation;
		int ancpos = position;
		position += lepas;
		if(position < 0){position = 0;}
		if(position > max){position = max;}
		if(action == GAUCHE || action == DROITE){posX = position;}else{posY = position;}
		if(toucheMur(lesmurs) || toucheJoueur(lesjoueurs)){position = ancpos;}
		etape = etape%NBETATSMARCHE + 1; 
		return position;
	}
	
	public Joueur(JeuServeur jeuserveur){
		this.jeuserveur = jeuserveur;
		vie = MAXVIE;
		etape = 1;
		orientation = DROITE;
	}
	
	public void gainVie(){
		vie += GAIN;
	}
	
	public void perteVie(){
		vie -= PERTE;
		Math.max(vie, 0);
	}
	
	public boolean estMort(){
		return vie == 0;
	}
	
	public void departJoueur(){
		if(super.label!=null){
			super.label.getJLabel().setVisible(false);
			message.getJLabel().setVisible(false);
			boule.getLabel().getJLabel().setVisible(false);
			jeuserveur.envoi(label);
			jeuserveur.envoi(message);
			jeuserveur.envoi(boule);
		}
	}
	
	public void action(int action, Hashtable<Connection, Joueur> lesjoueurs, ArrayList<Mur> lesmurs){
		switch(action){
			case GAUCHE :
				posX = deplace(action, posX, GAUCHE, -LEPAS, L_ARENE-L_PERSO, lesjoueurs, lesmurs);
			break;
			case DROITE :
				posX = deplace(action, posX, DROITE, LEPAS, L_ARENE-L_PERSO, lesjoueurs, lesmurs);
			break;
			case HAUT :
				posY = deplace(action, posY, orientation, -LEPAS, H_ARENE-H_PERSO-H_MESSAGE, lesjoueurs, lesmurs);
			break;	
			case BAS :
				posY = deplace(action, posY, orientation, LEPAS, H_ARENE-H_PERSO-H_MESSAGE, lesjoueurs, lesmurs);
			break;
			case TIRE :
				if(!boule.getLabel().getJLabel().isVisible()){
					jeuserveur.envoi(FIGHT);
					boule.tireBoule(this, lesmurs, lesjoueurs);
				}
			break;
		}
		affiche(MARCHE, etape);
	}
	
	public void affiche(String etat, int etape){
		label.getJLabel().setBounds(new Rectangle(posX, posY, L_PERSO, H_PERSO));
		label.getJLabel().setIcon(new ImageIcon(PERSO+this.numperso+etat+etape+"d"+orientation+EXTIMAGE));
		message.getJLabel().setBounds(posX-10, posY+H_PERSO, L_PERSO+20, H_MESSAGE);
		message.getJLabel().setText(pseudo+" : "+vie);
		this.jeuserveur.envoi(super.label);
		this.jeuserveur.envoi(this.message);
	}
	
	public void initPerso(String unpseudo, int unnumero, Hashtable<Connection, Joueur> lesjoueurs, ArrayList<Mur> lesmurs){
		pseudo = unpseudo;
		numperso = unnumero;
		label = new Label(Label.nbLabel+1, new JLabel());
		label.getJLabel().setHorizontalAlignment(SwingConstants.CENTER);
		label.getJLabel().setVerticalAlignment(SwingConstants.CENTER);
		jeuserveur.nouveauLabelJeu(label);
		message = new Label(Label.nbLabel+1, new JLabel());
		message.getJLabel().setHorizontalAlignment(SwingConstants.CENTER);
		message.getJLabel().setFont(new Font("Dialog", Font.PLAIN, 8));
		jeuserveur.nouveauLabelJeu(message);
		premierePosition(lesjoueurs, lesmurs);
		affiche(MARCHE, etape);
		Boule boule = new Boule(jeuserveur);
		jeuserveur.envoi(boule.getLabel());
	}
	
	public Boule getBoule(){
		return boule;
	}
	
	public String getPseudo(){
		return pseudo;
	}
	
	public Label getMessage(){
		return message;
	}
	
	public int getOrientation(){
		return orientation;
	}
}
