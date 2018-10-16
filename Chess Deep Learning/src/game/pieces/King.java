package game.pieces;

import java.util.ArrayList;
import java.util.List;
import game.Position;

public class King extends Piece {

    public King(int color, boolean createImgLabel) {
	super(color, Piece.KING, createImgLabel, "King");
    }

    public List<Position> getSpecialMoves() {
	List<Position> out = new ArrayList<>();

	// Rochade
	if (getMoveCounter() == 0) {
	    Piece rockL, rockR;
	    List<Position> weakPositions = new ArrayList<>();
	    int weakY;

	    if (color == Piece.WHITE) {
		rockL = field.getPiece(0, 7);
		rockR = field.getPiece(7, 7);
		weakY = 7;

	    } else {
		rockL = field.getPiece(0, 0);
		rockR = field.getPiece(7, 0);
		weakY = 0;
	    }

	    nextPosition: for (int weakX = 1; weakX < 7; weakX++) {
		Position weakPos = new Position(weakX, weakY);
		if(field.getPiece(weakX, weakY) != null) {
		    weakPositions.add(weakPos);
		    continue nextPosition;
		}
		for (Piece p : field.getPieces()) {
		    if (p.getColor() == enemyColor)
			if (p.getMoves().contains(weakPos)) {
			    weakPositions.add(weakPos);
			    continue nextPosition;
			}
		}
	    }

	    if (rockL != null && rockL.getType() == Piece.ROCK && rockL.getMoveCounter() == 0) {
		if (!(weakPositions.contains(new Position(1, weakY)) || weakPositions.contains(new Position(2, weakY))
			|| weakPositions.contains(new Position(3, weakY)))) {
		    out.add(new Position(2, weakY));
		}
	    }

	    if (rockR != null && rockR.getType() == Piece.ROCK && rockR.getMoveCounter() == 0) {
		if (!(weakPositions.contains(new Position(5, weakY))
			|| weakPositions.contains(new Position(6, weakY)))) {
		    out.add(new Position(6, weakY));
		}
	    }

	}
	return out;
    }
    
    @Override
    public List<Position> getRegularMoves() {
	List<Position> out = super.getRegularMoves();
	
	out.addAll(getSpecialMoves());
	
	return out;
    }
    
    @Override
    public List<Position> getMoves() {
	List<Position> out = new ArrayList<>();

	int x = position.getX();
	int y = position.getY();

	// Move in every direction
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
	out.setMoveCounter(this.getMoveCounter());
	return out;
    }
}
