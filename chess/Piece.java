package chess;

public abstract class Piece extends ReturnPiece {
    protected String color;

    public Piece(PieceType piecetype, PieceFile pieceFile, int pieceRank, String color) {
        super();
        this.pieceType = piecetype;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.color = color;
    }

    public void move(String fileRankFinal) {
        this.pieceFile = PieceFile.valueOf(Chess.positionFile(fileRankFinal));
        this.pieceRank = Chess.positionRank(fileRankFinal);

    }

    public abstract boolean canMove(String fileRankfinal);

    public String getColor() {
        return color;
    }

}