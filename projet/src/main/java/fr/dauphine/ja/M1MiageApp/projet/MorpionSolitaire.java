package fr.dauphine.ja.M1MiageApp.projet;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
 
public class MorpionSolitaire extends JFrame {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MorpionSolitairePanel panel;
	
	public MorpionSolitaire() {
        Container content = getContentPane();
        content.setLayout(new BorderLayout());
        panel = new MorpionSolitairePanel();
        content.add(panel, BorderLayout.CENTER);
        setTitle("MorpionSolitaire");
        pack();
        setLocationRelativeTo(null);
    }
 
    @SuppressWarnings("static-access")
	public static void main(String[] args) {
        JFrame f = new MorpionSolitaire();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}