package modele;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

import outils.connexion.Connection;

import controleur.Global;

public class Attaque extends Thread implements Global{
	
	private Joueur attaquant;
	private JeuServeur jeuserveur;
	private ArrayList<Mur> lesmurs;
	private Hashtable<Connection, Joueur> lesjoueurs;
	
	private boolean toucheMur(){
		for(Mur unmur : lesmurs){
			if(attaquant.getBoule().toucheObjet(unmur)){
				return true;
			}
		}
		return false;
	}
	
	private Joueur toucheJoueur(){
		for(Joueur unjoueur : lesjoueurs.values()){
			if(attaquant.getBoule().toucheObjet(unjoueur)){
				return unjoueur;
			}
		}
		return null;
	}
	
	public Attaque(Joueur attaquant, JeuServeur jeuserveur, ArrayList<Mur> lesmurs, Hashtable<Connection, Joueur> lesjoueurs){
		this.attaquant = attaquant;
		this.jeuserveur = jeuserveur;
		this.lesmurs = lesmurs;
		this.lesjoueurs = lesjoueurs;
		super.start();
	}
	
	public void pause(long milli, int nano){
		try {
			Thread.sleep(milli, nano);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		attaquant.affiche(MARCHE, 1);
		Boule boule = attaquant.getBoule();
		int orientation = attaquant.getOrientation();
		boule.getLabel().getJLabel().setVisible(true);
		Joueur victime = null;
		do{
			if(orientation == GAUCHE){
				boule.setPosX(LEPAS-boule.getPosX());
			}else{boule.setPosX(LEPAS+boule.getPosX());}
			boule.getLabel().getJLabel().setBounds(new Rectangle(boule.getPosX(), boule.getPosY(), L_BOULE, H_BOULE));
			pause(10, 00);
			jeuserveur.envoi(boule.getLabel());
			victime = toucheJoueur();
		}while(boule.getPosX()>=0 &&  boule.getPosX() <= L_ARENE && !toucheMur() && victime == null);
		if(victime!=null && !victime.estMort()){
			jeuserveur.envoi(HURT);
			victime.perteVie();
			attaquant.gainVie();
			for(int k=1;k<NBETATSBLESSE;k++){
				victime.affiche(BLESSE, k);
				pause(80, 00);
			}
			if(victime.estMort()){
				jeuserveur.envoi(DEATH);
				for(int k=1;k<NBETATSMORT;k++){
					victime.affiche(MORT, k);
					pause(80, 00);
				}
			}else{
				victime.affiche(MARCHE, 1);
			}	
			attaquant.affiche(MARCHE, 1);
		}
		boule.getLabel().getJLabel().setVisible(false);
		jeuserveur.envoi(boule.getLabel());
	}

}
