package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import game.Field;
import game.Position;
import game.pieces.Piece;

@SuppressWarnings("serial")
public class ChessboardPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final double CIRCLE_SIZE = 0.35;

	private int size, diffSize;
	private Color primaryColor, secundaryColor, choosenColor;

	private Field field;
	private Position choosenPosition;
	private Piece choosenPiece;
	private List<Position> possibleMoves;

	public ChessboardPanel() {
		primaryColor = new Color(26, 72, 118);
		secundaryColor = new Color(170, 196, 231);
		choosenColor = new Color(107, 153, 173);

		this.setBackground(secundaryColor);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		field = new Field();
		possibleMoves = new ArrayList<>();

		choosenPosition = new Position();

		field.resetStartingGrid(this);
	}

	public void setSize(int size) {
		size = size / 8 * 8;
		this.size = size;
		diffSize = size / 8;

		Dimension d = new Dimension(size, size);
//		super.setPreferredSize(d);
		super.setSize(d);
	}

	public void updatePositions() {
		Position pos;
		ImageLabel imgL;

		for (Piece piece : field.getPieces()) {
			pos = piece.getPosition();
			imgL = piece.getImgLabel();
			int x = pos.getX() * diffSize;
			int y = pos.getY() * diffSize;
			imgL.setLocation(x, y);
			imgL.setSize(diffSize, diffSize);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(primaryColor);

		for (int x = 0; x < size; x += 2 * diffSize)
			for (int y = 0; y < size; y += 2 * diffSize)
				g.fillRect(x, y, diffSize, diffSize);

		for (int x = diffSize; x < size; x += 2 * diffSize)
			for (int y = diffSize; y < size; y += 2 * diffSize)
				g.fillRect(x, y, diffSize, diffSize);

		int cx = choosenPosition.getX();
		int cy = choosenPosition.getY();
		if (cx < 8 && cx >= 0 && cy < 8 && cy >= 0) {
			g.setColor(choosenColor);
			g.fillRect(diffSize * cx, diffSize * cy, diffSize, diffSize);
		}

		g.setColor(choosenColor);

		for (Position p : possibleMoves) {
			int s = (int) (diffSize * CIRCLE_SIZE);
			int si = (int) (diffSize * (1 - CIRCLE_SIZE) / 2);
			int x = p.getX() * diffSize + si;
			int y = p.getY() * diffSize + si;

			g.fillOval(x, y, s, s);
		}

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int x, y;
		x = arg0.getX() / diffSize;
		y = arg0.getY() / diffSize;

		choosenPosition.setX(x);
		choosenPosition.setY(y);

		choosenPiece = field.getPiece(x, y);
		if (choosenPiece != null)
			possibleMoves = choosenPiece.getRegularMoves();
		if (possibleMoves.size() == 0) {
			choosenPiece = null;
			choosenPosition.setX(-1);
			choosenPosition.setY(-1);
		}

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX() / diffSize;
		int y = arg0.getY() / diffSize;
				
		if (choosenPosition.getX() >= 0 && choosenPosition.getY() >= 0)
			field.movePieceRegular(choosenPosition, new Position(x, y));

		choosenPosition.setX(-1);
		choosenPosition.setY(-1);
		choosenPiece = null;

		possibleMoves.clear();

		updatePositions();

		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (choosenPiece != null) {
			ImageLabel il = choosenPiece.getImgLabel();
			il.setLocation(e.getX() - diffSize / 2, e.getY() - diffSize / 2);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}
}
