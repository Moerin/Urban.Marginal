package modele;

import java.io.Serializable;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Label implements Serializable{
	
	public static int nbLabel;
	private int numLabel;
	private JLabel jLabel;
	
	public Label(int numLabel, JLabel jLabel){
		this.numLabel = numLabel;
		this.jLabel = jLabel;
	}
	
	//--- Getters ---
	public int getNumLabel () {
		return this.numLabel ;
	}
	
	public JLabel getJLabel () {
		return this.jLabel ;
	}
}
