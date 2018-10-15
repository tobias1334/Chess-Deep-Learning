package game.pieces;

import java.util.ArrayList;
import java.util.List;

import game.Position;

public class Pawn extends Piece {

	public Pawn(int color, boolean createImgLabel) {
		super(color, Piece.PAWN, createImgLabel, "Pawn");
	}

	@Override
	public List<Position> getMoves() {
		List<Position> out = new ArrayList<Position>();
		
		int newY, newY2, startY;

		if (color == Piece.BLACK) {
			newY = position.getY() + 1;
			newY2 = 3;
			startY = 1;

		} else {
			newY = position.getY() - 1;
			newY2 = 4;
			startY = 6;
		}

		// Pawn is moving forward
		if (field.isEmpty(position.getX(), newY)) {
			out.add(new Position(position.getX(), newY));

			// Pawn is moving forward 2 steps when he's at his start position
			if (position.getY() == startY && field.isEmpty(position.getX(), newY2))
				out.add(new Position(position.getX(), newY2));
		}

		// Pawn can capture
		for (int dx = -1; dx <= 1; dx += 2) {
			int x = position.getX() + dx;
			int c = field.getColor(x, newY);
			if (c == enemyColor)
				out.add(new Position(x, newY));
		}
		return out;
	}
	
	@Override
	public Piece clone() {
		Piece out = new Pawn(color, false);
		out.setHasBeenMoved(this.getHasBeenMoved());
		return out;
	}
}