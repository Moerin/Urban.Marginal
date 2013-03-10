package vue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import outils.son.Son;

import controleur.Controle;
import controleur.Global;

public class Arene extends JFrame implements Global {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel lblFond = null;
	private JPanel jpnJeu = null;
	private JPanel jpnMur = null;
	private JTextField txtSaisie = null;
	private JScrollPane jspChat = null;
	private JTextArea txtChat = null;
	private boolean client;
	private Controle controle;
	private Son[] lessons = new Son[SON.length];

	/**
	 * This is the default constructor
	 */
	public Arene(Controle controle, String typejeu) {
		super();
		client = (typejeu=="client") ;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.controle = controle;
		initialize();
		if(client){
			(new Son(SONAMBIANCE)).playContinue();
			for(int k=0;k<SON.length;k++){
				lessons[k] = new Son(CHEMINSONS+SON[k]);
			}
		}
	}
	
	public void joueSon(int numson){
		lessons[numson].play();
	}
	
	public void ajoutMur(JLabel unmur){
		jpnMur.add(unmur);
		jpnMur.repaint();
	}
	
	public void ajoutJoueur(JLabel unjoueur){
		jpnJeu.add(unjoueur);
		jpnJeu.repaint();
	}
	
	public void ajoutModifJoueur(int num, JLabel unlabel){
		try {
			this.jpnJeu.remove(num);
		} catch (ArrayIndexOutOfBoundsException ex) {}
		this.jpnJeu.add(unlabel, num);
		this.jpnJeu.repaint();
	}
	
	public void ajoutChat(String unephrase){
		txtChat.setText(unephrase+"\r\n"+txtChat.getText());
	}
	
	public void ajoutPanelMurs(JPanel unpanel){
		jpnMur.add(unpanel);
		jpnMur.repaint();
		jContentPane.requestFocus();
	}
	
	public void remplaceChat(String laphrase){
		txtChat.setText(laphrase);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(L_ARENE, H_ARENE+H_CHAT);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblFond = new JLabel();
			lblFond.setBounds(new Rectangle(0, 0, L_ARENE, H_ARENE));
			lblFond.setIcon(new ImageIcon(FONDARENE));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJpnJeu(), null);
			jContentPane.add(getJpnMur(), null);
			jContentPane.add(lblFond, null);
			if(client){
				jContentPane.add(getTxtSaisie(), null);
				jContentPane.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyPressed(java.awt.event.KeyEvent e) {
						jContentPane_keyPressed(e);
					}
				});
			}
			jContentPane.add(getJspChat(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jpnJeu	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnJeu() {
		if (jpnJeu == null) {
			jpnJeu = new JPanel();
			jpnJeu.setLayout(null);
			jpnJeu.setBounds(new Rectangle(0, 0, L_ARENE, H_ARENE));
			jpnJeu.setOpaque(false);
		}
		return jpnJeu;
	}

	/**
	 * This method initializes jpnMur	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJpnMur() {
		if (jpnMur == null) {
			jpnMur = new JPanel();
			jpnMur.setLayout(null);
			jpnMur.setBounds(new Rectangle(0, 0, L_ARENE, H_ARENE));
			jpnMur.setOpaque(false);
		}
		return jpnMur;
	}
	
	private void jContentPane_keyPressed(KeyEvent e){
		int valeur = -1;
		switch(e.getKeyCode()){
			case KeyEvent.VK_SPACE :
				valeur = ACTION;
			break;
			case KeyEvent.VK_LEFT :
				valeur = GAUCHE;
			break;
			case KeyEvent.VK_RIGHT :
				valeur = DROITE;
			break;
			case KeyEvent.VK_DOWN :
				valeur = BAS;
			break;
			case KeyEvent.VK_UP :
				valeur = HAUT;
			break;
		}
		if(valeur != -1){
			controle.evenementVue(this, ACTION+SEPARE+valeur);
		}
	}
	
	private void txtSaisie_keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			controle.evenementVue(this, CHAT+SEPARE+txtSaisie.getText());
			txtSaisie.setText("");
			jContentPane.requestFocus();
		}
	}
	
	/**
	 * This method initializes txtSaisie	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtSaisie() {
		if (txtSaisie == null) {
			txtSaisie = new JTextField();
			txtSaisie.setBounds(new Rectangle(2, H_ARENE+MARGE, L_ARENE-2*MARGE, H_SAISIE));
			txtSaisie.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					txtSaisie_keyPressed(e);
				}
			});
		}
		return txtSaisie;
	}

	/**
	 * This method initializes jspChat	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspChat() {
		if (jspChat == null) {
			jspChat = new JScrollPane();
			jspChat.setBounds(new Rectangle(MARGE, H_ARENE+H_SAISIE+2*MARGE, L_ARENE-3*MARGE, H_CHAT-H_SAISIE-8*MARGE));
			jspChat.setViewportView(getTxtChat());
			jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jspChat;
	}

	/**
	 * This method initializes txtChat	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getTxtChat() {
		if (txtChat == null) {
			txtChat = new JTextArea();
		}
		return txtChat;
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
