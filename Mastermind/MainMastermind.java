/* 	Mastermind projet 
 * 	MI-IN DEV3 EPTM Sion
 *	Thibaut Cotture Hopital du Valais 
 *	M120
 * 
 * 	MainMastermind
 */
package Mastermind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MainMastermind extends JFrame {

	private JPanel pnlControl, pnlGame;
	private JButton btnRestart, btnSolution, btnCheck, btnHelp;
	private JLabel lblWriter, lblStatus;
	private JRadioButton rbnBleu, rbnOrange, rbnVert, rbnRed, rbnGris, rbnViolet;
	private Row myRow , mySol;
	private GameController gc;
	int c 	= 0;
	int cp 	= 0;
	int t = 0;
	int oldT = 1;
	String sRes   =""; 
	static String[] asOp = {"oui","non" };


	//temp
	Color[] aSol = null;
	Color[] aProp1 = {Color.blue,Color.orange,Color.gray,Color.red};
	Color[] aProp2 =  {Color.orange,Color.blue,Color.gray,Color.red};
	

	public static void main(String[] args) {
		MainMastermind MMm = new MainMastermind();
		MMm.setVisible(true);
		System.out.println("-----------App-Start-----------");
		//affichage de la dialog de bienvenue
		int n = JOptionPane.showOptionDialog(	MMm, 
												"Bienvenue dans MasterMind. Connaissez-vous les régles du jeu ?",
												"Bienvenue",
												JOptionPane.YES_NO_OPTION,
												JOptionPane.PLAIN_MESSAGE	,
												null,
												asOp,
												asOp[0]);
		if (n == 1) {
			getHelp();
		} else {
			System.out.println("D'acc alors je continue");
		}
		
	}

	public MainMastermind() {
		super("Mastermind");
		this.gc = GameController.getInstance();
		aSol = gc.genrateRandomCombination();
		initGUI();
	}
	
	//function pour afficher l aide il ouvre le pdf avec les regles 
	public static void getHelp() {
		if (Desktop.isDesktopSupported()) {
			File file = new File("");
			String currentPath = file.getAbsolutePath();
		    try {
		        File myFile = new File(currentPath+"\\src\\Mastermind\\ReglesDuJeu.pdf");
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException ex) {
		        // no application registered for PDFs
		    }
		}
	}
	
	//function qui affiche la solution 
	public void printSolution() {
		mySol = new Row(aSol);
		// dialog
		JOptionPane jopSol = new JOptionPane();
		JDialog dialog = jopSol.createDialog("La solution");
		dialog.setSize(320, 113);
		dialog.setContentPane(mySol);
		dialog.setVisible(true);
	}
	
	 void initGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(450, 1000);
		setBackground(Color.white);
		setResizable(false);
		
		//instanciation des composant
		//pnl de commande a gauche
		pnlControl = new JPanel();
		btnRestart = new JButton("Recommencer");
		btnSolution = new JButton("Solution");
		lblStatus = new JLabel(sRes);
		btnCheck = new JButton("Check");

		rbnBleu = new JRadioButton("Bleu");
		rbnOrange = new JRadioButton("Orange");
		rbnVert = new JRadioButton("Vert");
		rbnRed = new JRadioButton("Rouge");
		rbnGris = new JRadioButton("Gris");
		rbnViolet = new JRadioButton("Violet");

		btnHelp = new JButton("Aide");
		lblWriter = new JLabel("ThibCott");
		
		//instanciation du groupe de radioButton
		ButtonGroup bGroup = new ButtonGroup();

		//ajoute des radio btn dans le groupe
		bGroup.add(rbnBleu);
		bGroup.add(rbnOrange);
		bGroup.add(rbnVert);
		bGroup.add(rbnRed);
		bGroup.add(rbnGris);
		bGroup.add(rbnViolet);
		
		
		pnlControl.setBackground(Color.WHITE);
		//ajoute du pnl de Control dans la fenetre
		getContentPane().add(pnlControl, BorderLayout.WEST);
		pnlControl.setLayout(new BoxLayout(pnlControl, BoxLayout.Y_AXIS));

		//ajoute du btn restart
		btnRestart.setMaximumSize(new Dimension(120, 20));
		pnlControl.add(btnRestart);
		btnRestart.addActionListener(new Restart());

		//ajoute du btn solution
		btnSolution.setMaximumSize(new Dimension(120, 20));
		pnlControl.add(btnSolution);
		btnSolution.addActionListener(new Solution());
		
		//ajoute du lbl du status
		pnlControl.add(lblStatus);
		
		//ajoute d un espace
		pnlControl.add(Box.createGlue());

		//ajoute du btn check
		btnCheck.setMaximumSize(new Dimension(120, 20));
		pnlControl.add(btnCheck);
		btnCheck.addActionListener(new Check());

		//ajoute des radio btn de couleurs 
		rbnBleu.setMaximumSize(new Dimension(120, 20));
		rbnBleu.setBackground(Color.blue);
		rbnBleu.setForeground(Color.white);
		rbnBleu.addActionListener(new ChangeColor());
		pnlControl.add(rbnBleu);

		rbnOrange.setMaximumSize(new Dimension(120, 20));
		rbnOrange.setBackground(Color.orange);
		rbnOrange.addActionListener(new ChangeColor());
		pnlControl.add(rbnOrange);

		rbnVert.setMaximumSize(new Dimension(120, 20));
		rbnVert.setBackground(Color.green);
		rbnVert.addActionListener(new ChangeColor());
		pnlControl.add(rbnVert);

		rbnRed.setMaximumSize(new Dimension(120, 20));
		rbnRed.setBackground(Color.red);
		rbnRed.addActionListener(new ChangeColor());
		pnlControl.add(rbnRed);

		rbnGris.setMaximumSize(new Dimension(120, 20));
		rbnGris.setBackground(Color.gray);
		rbnGris.addActionListener(new ChangeColor());
		pnlControl.add(rbnGris);

		rbnViolet.setMaximumSize(new Dimension(120, 20));
		rbnViolet.setBackground(Color.magenta);
		rbnViolet.addActionListener(new ChangeColor());
		pnlControl.add(rbnViolet);
		
		//ajoute du btn help 
		btnHelp.setMaximumSize(new Dimension(120, 20));
		pnlControl.add(btnHelp);
		btnHelp.addActionListener(new Help());
		
		//label pour l auteur de l app 
		pnlControl.add(lblWriter);
		pnlControl.setBackground(Color.decode("#e6f2ff"));
		
		//ajoute a la frame du pnl game c est la ou toutes les lignes sont afficher 
		pnlGame = new JPanel();
		pnlGame.setBackground(Color.white);

		for (int i = 0; i < 12; i++) {
			myRow = new Row(i);
			
			gc.alRow.add(myRow);
			
			myRow.setPreferredSize(new Dimension(300,70));
			myRow.addMouseListener(myRow);
			pnlGame.add(myRow);
			
		}
		gc.setActiveRound();
			
		getContentPane().add(pnlGame);
	}


	 
	class ChangeColor implements ActionListener {

		
		@Override
		public void actionPerformed(ActionEvent e) {

			// TODO Auto-generated method stub
			if (rbnBleu.isSelected()) {
				System.out.println("bleu");
				gc.currentColor = Color.blue;
			}
			if (rbnOrange.isSelected()) {
				System.out.println("orange");
				gc.currentColor = Color.ORANGE;
			}
			if (rbnGris.isSelected()) {
				System.out.println("gris");
				gc.currentColor = Color.gray;
			}
			if (rbnRed.isSelected()) {
				System.out.println("red");
				gc.currentColor = Color.red;
			}
			if (rbnVert.isSelected()) {
				System.out.println("vert");
				gc.currentColor = Color.green;
			}
			if (rbnViolet.isSelected()) {
				System.out.println("violet");
				gc.currentColor = Color.magenta;
			}
		}
	}
	class Solution implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Solution");
			printSolution();
		}
	}
	class Restart implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Restart");
					
			int n = JOptionPane.showOptionDialog(	rootPane, 
													"Etes-vous vraiment sûr de vouloir recommencez ?",
													"Recommancer ?",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,
													asOp,
													asOp[0]);
			if(n == 0) {
				//appel de la fonction pour recommencer 				
				gc.reset();
			}else {
				System.out.println("Ooops, bon je continue");
			}
		
		}
		
	}
	class Help implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			getHelp();
		}
		
	}
	
	class Check implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			c=0;
			cp=0;
			// TODO Auto-generated method stub
			if(gc.currentRound >= 11) {
				
				int n = JOptionPane.showOptionDialog(	rootPane, 
														"Voulez-vous voir la solution  ?",
														"Fin de partie",
														JOptionPane.YES_NO_OPTION,
														JOptionPane.QUESTION_MESSAGE,
														null,
														asOp,
														asOp[0]);
				if(n == 0) {
					//appel de la fonction pour afficher la solution  	
					printSolution();
					gc.reset();
					
				}else {
					System.out.println("Ooops, bon je continue");
				}
				
				
				//faire qqch pour arreter le jeu 
				gc.reset();
				sRes = "";
				lblStatus.setText("<html>"+sRes+"<html/>");

			} else {
				
				
				int ta=gc.currentRound+1;
			
				//verifie si tout a ete saisie
				boolean isBlack = false;
				for (Color color : gc.alRow.get(gc.currentRound).getColor() ) {
					System.out.println("c e :" + color);
					if(color == Color.black) {
						isBlack = true;
					}					
				}
				if(isBlack==true) {
				} else {

					//check de la solution
					for (int i : gc.checkSol(aSol, gc.alRow.get(gc.currentRound).getColor() )) {
						if(i == 1) {
							c++;
						}
						if(i == 2) {
							cp++;
						}
					}
					if(cp>=3) {
						JOptionPane.showMessageDialog(
								null,
								"Bravo, vous avez trouvé la bonne combinaison de couleur !");
								gc.reset();

					}
					sRes +="<br><p>Tour : "+ta+"</p>";

					sRes+="<p> Blanc(s) : " + c + "<br/> Noir(s) : " + cp + "</p>";
				
					//incremnt pour passer au tour suivant 
					gc.nextRound();
				}
				//System.out.println("res: "+sRes);
				//affichage du status du resultat de la combinaison 
				lblStatus.setText("<html>"+sRes+"<html/>");
			}	
		} 
	}
}
