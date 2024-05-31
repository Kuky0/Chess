package chess;

public class Pawn extends Piece {
    private boolean firstMove;
    private int MOVE_MAX = 1;

    public Pawn(PieceType pieceType, PieceFile pieceFile, int pieceRank, String color) {
        super(pieceType, pieceFile, pieceRank, color);
        firstMove = true;
    }

    public boolean canMove(String fileRankFinal) {
        switch (this.pieceType) {
            case WP:
                if (Chess.positionRank(fileRankFinal) < this.pieceRank)
                    return false;
                break;
            case BP:
                MOVE_MAX = -1;
                if (Chess.positionRank(fileRankFinal) > this.pieceRank)
                    return false;
                break;
            default:
        }

        if (Chess.openSquare(fileRankFinal) && Chess.positionRank(fileRankFinal) == this.pieceRank + MOVE_MAX
                && ((this.pieceFile).name()).equals(Chess.positionFile(fileRankFinal))) {
            return true;
        }

        if (Chess.openSquare(fileRankFinal) && firstMove
                && Chess.positionRank(fileRankFinal) == this.pieceRank + (MOVE_MAX * 2)
                && ((this.pieceFile).name()).equals(Chess.positionFile(fileRankFinal))) {
            firstMove = false;
            return canMove(Chess.positionFile(fileRankFinal) + (Chess.positionRank(fileRankFinal) - (MOVE_MAX)));
        }

        if (!(Chess.openSquare(fileRankFinal)) &&
                (Math.abs(this.pieceRank - Chess.positionRank(fileRankFinal)) == 1)
                && Math.abs(((this.pieceFile).name()).compareTo(Chess.positionFile(fileRankFinal))) == 1) {
            return true;
        }

        return false;
    }

}