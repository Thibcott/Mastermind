package Mastermind;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorListener;


public class MainMastermind extends JFrame {

	private JPanel pnlControl, pnlGame;
	private JButton btnRestart, btnSolution, btnCheck;
	private JLabel lblWriter, lblStatus;
	private JRadioButton rbnBleu, rbnOrange, rbnVert, rbnRed, rbnGris, rbnViolet;
	private Color[] solution;
	private Color[][] propositions;
	private Row myRow , mySol;
	private GameController gc;
	int c 	= 0;
	int cp 	= 0;
	int t = 0;
	String sRes   =""; 

	//temp
	Color[] aSol = null;
	Color[] aProp1 = {Color.blue,Color.orange,Color.gray,Color.red};
	Color[] aProp2 =  {Color.orange,Color.blue,Color.gray,Color.red};
	

	public static void main(String[] args) {
		MainMastermind MMm = new MainMastermind();
		MMm.setVisible(true);
		System.out.println("-----------App-Start-----------");
		//affichage de la dialog de bienvenue
			//pas fini a finir 
		String[] asOp = {"yes","no" };
		int n = JOptionPane.showOptionDialog(	MMm, 
												"Bienvenue dans MasterMind. Connaissez vous les regles du jeu ?",
												"Bienvenue",
												JOptionPane.YES_NO_OPTION,
												JOptionPane.PLAIN_MESSAGE	,
												null,
												asOp,
												asOp[0]);
		if (n == 1) {
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

		// pnl de commande a gauche
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

		lblWriter = new JLabel("ThibCott");

		ButtonGroup bGroup = new ButtonGroup();

		bGroup.add(rbnBleu);
		bGroup.add(rbnOrange);
		bGroup.add(rbnVert);
		bGroup.add(rbnRed);
		bGroup.add(rbnGris);
		bGroup.add(rbnViolet);

		pnlControl.setBackground(Color.WHITE);
		getContentPane().add(pnlControl, BorderLayout.WEST);
		pnlControl.setLayout(new BoxLayout(pnlControl, BoxLayout.Y_AXIS));

		
		btnRestart.setMaximumSize(new Dimension(120, 20));
		pnlControl.add(btnRestart);
		btnRestart.addActionListener(new Restart());

		btnSolution.setMaximumSize(new Dimension(120, 20));
		pnlControl.add(btnSolution);
		btnSolution.addActionListener(new Solution());
		
		
		pnlControl.add(lblStatus);
		pnlControl.add(Box.createGlue());

		btnCheck.setMaximumSize(new Dimension(120, 20));
		pnlControl.add(btnCheck);
		btnCheck.addActionListener(new Check());

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
		
		rbnBleu.setEnabled(true);

		pnlControl.add(rbnViolet);
		pnlControl.add(lblWriter);
		pnlControl.setBackground(Color.decode("#e6f2ff"));
		
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
		

			
			String[] asOp = {"yes","no" };
			int n = JOptionPane.showOptionDialog(	rootPane, 
													"Etez vous vraiment sur de vouloir quittez ?",
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
	
	class Check implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			c=0;
			cp=0;
			// TODO Auto-generated method stub
			if(gc.currentRound >= 12) {
				
				String[] asOp = {"yes","no" };
				int n = JOptionPane.showOptionDialog(	rootPane, 
														"Voulez vous voir la solution  ?",
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

			}else {
				
				System.out.println("check");
				System.out.println("Tour :" + gc.currentRound);
				int ta=gc.currentRound+1;
				sRes +="<p>Tour : "+ta+"</p>";
	
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
					sRes+="<p> Blanc(s) : " + c + "<br/> Noir(s) : " + cp + "</p>";
				
					//incremnt pour passer au tour suivant 
					gc.nextRound();
				}
				System.out.println("res: "+sRes);
				//affichage du status du resultat de la combinaison 
				lblStatus.setText("<html>"+sRes+"<html/>");
			
			}
			
			
				
		} 
		
	}
}
