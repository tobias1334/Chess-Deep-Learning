package game.pieces;

import java.util.ArrayList;
import java.util.List;

import game.Position;

public class Bishop extends Piece {

	public Bishop(int color, boolean createImgLabel) {
		super(color, Piece.BISHOP, createImgLabel, "Bishop");
	}

	@Override
	public List<Position> getMoves() {
		List<Position> out = new ArrayList<>();

		int x = position.getX();
		int y = position.getY();

		while (field.getColor(++x, ++y) == Piece.NONE)
			out.add(new Position(x, y));
		if (field.getColor(x, y) == enemyColor)
			out.add(new Position(x, y));

		x = position.getX();
		y = position.getY();

		while (field.getColor(--x, ++y) == Piece.NONE)
			out.add(new Position(x, y));
		if (field.getColor(x, y) == enemyColor)
			out.add(new Position(x, y));

		x = position.getX();
		y = position.getY();

		while (field.getColor(++x, --y) == Piece.NONE)
			out.add(new Position(x, y));
		if (field.getColor(x, y) == enemyColor)
			out.add(new Position(x, y));

		x = position.getX();
		y = position.getY();

		while (field.getColor(--x, --y) == Piece.NONE)
			out.add(new Position(x, y));
		if (field.getColor(x, y) == enemyColor)
			out.add(new Position(x, y));

		return out;
	}

	@Override
	public Piece clone() {
		Piece out = new Bishop(color, false);
		out.setHasBeenMoved(this.getHasBeenMoved());
		return out;
	}
}