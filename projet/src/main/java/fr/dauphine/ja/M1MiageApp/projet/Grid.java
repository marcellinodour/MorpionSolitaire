package fr.dauphine.ja.M1MiageApp.projet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dauphine.ja.M1MiageApp.projet.Grid.Choice;
import fr.dauphine.ja.M1MiageApp.projet.Grid.Line;
import fr.dauphine.ja.M1MiageApp.projet.Grid.Result;

class Grid {
	enum Result {
		OK, KO, ERROR
	}

	final int EMPTY = 0, POINT = 1, HORI = 2, VERT = 4, DIUP = 8, DIDO = 16,
			HORI_END = 32, VERT_END = 64, DIUP_END = 128, DIDO_END = 256,
			CAND = 512, ORIG = 1024, HINT = 2048;

	final int[] basePoints = {120, 72, 72, 975, 513, 513, 975, 72, 72, 120};

	int cellSize, pointSize, halfCell, centerX, centerY, origX, origY;
	int minC, minR, maxC, maxR;

	int[][] points;
	Map<Point, Choice> choices;
	List<Choice> candidates;

	Map<Point, Integer> computerMoves;
	List<Line> computerLines;
	Map<Point, Integer> playerMoves;
	List<Line> playerLines;
	String game_version = null;

	class Line {
		final Point p1, p2;

		Line(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
	}

	class Choice {
		int[] dir;

		@SuppressWarnings("hiding")
		List<Point> points;

		Choice(List<Point> p, int[] d) {
			points = p;
			dir = d;
		}
	}

