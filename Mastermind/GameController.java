package Mastermind;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import EX2NA.EXAMEN02.pnlMouseListener;

public class GameController {

	public  Color currentColor = Color.BLACK;
	public  int currentRound = 0;
	public   List<Row> alRow = new ArrayList<>();
	
	private static GameController instance = null; 
	
	public static GameController getInstance() {
		if(instance==null) {
			instance = new GameController();
		}
		return instance;
	}
	
	
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
	
	//Check si la solution et la meme que la proposition
	//si  0 : pas correct
	//si  1 : bonne couleur
	//si  2 : bonne couleur a la bonne position 
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
					System.out.println("pareil");
					res[i]=2;
				}
			}
		}


		return res;	
	}

	public void setActiveRound() {
		for(int i =0;i < alRow.size();i++) {
			if(currentRound==i) {
				alRow.get(i).setBackground(Color.decode("#ccefff"));	
			}else {
				alRow.get(i).setBackground(Color.white);
			}
		}
	}
	
	public void reset() {
		System.out.println("appel de la function 'reset'");
		currentRound = 0;
		for (Row row : alRow) {
			row.reset();
		}
		setActiveRound();
		
	}
	
	public void nextRound() {
		currentRound++;
		setActiveRound();
		
	}
	


}
