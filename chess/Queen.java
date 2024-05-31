package chess;

public class Queen extends Piece {
    public Queen(PieceType pieceType, PieceFile pieceFile, int pieceRank, String color) {
        super(pieceType, pieceFile, pieceRank, color);
    }

    public boolean canMove(String fileRankfinal) {

        return (canMoveBishop(fileRankfinal) || canMoveRook(fileRankfinal));
    }

    public boolean canMoveBishop(String fileRankfinal) {
        int rankDiff = this.pieceRank - Chess.positionRank(fileRankfinal);
        int fileDiff = (this.pieceFile.toString().charAt(0) - 96) - (Chess.positionFile(fileRankfinal).charAt(0) - 96);
        int numIterations = Math.abs(rankDiff);
        if ((Math.abs(rankDiff) != Math.abs(fileDiff)) || rankDiff == 0 || fileDiff == 0) {
            return false;
        }
        if (fileDiff < 0 && rankDiff < 0) {
            for (int i = 1; i <= numIterations; i++) {
                int currRank = this.pieceRank + i;
                int intCurrFile = this.pieceFile.toString().charAt(0) - 96 + i;
                if (currRank < 9 && intCurrFile < 9) {
                    String currPosition = Character.toString(((char) (intCurrFile + 96))) + currRank;
                    if ((i == numIterations)
                            && ((Chess.openSquare(currPosition) || ((Chess.pieceAt(currPosition) != null) && !this.color
                                    .equalsIgnoreCase(((Piece) Chess.pieceAt(currPosition)).getColor()))))) {
                        return true;
                    }
                    if (!Chess.openSquare(currPosition)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (fileDiff > 0 && rankDiff < 0) {
            for (int i = 1; i <= numIterations; i++) {
                int currRank = this.pieceRank + i;
                int intCurrFile = this.pieceFile.toString().charAt(0) - 96 - i;
                if (currRank < 9 && intCurrFile > 0) {
                    String currPosition = Character.toString(((char) (intCurrFile + 96))) + currRank;
                    if ((i == numIterations)
                            && ((Chess.openSquare(currPosition) || ((Chess.pieceAt(currPosition) != null) && !this.color
                                    .equalsIgnoreCase(((Piece) Chess.pieceAt(currPosition)).getColor()))))) {
                        return true;
                    }
                    if (!Chess.openSquare(currPosition)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (fileDiff > 0 && rankDiff > 0) {
            for (int i = 1; i <= numIterations; i++) {
                int currRank = this.pieceRank - i;
                int intCurrFile = this.pieceFile.toString().charAt(0) - 96 - i;
                if (currRank > 0 && intCurrFile > 0) {
                    String currPosition = Character.toString(((char) (intCurrFile + 96))) + currRank;
                    if ((i == numIterations)
                            && ((Chess.openSquare(currPosition) || ((Chess.pieceAt(currPosition) != null) && !this.color
                                    .equalsIgnoreCase(((Piece) Chess.pieceAt(currPosition)).getColor()))))) {
                        return true;
                    }
                    if (!Chess.openSquare(currPosition)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (fileDiff < 0 && rankDiff > 0) {
            for (int i = 1; i <= numIterations; i++) {
                int currRank = this.pieceRank - i;
                int intCurrFile = this.pieceFile.toString().charAt(0) - 96 + i;
                if (currRank > 0 && intCurrFile < 9) {
                    String currPosition = Character.toString(((char) (intCurrFile + 96))) + currRank;
                    if ((i == numIterations)
                            && ((Chess.openSquare(currPosition) || ((Chess.pieceAt(currPosition) != null) && !this.color
                                    .equalsIgnoreCase(((Piece) Chess.pieceAt(currPosition)).getColor()))))) {
                        return true;
                    }
                    if (!Chess.openSquare(currPosition)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean canMoveRook(String fileRankfinal) {
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

        if (((this.pieceFile).name()).equals(Chess.positionFile(fileRankfinal))
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

}