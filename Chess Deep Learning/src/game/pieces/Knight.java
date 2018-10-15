package game.pieces;

import java.util.ArrayList;
import java.util.List;

import game.Position;

public class Knight extends Piece {

	public Knight(int color, boolean createImgLabel) {
		super(color, Piece.KNIGHT, createImgLabel, "Knight");
	}

	@Override
	public List<Position> getMoves() {
		List<Position> out = new ArrayList<>();

		int x, y, c;

		for (int d1 = -2; d1 <= 2; d1 += 4)
			for (int d2 = -1; d2 <= 1; d2 += 2) {
				x = position.getX() + d1;
				y = position.getY() + d2;
				c = field.getColor(x, y);
				if (c == enemyColor || c == Piece.NONE)
					out.add(new Position(x, y));
				x = position.getX() + d2;
				y = position.getY() + d1;
				c = field.getColor(x, y);
				if (c == enemyColor || c == Piece.NONE)
					out.add(new Position(x, y));
			}

		return out;
	}
	
	@Override
	public Piece clone() {
		Piece out = new Knight(color, false);
		out.setHasBeenMoved(this.getHasBeenMoved());
		return out;
	}
}
