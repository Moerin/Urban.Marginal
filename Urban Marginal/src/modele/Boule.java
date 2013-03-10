package modele;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import outils.connexion.Connection;

import controleur.Global;

public class Boule extends Objet implements Global {
	
	private JeuServeur jeuserveur;
	
	public Boule(JeuServeur jeuserveur){
		this.jeuserveur = jeuserveur;
		super.label = new Label(Label.nbLabel++, new JLabel());
		super.label.getJLabel().setHorizontalAlignment(SwingConstants.CENTER) ;
		super.label.getJLabel().setVerticalAlignment(SwingConstants.CENTER) ;
		super.label.getJLabel().setBounds(new Rectangle(0, 0, L_BOULE, H_BOULE));
		super.label.getJLabel().setIcon(new ImageIcon(BOULE));
		jeuserveur.nouveauLabelJeu(super.label);
		super.label.getJLabel().setVisible(false);
	}
	
	public void tireBoule(Joueur attaquant, ArrayList<Mur> lesmurs, Hashtable<Connection, Joueur> lesjoueurs){
		if(attaquant.getOrientation() == GAUCHE){
			attaquant.getBoule().setPosX(attaquant.getPosX()-L_BOULE-1);
		}else{
			attaquant.getBoule().setPosX(attaquant.getPosX()+L_BOULE+1);
		}
		attaquant.getBoule().setPosY(attaquant.getPosY()+(H_PERSO/2));
		new Attaque(attaquant, jeuserveur, lesmurs, lesjoueurs);
	}
}