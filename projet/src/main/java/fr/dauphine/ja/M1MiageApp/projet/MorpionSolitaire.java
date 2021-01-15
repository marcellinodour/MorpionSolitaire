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
			setTitle("Morpion Solitaire - Ordinateur");
			content.add(new MorpionSolitaireComputerPanel(), BorderLayout.CENTER);
		}else{
			setTitle("Morpion Solitaire - Joueur");
			content.add(new MorpionSolitairePlayerPanel(), BorderLayout.CENTER);
		}
		
		pack();
		setLocationRelativeTo(null);
	}
}