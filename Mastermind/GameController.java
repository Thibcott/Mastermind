/* 	Mastermind projet 
 * 	MI-IN DEV3 EPTM Sion
 *	Thibaut Cotture Hopital du Valais 
 *	M120
 * 
 * 	GameController
 */
package Mastermind;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {

	public  Color currentColor = Color.BLACK;
	public  int currentRound = 0;
	public   List<Row> alRow = new ArrayList<>();
	
	private static GameController instance = null; 
	
	//singleton
	public static GameController getInstance() {
		if(instance==null) {
			instance = new GameController();
		}
		return instance;
	}
	
	/** 
	 * methode genrateRandomCombination
	 * elle permet de generer aleatoirement une combinaison 
	 * @author thibcott
	 * */ 
	public  Color[] genrateRandomCombination() {
		Color[] aColor = {	Color.blue,
							Color.orange,
							Color.gray,
							Color.red,
							Color.green,
							Color.magenta};
		List<Color> aCFinal = new ArrayList<>();

		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			// Obtain a number between [0 - 5].
			int r = 0;
			do {
				r= rand.nextInt(6);
			} while (aCFinal.contains(aColor[r]));
			
			aCFinal.add(aColor[r]);
		}
		return aCFinal.toArray(new Color[0]);
	}
	
	//Check si la solution est la meme que la proposition
	//si  0 : pas correct
	//si  1 : bonne couleur
	//si  2 : bonne couleur a la bonne position 
	/**
	 * Check si la solution est la meme que la proposition
		si  0 : pas correct
		si  1 : bonne couleur
		si  2 : bonne couleur a la bonne position
		@author thibcott 
	 * */
	public int[] checkSol(Color[] sol,Color[] Proposition) {
		
		int[] res = new int[4];
		boolean isBlack = false;
		
		for (Color color : Proposition) {
			System.out.println(color);

		}
		System.out.println("-");
		for (Color color : sol) {
			System.out.println(color);
		}

		
		//verifie la couleur
		for (int  i = 0; i < sol.length; i++) {
			for (int j = 0; j < Proposition.length; j++) {
				if(Proposition[j]==sol[i]) {
					//System.out.println("bonne couleur");
					res[j] = 1;
				} else {
					//System.out.println("mauvaise couleur");
				}
				
			}
		}
		
		for (int i = 0; i < res.length; i++) {
			if(res[i]==1) {
				if(Proposition[i]==sol[i]) {
					//System.out.println("pareil");
					res[i]=2;
				}
			}
		}


		return res;	
	}

	/**
	 * methode qui permet pour set une ligne en mode active
	 * 
	 * @author thibcott
	 * */
	public void setActiveRound() {
		for(int i =0;i < alRow.size();i++) {
			if(currentRound==i) {
				alRow.get(i).setBackground(Color.decode("#ccefff"));	
			}else {
				alRow.get(i).setBackground(Color.white);
			}
		}
	}
	
	/**
	 * Methode qui permet de recommanecer la partie du jeu 
	 * 
	 * @author thibcott
	 * */
	public void reset() {
		currentRound = 0;
		for (Row row : alRow) {
			row.reset();
		}
		setActiveRound();
	}
	
	/**
	 * Methode qui permet de passer au tour suivant 
	 * 
	 * @author thibcot
	 **/
	public void nextRound() {
		currentRound++;
		setActiveRound();
	}
	


}
