package game;

import java.util.ArrayList;
import java.util.List;

import game.pieces.*;
import gui.ChessboardPanel;

public class Field {

    private Piece[][] field;
    private List<Piece> pieces;
    private int currentPlayer;

    private ChessboardPanel chessboardPanel;

    private boolean validCoordinates(int x, int y) {
	return x < 8 && x >= 0 && y < 8 && y >= 0;
    }

    public Field() {
	field = new Piece[8][8];
	pieces = new ArrayList<>();
    }

    public boolean isEmpty(int x, int y) {
	if (validCoordinates(x, y))
	    return (field[x][y] == null) ? true : false;
	return false;
    }

    public int getColor(int x, int y) {
	if (!validCoordinates(x, y))
	    return -1;
	if (field[x][y] == null)
	    return Piece.NONE;
	return field[x][y].getColor();
    }

    public void setStone(Piece piece, int x, int y) {
	if (validCoordinates(x, y) && !pieces.contains(piece)) {
	    field[x][y] = piece;
	    pieces.add(piece);
	    piece.setField(this);
	    piece.setPosition(x, y);
	}
    }

    public int getCurrentPlayer() {
	return currentPlayer;
    }

    public void resetStartingGrid(ChessboardPanel cp) {
	chessboardPanel = cp;
	currentPlayer = Piece.WHITE;

	// Pawns
	for (int x = 0; x < 8; x++) {
	    Pawn pawn = new Pawn(Piece.BLACK, true);
	    setStone(pawn, x, 1);
	    cp.add(pawn.getImgLabel());

	    pawn = new Pawn(Piece.WHITE, true);
	    setStone(pawn, x, 6);
	    cp.add(pawn.getImgLabel());
	}

	// Rocks
	for (int x = 0; x < 8; x += 7) {
	    Rock rock = new Rock(Piece.BLACK, true);
	    setStone(rock, x, 0);
	    cp.add(rock.getImgLabel());

	    rock = new Rock(Piece.WHITE, true);
	    setStone(rock, x, 7);
	    cp.add(rock.getImgLabel());
	}

	// Knights
	for (int x = 1; x < 8; x += 5) {
	    Knight knight = new Knight(Piece.BLACK, true);
	    setStone(knight, x, 0);
	    cp.add(knight.getImgLabel());

	    knight = new Knight(Piece.WHITE, true);
	    setStone(knight, x, 7);
	    cp.add(knight.getImgLabel());
	}

	// Bishops
	for (int x = 2; x < 8; x += 3) {
	    Bishop bishop = new Bishop(Piece.BLACK, true);
	    setStone(bishop, x, 0);
	    cp.add(bishop.getImgLabel());

	    bishop = new Bishop(Piece.WHITE, true);
	    setStone(bishop, x, 7);
	    cp.add(bishop.getImgLabel());
	}

	King king = new King(Piece.BLACK, true);
	setStone(king, 4, 0);
	cp.add(king.getImgLabel());

	king = new King(Piece.WHITE, true);
	setStone(king, 4, 7);
	cp.add(king.getImgLabel());

	Queen queen = new Queen(Piece.BLACK, true);
	setStone(queen, 3, 0);
	cp.add(queen.getImgLabel());

	queen = new Queen(Piece.WHITE, true);
	setStone(queen, 3, 7);
	cp.add(queen.getImgLabel());
    }

    // this method is needed to move a piece at the end. with this method you aren't
    // allowed to move piece, which opens a king castled moved
    public boolean movePieceRegular(Position p1, Position p2) {
	Piece p = field[p1.getX()][p1.getY()];
	if (p == null)
	    return false;
	if (p.getRegularMoves().contains(p2))
	    return movePiece(p1, p2);
	return false;
    }

    // this method is needed to calculate all possible moves
    public boolean movePiece(Position p1, Position p2) {
	Piece p = field[p1.getX()][p1.getY()];

	if (p == null) // no piece can't be moved
	    return false;

	// Rochade
	if (p.getType() == Piece.KING) {
	    King king = (King) p;
	    if (king.getSpecialMoves().contains(p2)) {
		Piece rock;
		if (p2.getX() == 1) {
		    rock = field[0][p2.getY()];
		    rock.setPosition(2, p2.getY());
		    field[0][p2.getY()] = null;
		    field[2][p2.getY()] = rock;

		} else {
		    rock = field[7][p2.getY()];
		    rock.setPosition(5, p2.getY());
		    field[7][p2.getY()] = null;
		    field[5][p2.getY()] = rock;
		}

		king.setPosition(p2);
		field[p1.getX()][p1.getY()] = null;
		field[p2.getX()][p2.getY()] = king;
		king.moveCounter();
		rock.moveCounter();
	    }
	}

	if (p.getMoves().contains(p2) && p.getColor() == currentPlayer) { // check for legal move & only pieces with the
									  // right color can be moved
	    Piece cp = field[p2.getX()][p2.getY()];

	    // en passant
	    if (p.getType() == Piece.PAWN && p1.getX() != p2.getX() && cp == null) {
		int x = p2.getX();
		int y = p2.getY() + ((p.getColor() == Piece.WHITE) ? 1 : -1);
		cp = field[x][y];
		field[x][y] = null;
	    }

	    if (cp != null) {
		if (cp.availableImgLabel())
		    chessboardPanel.remove(cp.getImgLabel()); // remove castled piece
		pieces.remove(cp);
	    }

	    // mark the last moved piece
	    for (Piece plm : pieces)
		plm.setLastMoved(false);
	    p.setLastMoved(true);

	    // reposition piece
	    field[p2.getX()][p2.getY()] = p;
	    field[p1.getX()][p1.getY()] = null;
	    p.setPosition(p2);
	    p.moveCounter();

	    // change turn
	    if (currentPlayer == Piece.WHITE)
		currentPlayer = Piece.BLACK;
	    else
		currentPlayer = Piece.WHITE;
	    return true;
	}
	return false;
    }

    public Piece getPiece(int x, int y) {
	if (validCoordinates(x, y))
	    return field[x][y];
	return null;
    }

    public boolean checkCheck(Piece[][] field, int color) {
	for (Piece p : pieces) {
	    if (p.getColor() == color)
		continue;
	    for (Position pos : p.getMoves()) {
		Piece castledPiece = field[pos.getX()][pos.getY()];
		if (castledPiece == null)
		    continue;
		if (castledPiece.getType() == Piece.KING) {
//					System.out.println(p.getPosition() + " check by " + p.getType());
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean checkCheck(int color) {
	return checkCheck(field, color);
    }

    public List<Piece> getPieces() {
	return pieces;
    }

    public Field clone() {
	Field out = new Field();
	out.setCurrentPlayer(currentPlayer);
	for (int x = 0; x < 8; x++)
	    for (int y = 0; y < 8; y++)
		if (field[x][y] != null)
		    out.setStone(field[x][y].clone(), x, y);
	return out;
    }

    public void setCurrentPlayer(int color) {
	currentPlayer = color;
    }

    @Override
    public String toString() {
	String out = "";

	for (int y = 0; y < 8; y++) {
	    out += "|";
	    for (int x = 0; x < 8; x++) {
		if (field[x][y] == null)
		    out += " |";
		else
		    out += field[x][y].getType() + "|";
	    }
	    out += "\n";
	}

	return out;
    }
}
