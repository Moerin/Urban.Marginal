package vue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JTextField;

import controleur.Controle;

public class EntreeJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JButton cmdServeur = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextField txtIP = null;
	private JButton cmdClient = null;
	private JButton cmdQuitter = null;
	private Controle controle;
	

	/**
	 * This is the default constructor
	 */
	public EntreeJeu(Controle controle) {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.controle = controle;
		initialize();
	}
	
	//--- Clic sur le bouton demarrer ---
	private void cmdServeur_clic(){
		controle.evenementVue(this, "serveur");
	}
	
	//--- Clic sur le bouton connecter ---
	private void cmdClient_clic(){
		controle.evenementVue(this, txtIP.getText());
	}
	
	//--- Clic sur le bouton quitter ---
	private void cmdQuitter_clic(){
		System.exit(0);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(410, 242);
		this.setContentPane(getJContentPane());
		this.setTitle("Urban Marginal");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(22, 92, 73, 34));
			jLabel2.setText("IP serveur :");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(20, 50, 283, 31));
			jLabel1.setText("Je veux me connecter a un serveur existant :");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(20, 11, 154, 25));
			jLabel.setText("Je veux etre un serveur :");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCmdServeur(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTxtIP(), null);
			jContentPane.add(getCmdClient(), null);
			jContentPane.add(getCmdQuitter(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cmdServeur	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCmdServeur() {
		if (cmdServeur == null) {
			cmdServeur = new JButton();
			cmdServeur.setBounds(new Rectangle(181, 9, 91, 25));
			cmdServeur.setText("Demarrer");
			cmdServeur.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cmdServeur_clic();
				}
			});
		}
		return cmdServeur;
	}

	/**
	 * This method initializes txtIP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtIP() {
		if (txtIP == null) {
			txtIP = new JTextField();
			txtIP.setBounds(new Rectangle(103, 91, 111, 29));
			txtIP.setText("127.0.0.1");
		}
		return txtIP;
	}

	/**
	 * This method initializes cmdClient	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCmdClient() {
		if (cmdClient == null) {
			cmdClient = new JButton();
			cmdClient.setBounds(new Rectangle(226, 92, 100, 31));
			cmdClient.setText("Connecter");
			cmdClient.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cmdClient_clic();
				}
			});
		}
		return cmdClient;
	}

	/**
	 * This method initializes cmdQuitter	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCmdQuitter() {
		if (cmdQuitter == null) {
			cmdQuitter = new JButton();
			cmdQuitter.setBounds(new Rectangle(224, 132, 104, 32));
			cmdQuitter.setText("Quitter");
			cmdQuitter.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cmdQuitter_clic();
				}
			});
		}
		return cmdQuitter;
	}

}  //  @jve:decl-index=0:visual-constraint="225,9"