	public Grid(int cs, int ps, String game_version) {
		cellSize = cs;
		pointSize = ps;
		halfCell = cs / 2;
		points = new int[50][50];
		minC = minR = 0;
		maxC = maxR = 50;
		this.game_version = game_version;
		newGame();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void newGame() {
		for (int r = minR; r < maxR; r++)
			for (int c = minC; c < maxC; c++)
				points[r][c] = EMPTY;

		choices = new HashMap<Point, Choice>();
		candidates = new ArrayList();
		minC = minR = 18;
		maxC = maxR = 31;

		computerMoves = new HashMap<Point, Integer>();
		computerLines = new ArrayList<Line>();
		playerMoves = new HashMap<Point, Integer>();
		playerLines = new ArrayList<Line>();

		// cross
		for (int r = 0; r < 10; r++)
			for (int c = 0; c < 10; c++)
				if ((basePoints[r] & (1 << c)) != 0)
					points[20 + r][20 + c] = POINT;
	}

	public void draw(Graphics2D g, int w, int h) {
		centerX = w / 2;
		centerY = h / 2;
		origX = centerX - halfCell - 24 * cellSize;
		origY = centerY - halfCell - 24 * cellSize;

		// grid
		g.setColor(Color.lightGray);

		int x = (centerX - halfCell) % cellSize;
		int y = (centerY - halfCell) % cellSize;

		for (int i = 0; i <= w / cellSize; i++)
			g.drawLine(x + i * cellSize, 0, x + i * cellSize, h);

		for (int i = 0; i <= h / cellSize; i++)
			g.drawLine(0, y + i * cellSize, w, y + i * cellSize);

		// lines
		g.setStroke(new BasicStroke(2));

		// computerLines
		drawLines(computerLines, g, Color.red);

		// playerLines
		drawLines(playerLines, g, Color.blue);

		// points
		for (int r = minR; r < maxR; r++)
			for (int c = minC; c < maxC; c++) {
				int p = points[r][c];

				if (p == EMPTY)
					continue;

				if ((p & ORIG) != 0)
					g.setColor(Color.red);

				else if ((p & CAND) != 0)
					g.setColor(Color.green);

				else if ((p & HINT) != 0) {
					g.setColor(Color.lightGray);
					points[r][c] &= ~HINT;
				} else
					g.setColor(Color.darkGray);

				drawPoint(g, c, r);
			}

	}

	private void drawLines(List<Line> lines, Graphics2D g, Color color) {
		if(lines.size() > 0) {
			for (int i = 0 ; i < lines.size(); i++) {
				Line line = lines.get(i);
				g.setColor(color);
				int x1 = origX + line.p1.x * cellSize;
				int y1 = origY + line.p1.y * cellSize;
				int x2 = origX + line.p2.x * cellSize;
				int y2 = origY + line.p2.y * cellSize;
				g.drawLine(x1, y1, x2, y2);
			}
		}
	}

	private void drawPoint(Graphics2D g, int x, int y) {
		g.setFont(new Font("SansSerif", Font.PLAIN, 12));
		String score = null;

		if(computerMoves.containsKey(new Point(x, y))) {
			drawMove(computerMoves, score, x, y, g, Color.red);

		} else if (playerMoves.containsKey(new Point(x, y))) {
			drawMove(playerMoves, score, x, y, g, Color.blue);
		}
		else {
			x = origX + x * cellSize - (pointSize / 2);
			y = origY + y * cellSize - (pointSize / 2);

			g.fillOval(x, y, pointSize, pointSize);
		}     

	}

	private void drawMove(Map<Point, Integer> moves, String score, int x, int y, Graphics2D g, Color color) {
		FontMetrics fm = g.getFontMetrics();
		double length_score;

		score = moves.get(new Point(x, y)).toString();
		length_score = fm.getStringBounds(score, g).getWidth();

		x = origX + x * cellSize - (pointSize / 2);
		y = origY + y * cellSize - (pointSize / 2);

		g.setColor(Color.white);
		g.fillOval(x - 5, y - 5, 20, 20);
		g.setColor(Color.darkGray);
		g.drawOval(x - 5, y - 5, 20, 20);
		g.setColor(color);
		g.drawString(score, x - (int) (length_score/2) + 5, y + (fm.getMaxAscent()/2) + 3);
	}

	public Result computerMove(int r, int c, int score) {
		checkLines(r, c);
		if (candidates.size() > 0) {
			Choice choice = candidates.get(0);
			addLine(choice.points, choice.dir, 0);
			computerMoves.put(new Point(c,r), score);
			return Result.OK;
		}
		return Result.KO;
	}

	public Result playerMove(float x, float y, int score) {
		int r = Math.round((y - origY) / cellSize);
		int c = Math.round((x - origX) / cellSize);

		// see if inside active area
		if (c < minC || c > maxC || r < minR || r > maxR)
			return Result.KO;

		// only process when mouse click is close enough to grid point
		int diffX = (int) Math.abs(x - (origX + c * cellSize));
		int diffY = (int) Math.abs(y - (origY + r * cellSize));
		if (diffX > cellSize / 5 || diffY > cellSize / 5)
			return Result.KO;

		// did we have a choice in the previous turn
		if ((points[r][c] & CAND) != 0) {
			Choice choice = choices.get(new Point(c, r));
			addLine(choice.points, choice.dir, 1);
			playerMoves.put(new Point(c, r), score);
			for (Choice ch : choices.values()) {
				for (Point p : ch.points)
					points[p.y][p.x] &= ~(CAND | ORIG);
			}
			choices.clear();
			return Result.OK;
		}

		if (points[r][c] != EMPTY || choices.size() > 0)
			return Result.KO;

		checkLines(r, c);

		if (candidates.size() == 1) {
			Choice choice = candidates.get(0);
			addLine(choice.points, choice.dir, 1);
			playerMoves.put(new Point(c, r), score);
			return Result.OK;
		} else if (candidates.size() > 1) {
			// we can make more than one line
			points[r][c] |= ORIG;
			for (Choice ch : candidates) {
				List<Point> cand = ch.points;
				Point p = cand.get(cand.size() - 1);
				if (p.equals(new Point(c, r)))
					p = cand.get(0);
				points[p.y][p.x] |= CAND;
				choices.put(p, ch);
			}
			return Result.ERROR;
		}

		return Result.KO;
	}

	private void checkLine(int dir, int end, int r, int c, int rIncr, int cIncr) {
		List<Point> result = new ArrayList<Point>(5);
		for (int i = -4; i < 1; i++) {
			result.clear();
			for (int j = 0; j < 5; j++) {
				int y = r + rIncr * (i + j);
				int x = c + cIncr * (i + j);
				int p = points[y][x];
				if (p != EMPTY && (p & dir) == 0 || (p & end) != 0 || i + j == 0)
					result.add(new Point(x, y));
				else
					break;
			}

			if (result.size() == 5) {
				if(game_version.equals("5T")) {
					int cpt_5T = 0;
					cpt_5T = 0;
					for( Point k : result) {
						int k_2 = points[k.x][k.y];
						if(k_2 == EMPTY) {
							cpt_5T++;
						}
					}
					if(cpt_5T == 1) {
						candidates.add(new Choice(new ArrayList<Point>(result), new int[]{dir, end}));
					}
				} else {
					candidates.add(new Choice(new ArrayList<Point>(result), new int[]{dir, end}));
				}
			}
		}
	}

	private void checkLines(int r, int c) {
		candidates.clear();
		checkLine(HORI, HORI_END, r, c, 0, 1);
		checkLine(VERT, VERT_END, r, c, 1, 0);
		checkLine(DIUP, DIUP_END, r, c, -1, 1);
		checkLine(DIDO, DIDO_END, r, c, 1, 1);
	}

	private void addLine(List<Point> line, int[] dir, int state) {
		Point p1 = line.get(0);
		Point p2 = line.get(line.size() - 1);

		// mark end points for 5T
		points[p1.y][p1.x] |= dir[1];
		points[p2.y][p2.x] |= dir[1];

		//lines.add(new Line(p1, p2));

		if (state == 0){
			computerLines.add(new Line(p1, p2));
		}

		if (state == 1){
			playerLines.add(new Line(p1, p2));
		}


		for (Point p : line)
			points[p.y][p.x] |= dir[0];

		// growable active area
		minC = Math.min(p1.x - 1, Math.min(p2.x - 1, minC));
		maxC = Math.max(p1.x + 1, Math.max(p2.x + 1, maxC));
		minR = Math.min(p1.y - 1, Math.min(p2.y - 1, minR));
		maxR = Math.max(p1.y + 1, Math.max(p2.y + 1, maxR));
	}

	public List<Point> possibleMoves() {
		List<Point> moves = new ArrayList<Point>();
		for (int r = minR; r < maxR; r++)
			for (int c = minC; c < maxC; c++) {
				if (points[r][c] == EMPTY) {
					checkLines(r, c);
					if (candidates.size() > 0)
						moves.add(new Point(c, r));
				}
			}
		return moves;
	}

	public void showHints() {
		for (Point p : possibleMoves())
			points[p.y][p.x] |= HINT;
	}
}
