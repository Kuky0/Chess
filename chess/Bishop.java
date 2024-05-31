package chess;

public class Bishop extends Piece {
    public Bishop(PieceType pieceType, PieceFile pieceFile, int pieceRank, String color) {
        super(pieceType, pieceFile, pieceRank, color);
    }

    public boolean canMove(String fileRankfinal) {
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

}