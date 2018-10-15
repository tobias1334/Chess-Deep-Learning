package game.pieces;

import java.util.ArrayList;
import java.util.List;

import game.Field;
import game.Position;
import gui.ImageLabel;

public abstract class Piece {
	public static final int NONE = 0;
	public static final int WHITE = 1;
	public static final int BLACK = 2;

	public static final int PAWN = 0;
	public static final int ROCK = 1;
	public static final int BISHOP = 2;
	public static final int KNIGHT = 3;
	public static final int QUEEN = 4;
	public static final int KING = 5;

	public static final String PATH_PNG = "src/gui/png/";

	protected int color, enemyColor;
	protected Position position;
	protected Field field;
	protected ImageLabel imgLabel;

	private int moveCounter;
	private int type;

	public Piece(int color, int type, boolean createImgLabel, String fileName) {
		position = new Position();
		this.color = color;
		this.type = type;
		moveCounter = 0;

		if (color == WHITE) {
			enemyColor = BLACK;
			fileName += "White.png";
		} else {
			enemyColor = WHITE;
			fileName += "Black.png";
		}

		if (createImgLabel)
			imgLabel = new ImageLabel(PATH_PNG + fileName);
	}

	public int getColor() {
		return color;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public ImageLabel getImgLabel() {
		return imgLabel;
	}

	public int getType() {
		return type;
	}

	public abstract List<Position> getMoves();

	public abstract Piece clone();

	public List<Position> getRegularMoves() {
		if (field.getCurrentPlayer() == color)
			return removeIllegalMoves(getMoves());
		return new ArrayList<Position>();
	}

	private List<Position> removeIllegalMoves(List<Position> moves) {
		List<Position> out = new ArrayList<>();
		for (Position pos : moves) {
			Field cloneField = field.clone();
			cloneField.movePiece(position, pos);

			boolean check = cloneField.checkCheck(color);
//			System.out.println(position + " -> " + pos + " check: " + check + ((color == Piece.WHITE) ? " white " : " black ") + cloneField.getPiece(pos.getX(), pos.getY()).getType());
//			System.out.println(cloneField);
			if (!check)
				out.add(pos);

		}
		return out;
	}

	public boolean availableImgLabel() {
		return !(imgLabel == null);
	}

	public void moveCounter() {
		moveCounter++;
	}
	
	public void setMoveCounter(int mc) {
		moveCounter = mc;
	}
	
	public int getMoveCounter() {
		return moveCounter;
	}
}
