package fr.dauphine.ja.M1MiageApp.projet;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class MorpionSolitaire extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MorpionSolitaire(int i) {
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		if(i == 0) {
			MorpionSolitaireComputerPanel panel = new MorpionSolitaireComputerPanel();
			content.add(panel, BorderLayout.CENTER);
		}else{
			MorpionSolitairePlayerPanel panel = new MorpionSolitairePlayerPanel();
			content.add(panel, BorderLayout.CENTER);
		}
		
		setTitle("Morpion Solitaire");
		pack();
		setLocationRelativeTo(null);
	}
}