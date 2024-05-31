package chess;

public class Rook extends Piece {
    private boolean firstMove;

    public Rook(PieceType pieceType, PieceFile pieceFile, int pieceRank, String color) {
        super(pieceType, pieceFile, pieceRank, color);
        firstMove = true;
    }

    public boolean canMove(String fileRankfinal) {
        if (Chess.positionRank(fileRankfinal) == this.pieceRank
                && !((this.pieceFile).name()).equals(Chess.positionFile(fileRankfinal))) {
            int min, max;
            if (((this.pieceFile).name().compareTo(Chess.positionFile(fileRankfinal))) > 0) {
                min = Chess.positionFile(fileRankfinal).charAt(0) - 96;
                max = (this.pieceFile).name().charAt(0) - 96;
            } else {
                max = Chess.positionFile(fileRankfinal).charAt(0) - 96;
                min = (this.pieceFile).name().charAt(0) - 96;
            }
            for (int i = min + 1; i <= max; i++) {
                if (i == max) {
                    if ((((this.pieceFile).name().compareTo(Chess.positionFile(fileRankfinal))) > 0)
                            && (Chess.openSquare(fileRankfinal)
                                    || ((Chess.pieceAt(fileRankfinal) != null) && !this.color
                                            .equalsIgnoreCase(((Piece) Chess.pieceAt(fileRankfinal)).getColor())))) {
                        return true;
                    }
                    if ((((this.pieceFile).name().compareTo(Chess.positionFile(fileRankfinal))) < 0)
                            && ((Chess.openSquare(Character.toString(((char) (i + 96))) + this.pieceRank))
                                    || ((Chess.pieceAt(fileRankfinal) != null) && !this.color
                                            .equalsIgnoreCase(((Piece) Chess.pieceAt(fileRankfinal)).getColor())))) {
                        return true;
                    }
                }
                if (!Chess.openSquare(Character.toString(((char) (i + 96))) + this.pieceRank)) {
                    return false;
                }
            }
            return true;
        }

        else if (((this.pieceFile).name()).equals(Chess.positionFile(fileRankfinal))
                && Chess.positionRank(fileRankfinal) != this.pieceRank) {
            int min, max;
            if ((this.pieceRank - Chess.positionRank(fileRankfinal)) > 0) {
                min = Chess.positionRank(fileRankfinal);
                max = this.pieceRank;
            } else {
                max = Chess.positionRank(fileRankfinal);
                min = this.pieceRank;
            }
            for (int i = min + 1; i <= max; i++) {
                if (i == max) {
                    if (((this.pieceRank - Chess.positionRank(fileRankfinal)) > 0) && (Chess.openSquare(fileRankfinal)
                            || ((Chess.pieceAt(fileRankfinal) != null) && !this.color
                                    .equalsIgnoreCase(((Piece) Chess.pieceAt(fileRankfinal)).getColor())))) {
                        return true;
                    }
                    if (((this.pieceRank - Chess.positionRank(fileRankfinal)) < 0)
                            && ((Chess.openSquare((this.pieceFile).name() + i))
                                    || ((Chess.pieceAt(fileRankfinal) != null) && !this.color
                                            .equalsIgnoreCase(((Piece) Chess.pieceAt(fileRankfinal)).getColor())))) {
                        return true;
                    }
                }
                if (!Chess.openSquare((this.pieceFile).name() + i)) {
                    return false;
                }
            }
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
