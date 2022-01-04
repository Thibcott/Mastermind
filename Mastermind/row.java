/* 	Mastermind projet 
 * 	MI-IN DEV3 EPTM Sion
 *	Thibaut Cotture Hopital du Valais 
 *	M120
 * 
 * 	Row
 */
package Mastermind;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Row extends JPanel implements MouseListener {

	private Color[] colors = { Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK };
	private int round;
	int ihaveBlack = 0;
	boolean bHaveBlack = false;
	
	public Row() {}
	public Row(int round) {
		super();
		this.round=round;
	}

	public Row(Color[] c) {
		this.colors = c;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int x = 5;
		int y = 2;
		int w = (getWidth() / 4) - 10;

		// TODO Auto-generated method stub
		
		for (int i = 0; i < 4; i++) {
			g.setColor(this.colors[i]);
			g.fillOval(x, y, w, w);
			
			x += w + 10;

		}

	}
	public  Color[] getColor() {
		return colors;
	}
	public int getCurrentRound() {
		return round;
		
	}
	public Boolean getHaveBlack() {
		return bHaveBlack;
	}
	
	public void reset() {
		
		for(int i = 0; i < 4;i++) {
			this.colors[i] = Color.BLACK;
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(round == GameController.getInstance().currentRound) {
			int roundId = e.getX() / (this.getWidth() / 4);
			this.colors[roundId] = GameController.getInstance().currentColor;
			for (Color color : colors) {
				//System.out.println(color);
				if(color==Color.black) {
					ihaveBlack++;
					//System.out.println("y a des noirs 1");
				}
			}
			if(ihaveBlack>=1) {
				//System.out.println("y a des noirs 2");
				bHaveBlack = false;
				//btnCheck.setEnabled(bHaveBlack);
			} else {
				bHaveBlack = true;
				//btnCheck.setEnabled(bHaveBlack);

			}
			//redessine
			repaint();
		
		}else {
			System.out.println("is not clickable");
		}
		
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
}
