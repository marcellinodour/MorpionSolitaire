package fr.dauphine.ja.M1MiageApp.projet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class MorpionSolitaireComputerPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton home_button;
	private Boolean end_of_game = false;
	Grid grid;
	String message = "Silence, je reflechis ...";
	int score;
	Font scoreFont;

	public MorpionSolitaireComputerPanel(String game_version) {
		setPreferredSize(new Dimension(1000, 750));
		setBackground(Color.white);
		setLayout(null);
		
		home_button = new JButton("Retour");
		home_button.addActionListener(this);
		home_button.setBounds(500, 20, 80, 30);
		add(home_button);

		setFont(new Font("SansSerif", Font.BOLD, 16));
		scoreFont = new Font("SansSerif", Font.BOLD, 12);

		grid = new Grid(35, 9, game_version);

		start();
	}

	public final void start() {
		new Thread(new Runnable() {
			public void run() {
				Random rand = new Random();
				while (true) {
					try {

						List<Point> moves = grid.possibleMoves();
						Point move = moves.get(rand.nextInt(moves.size()));

						grid.computerMove(move.y, move.x, score + 1);

						score++;

						if (grid.possibleMoves().isEmpty()) {
							end_of_game = true;
							repaint();
							break;
						}

						repaint();
						Thread.sleep(100L);
					} catch (InterruptedException ignored) {
						System.out.println(ignored);
					}
				}
			}
		}).start();
	}

	@Override
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		grid.draw(g, getWidth(), getHeight());

		if (end_of_game) {
			message = "Fini ... ";
			message += "Et ça fait " + score + " points pour moi.";
		}

		g.setColor(Color.white);
		g.fillRect(0, getHeight() - 50, getWidth(), getHeight() - 50);

		g.setColor(Color.lightGray);
		g.setStroke(new BasicStroke(1));
		g.drawLine(0, getHeight() - 50, getWidth(), getHeight() - 50);

		g.setColor(Color.darkGray);

		g.setFont(getFont());
		g.drawString(message, 20, getHeight() - 18);

		String s2 = "Score " + String.valueOf(score);
		g.drawString(s2, getWidth() - 100, getHeight() - 20);
		
	}

	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		JFrame f = new Home();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		Window w = SwingUtilities.getWindowAncestor(this);
		w.setVisible(false);
	}
}