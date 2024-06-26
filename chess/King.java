package chess;

import java.util.ArrayList;

public class King extends Piece {
    private boolean firstMove;

    public King(PieceType pieceType, PieceFile pieceFile, int pieceRank, String color) {
        super(pieceType, pieceFile, pieceRank, color);
        firstMove = true;
    }

    public boolean canMove(String fileRankfinal) {
        ArrayList<String> possibleMoves = new ArrayList<String>();
        char fileStart = (char) (this.pieceFile.toString().charAt(0) - 1);
        int rankStart = this.pieceRank - 1;
        for (char c = fileStart; c <= fileStart + 2; c++) {
            for (int i = rankStart; i <= rankStart + 2; i++) {
                if ((c >= 'a' && c <= 'h') && (i <= 8 && i >= 0)) {
                    String pos = Character.toString(c) + i;
                    if (Chess.openSquare(pos) || ((Chess.pieceAt(pos) != null)
                            && !this.color.equalsIgnoreCase(((Piece) Chess.pieceAt(pos)).getColor()))) {
                        possibleMoves.add(pos);
                    }
                }
            }
        }
        if (possibleMoves.contains(fileRankfinal)) {
            return true;
        }
        return false;
    }

    public boolean hasFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean t) {
        firstMove = t;
    }
}