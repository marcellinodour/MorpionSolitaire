package fr.dauphine.ja.M1MiageApp.projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class HomePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton computer_button;
	private JButton player_button;
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton game_5T;
	private JRadioButton game_5D;
	private JTextField player_name;
	private String player_name_value = "ANONYMOUS";

	public HomePanel() {
		Dimension size;
		setPreferredSize(new Dimension(350, 200));
		setBackground(Color.white);
		setLayout(null);

		JLabel label = new JLabel("Choisir le mode de jeu :");
		add(label);
		
		JLabel label_player_name = new JLabel("(Facultatif) nom du joueur :");
		add(label_player_name);
		
		player_name = new JTextField(10);
		add(player_name);

		game_5T = new JRadioButton("5T");
		game_5T.setSelected(true);
		add(game_5T);

		game_5D = new JRadioButton("5D");
		add(game_5D);

		bg.add(game_5D);
		bg.add(game_5T);

		computer_button = new JButton("Ordinateur");
		computer_button.addActionListener(this);
		add(computer_button);

		player_button = new JButton("Joueur");
		player_button.addActionListener(this);
		add(player_button);
		
		size = label.getPreferredSize();
		label.setBounds(50, 25, size.width, size.height);
		
		size = game_5T.getPreferredSize();
		game_5T.setBounds(200, 25, size.width, size.height);
		
		size = game_5D.getPreferredSize();
		game_5D.setBounds(250, 25, size.width, size.height);
		
		size = label_player_name.getPreferredSize();
		label_player_name.setBounds(30, 60, size.width, size.height);
		
		size = player_name.getPreferredSize();
		player_name.setBounds(200, 60, size.width, size.height);
		
		size = computer_button.getPreferredSize();
		computer_button.setBounds(100, 100, size.width, size.height);
		
		size = player_button.getPreferredSize();
		player_button.setBounds(200, 100, size.width, size.height);
	}

	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		JFrame f;
		Window w;
		@SuppressWarnings("unused")
		String game_version = null;

		if(game_5T.isSelected()) {
			game_version = "5T";
		}else if(game_5D.isSelected()) {
			game_version = "5D";
		}else {
			JOptionPane.showMessageDialog(this, "Erreur aucun jeu selectionné");
		}


		if(source == computer_button) {
			f = new MorpionSolitaire(0, game_version, "COMPUTER");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);

			w = SwingUtilities.getWindowAncestor(this);
			w.setVisible(false);

		} else if(source == player_button) {
			if((!player_name.getText().trim().equals(""))) {				
				System.out.println(player_name.getText());
				player_name_value = player_name.getText();
			}
			f = new MorpionSolitaire(1, game_version, player_name_value);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);

			w = SwingUtilities.getWindowAncestor(this);
			w.setVisible(false);
		}
	}
}
