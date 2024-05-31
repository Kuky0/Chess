package chess;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(PieceType pieceType, PieceFile pieceFile, int pieceRank, String color) {
        super(pieceType, pieceFile, pieceRank, color);
    }

    public boolean canMove(String fileRankfinal) {

        ArrayList<String> possibleMoves = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            int currentFileMin = (this.pieceFile.toString().charAt(0) - 96) + i - 2;
            int currentFileMax = (this.pieceFile.toString().charAt(0) - 96) - i + 2;
            int currentRankUp = this.pieceRank + (i + 1);
            int currentRankDown = this.pieceRank - (i + 1);
            if (currentRankUp < 9) {
                if (currentFileMax < 9) {
                    String topMax = Character.toString(currentFileMax + 96) + currentRankUp;
                    if (Chess.openSquare(topMax) || ((Chess.pieceAt(topMax) != null)
                            && !this.color.equalsIgnoreCase(((Piece) Chess.pieceAt(topMax)).getColor()))) {
                        possibleMoves.add(topMax);
                    }
                }
                if (currentFileMin > 0) {
                    String topMin = Character.toString(currentFileMin + 96) + currentRankUp;
                    if (Chess.openSquare(topMin) || ((Chess.pieceAt(topMin) != null)
                            && !this.color.equalsIgnoreCase(((Piece) Chess.pieceAt(topMin)).getColor()))) {
                        possibleMoves.add(topMin);
                    }
                }
            }
            if (currentRankDown > 0) {
                if (currentFileMax < 9) {
                    String bottomMax = Character.toString(currentFileMax + 96) + currentRankDown;
                    if (Chess.openSquare(bottomMax) || ((Chess.pieceAt(bottomMax) != null)
                            && !this.color.equalsIgnoreCase(((Piece) Chess.pieceAt(bottomMax)).getColor()))) {
                        possibleMoves.add(bottomMax);
                    }
                }
                if (currentFileMin > 0) {
                    String bottomMin = Character.toString(currentFileMin + 96) + currentRankDown;
                    if (Chess.openSquare(bottomMin) || ((Chess.pieceAt(bottomMin) != null)
                            && !this.color.equalsIgnoreCase(((Piece) Chess.pieceAt(bottomMin)).getColor()))) {
                        possibleMoves.add(bottomMin);
                    }
                }
            }
        }
        if (possibleMoves.contains(fileRankfinal)) {
            return true;
        }
        return false;
    }
}