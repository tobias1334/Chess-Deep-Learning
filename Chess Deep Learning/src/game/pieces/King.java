package game.pieces;

import java.util.ArrayList;
import java.util.List;

import game.Position;

public class King extends Piece {

	public King(int color, boolean createImgLabel) {
		super(color, Piece.KING, createImgLabel, "King");
	}

	@Override
	public List<Position> getMoves() {
		List<Position> out = new ArrayList<>();

		int x = position.getX();
		int y = position.getY();

		for (int dx = -1; dx <= 1; dx++)
			for (int dy = -1; dy <= 1; dy++) {
				int c = field.getColor(x + dx, y + dy);
				if (c == enemyColor || c == Piece.NONE)
					out.add(new Position(x + dx, y + dy));
			}

		return out;
	}

	@Override
	public Piece clone() {
		Piece out = new King(color, false);
		out.setHasBeenMoved(this.getHasBeenMoved());
		return out;
	}
}
