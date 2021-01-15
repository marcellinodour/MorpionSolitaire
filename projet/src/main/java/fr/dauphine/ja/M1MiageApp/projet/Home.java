package fr.dauphine.ja.M1MiageApp.projet;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class Home extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HomePanel panel;

	public Home(){
		Container content = getContentPane();
		panel = new HomePanel();
		content.add(panel);
		setTitle("Home - Morpion Solitaire");
		pack();
		setLocationRelativeTo(null);
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		JFrame f = new Home();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}

}
