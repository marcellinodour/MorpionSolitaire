package fr.dauphine.ja.M1MiageApp.projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HomePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton computer_button;
	private JButton player_button;

	public HomePanel() {		
		setPreferredSize(new Dimension(400, 200));
		setBackground(Color.white);

		JLabel label = new JLabel("Choisir le mode de jeu :");
		add(label);

		computer_button = new JButton("Ordinateur");
		computer_button.addActionListener(this);
		add(computer_button);

		player_button = new JButton("Joueur");
		player_button.addActionListener(this);
		add(player_button);
	}

	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		JFrame f;
		Window w;
		
		if(source == computer_button) {
			f = new MorpionSolitaire(0);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setVisible(true);
	        
	        w = SwingUtilities.getWindowAncestor(this);
	        w.setVisible(false);
	        
		} else if(source == player_button) {
			f = new MorpionSolitaire(1);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setVisible(true);
	        
	        w = SwingUtilities.getWindowAncestor(this);
	        w.setVisible(false);
		}
	}
}
