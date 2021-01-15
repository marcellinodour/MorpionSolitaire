package fr.dauphine.ja.M1MiageApp.projet;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class MorpionSolitaire extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String player_name=null;

	public MorpionSolitaire(int i, String game_version, String player_name) {
		this.player_name = player_name;
		System.out.println(player_name);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		if(i == 0) {
			content.add(new MorpionSolitaireComputerPanel(game_version), BorderLayout.CENTER);
		}else{
			content.add(new MorpionSolitairePlayerPanel(game_version), BorderLayout.CENTER);
		}
		
		setTitle("Morpion Solitaire - version : " + game_version + " - Joueur : " + player_name);
		
		pack();
		setLocationRelativeTo(null);
	}

	public String getPlayer_name() {
		return player_name;
	}
}