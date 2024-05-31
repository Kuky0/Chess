package chess;

import java.util.ArrayList;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;
import chess.ReturnPlay.Message;

class ReturnPiece {
    static enum PieceType {
        WP, WR, WN, WB, WQ, WK,
        BP, BR, BN, BB, BK, BQ
    };

    static enum PieceFile {
        a, b, c, d, e, f, g, h
    };

    PieceType pieceType;
    PieceFile pieceFile;
    int pieceRank;

    public String toString() {
        return "" + pieceFile + pieceRank + ":" + pieceType;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof ReturnPiece)) {
            return false;
        }
        ReturnPiece otherPiece = (ReturnPiece) other;
        return pieceType == otherPiece.pieceType &&
                pieceFile == otherPiece.pieceFile &&
                pieceRank == otherPiece.pieceRank;
    }
}

class ReturnPlay {
    enum Message {
        ILLEGAL_MOVE, DRAW,
        RESIGN_BLACK_WINS, RESIGN_WHITE_WINS,
        CHECK, CHECKMATE_BLACK_WINS, CHECKMATE_WHITE_WINS,
        STALEMATE
    };

    ArrayList<ReturnPiece> piecesOnBoard;
    Message message;
}

public class Chess {

    enum Player {
        white, black
    }

    private static ReturnPlay board;
    private static Player turnPlayer = Player.white;
    private static ArrayList<String> moveHistory;

    public static ReturnPlay play(String move) {
        String[] inputs = (move.replaceAll("\\s+", " ").trim()).split(" ");

        if (inputs.length < 2) {
            board.message = Message.ILLEGAL_MOVE;
            return board;
        }

        inputs[0] = inputs[0].toLowerCase();
        inputs[1] = inputs[1].toLowerCase();

        if (inputs[0].equals("resign")) {
            board.message = (turnPlayer == Player.white) ? Message.RESIGN_BLACK_WINS : Message.RESIGN_WHITE_WINS;
            return board;
        }
        Piece currentPiece = (Piece) pieceAt(inputs[0]);

        if (currentPiece == null || inputs[0].equals(inputs[1])
                || !((currentPiece.getColor()).equals((turnPlayer).name()))
                || (((Piece) pieceAt(inputs[1])) != null
                        && ((Piece) pieceAt(inputs[1])).getColor().equals((turnPlayer).name()))) {
            board.message = Message.ILLEGAL_MOVE;
            return board;
        } else if (currentPiece.canMove(inputs[1]) || isEnpassant(currentPiece, inputs[1])
                || isCastle(currentPiece, inputs[1])) {
            Piece destinationPiece = (isEnpassant(currentPiece, inputs[1]))
                    ? (Piece) pieceAt(moveHistory.get(moveHistory.size() - 1).split(" ")[1])
                    : (Piece) pieceAt(inputs[1]);
            board.message = null;

            if (destinationPiece != null) {
                board.piecesOnBoard.remove(destinationPiece);
            }

            boolean castled = isCastle(currentPiece, inputs[1]);
            if (castled) {
                String rookPos = ('e' > inputs[1].charAt(0)) ? ("a" + currentPiece.pieceRank)
                        : ("h" + currentPiece.pieceRank);
                Piece other = ((Piece) pieceAt(rookPos));
                if (currentPiece.pieceFile.toString().charAt(0) < inputs[1].charAt(0)) {
                    currentPiece.move(inputs[1]);
                    other.move("f" + currentPiece.pieceRank);
                    ((King) currentPiece).setFirstMove(false);
                    ((Rook) other).setFirstMove(false);
                } else {
                    currentPiece.move(inputs[1]);
                    other.move("d" + currentPiece.pieceRank);
                    ((King) currentPiece).setFirstMove(false);
                    ((Rook) other).setFirstMove(false);
                }
            } else {
                currentPiece.move(inputs[1]);
                switch (currentPiece.pieceType.toString().charAt(1)) {
                    case 'K':
                        ((King) currentPiece).setFirstMove(false);
                        break;
                    case 'R':
                        ((Rook) currentPiece).setFirstMove(false);
                        break;
                }
            }
            King allyKing = (King) findKing(turnPlayer.name());
            String allyKingLoc = allyKing.pieceFile.name() + allyKing.pieceRank;
            for (ReturnPiece atkPiece : board.piecesOnBoard) {
                if (Player.valueOf(((Piece) atkPiece).getColor()) != turnPlayer
                        && ((Piece) atkPiece).canMove(allyKingLoc)) {
                    if (castled) {
                        String rookPos = ('e' > inputs[1].charAt(0)) ? ("a" + currentPiece.pieceRank)
                                : ("h" + currentPiece.pieceRank);
                        String rookCastlePos = ('e' > inputs[1].charAt(0)) ? ("d" + currentPiece.pieceRank)
                                : ("f" + currentPiece.pieceRank);
                        Piece other = ((Piece) pieceAt(rookCastlePos));
                        currentPiece.move(inputs[0]);
                        other.move(rookPos);
                        ((King) currentPiece).setFirstMove(true);
                        ((Rook) other).setFirstMove(true);
                    } else {
                        currentPiece.move(inputs[0]);
                        if (destinationPiece != null) {
                            board.piecesOnBoard.add(destinationPiece);
                        }
                    }
                    board.message = Message.ILLEGAL_MOVE;
                    return board;
                }
            }

            if ((currentPiece.pieceType == PieceType.WP && currentPiece.pieceRank == 8)
                    || (currentPiece.pieceType == PieceType.BP && currentPiece.pieceRank == 1)) {

                String promotionType;
                if (inputs.length != 3) {
                    promotionType = "Q";
                } else if (inputs[2].contains("Q") || inputs[2].contains("N") || inputs[2].contains("B")
                        || inputs[2].contains("R")) {
                    promotionType = inputs[2];
                } else {
                    currentPiece.move(inputs[0]);
                    if (destinationPiece != null) {
                        board.piecesOnBoard.add(destinationPiece);
                    }
                    board.message = Message.ILLEGAL_MOVE;
                    return board;
                }
                String currPieceColor = currentPiece.getColor().substring(0, 1).toUpperCase();
                String promotePieceType = currPieceColor + promotionType;

                ReturnPiece pawnPromoteTo;
                switch (promotionType) {
                    case "N":
                        pawnPromoteTo = new Knight(PieceType.valueOf(promotePieceType), currentPiece.pieceFile,
                                currentPiece.pieceRank, currentPiece.getColor());
                        break;
                    case "R":
                        pawnPromoteTo = new Rook(PieceType.valueOf(promotePieceType), currentPiece.pieceFile,
                                currentPiece.pieceRank, currentPiece.getColor());
                        break;
                    case "B":
                        pawnPromoteTo = new Bishop(PieceType.valueOf(promotePieceType), currentPiece.pieceFile,
                                currentPiece.pieceRank, currentPiece.getColor());
                        break;
                    case "Q":
                    default:
                        pawnPromoteTo = new Queen(PieceType.valueOf(promotePieceType), currentPiece.pieceFile,
                                currentPiece.pieceRank, currentPiece.getColor());
                        break;
                }
                board.piecesOnBoard.remove(currentPiece);
                board.piecesOnBoard.add(pawnPromoteTo);
            }

            King enemyKing = (King) findKing((turnPlayer == Player.white) ? Player.black.name() : Player.white.name());
            String enemyKingLoc = enemyKing.pieceFile.name() + enemyKing.pieceRank;
            for (ReturnPiece atkPiece : board.piecesOnBoard) {
                if (Player.valueOf(((Piece) atkPiece).getColor()) == turnPlayer
                        && ((Piece) atkPiece).canMove(enemyKingLoc)) {
                    board.message = Message.CHECK;
                }
            }

            if (board.message == Message.CHECK && checkmate(enemyKingLoc)) {
                board.message = (turnPlayer == Player.white) ? Message.CHECKMATE_WHITE_WINS
                        : Message.CHECKMATE_BLACK_WINS;
            }

        } else {
            board.message = Message.ILLEGAL_MOVE;
            return board;
        }
        moveHistory.add(inputs[0] + " " + inputs[1] + " " + currentPiece.pieceType.name());
        turnPlayer = (turnPlayer == Player.white) ? Player.black : Player.white;
        if (inputs.length >= 3 && inputs[inputs.length - 1].equals("draw?")) {
            board.message = Message.DRAW;
        }

        return board;
    }

    public static void start() {
        ArrayList<ReturnPiece> initialstate = new ArrayList<>();
        initialstate.add(new Rook(PieceType.WR, PieceFile.a, 1, "white"));
        initialstate.add(new Rook(PieceType.WR, PieceFile.h, 1, "white"));
        initialstate.add(new Knight(PieceType.WN, PieceFile.b, 1, "white"));
        initialstate.add(new Knight(PieceType.WN, PieceFile.g, 1, "white"));
        initialstate.add(new Bishop(PieceType.WB, PieceFile.c, 1, "white"));
        initialstate.add(new Bishop(PieceType.WB, PieceFile.f, 1, "white"));
        initialstate.add(new Queen(PieceType.WQ, PieceFile.d, 1, "white"));
        initialstate.add(new King(PieceType.WK, PieceFile.e, 1, "white"));
        initialstate.add(new Pawn(PieceType.WP, PieceFile.a, 2, "white"));
        initialstate.add(new Pawn(PieceType.WP, PieceFile.b, 2, "white"));
        initialstate.add(new Pawn(PieceType.WP, PieceFile.c, 2, "white"));
        initialstate.add(new Pawn(PieceType.WP, PieceFile.d, 2, "white"));
        initialstate.add(new Pawn(PieceType.WP, PieceFile.e, 2, "white"));
        initialstate.add(new Pawn(PieceType.WP, PieceFile.f, 2, "white"));
        initialstate.add(new Pawn(PieceType.WP, PieceFile.g, 2, "white"));
        initialstate.add(new Pawn(PieceType.WP, PieceFile.h, 2, "white"));
        initialstate.add(new Rook(PieceType.BR, PieceFile.a, 8, "black"));
        initialstate.add(new Rook(PieceType.BR, PieceFile.h, 8, "black"));
        initialstate.add(new Knight(PieceType.BN, PieceFile.b, 8, "black"));
        initialstate.add(new Knight(PieceType.BN, PieceFile.g, 8, "black"));
        initialstate.add(new Bishop(PieceType.BB, PieceFile.c, 8, "black"));
        initialstate.add(new Bishop(PieceType.BB, PieceFile.f, 8, "black"));
        initialstate.add(new Queen(PieceType.BQ, PieceFile.d, 8, "black"));
        initialstate.add(new King(PieceType.BK, PieceFile.e, 8, "black"));
        initialstate.add(new Pawn(PieceType.BP, PieceFile.a, 7, "black"));
        initialstate.add(new Pawn(PieceType.BP, PieceFile.b, 7, "black"));
        initialstate.add(new Pawn(PieceType.BP, PieceFile.c, 7, "black"));
        initialstate.add(new Pawn(PieceType.BP, PieceFile.d, 7, "black"));
        initialstate.add(new Pawn(PieceType.BP, PieceFile.e, 7, "black"));
        initialstate.add(new Pawn(PieceType.BP, PieceFile.f, 7, "black"));
        initialstate.add(new Pawn(PieceType.BP, PieceFile.g, 7, "black"));
        initialstate.add(new Pawn(PieceType.BP, PieceFile.h, 7, "black"));

        board = new ReturnPlay();
        board.piecesOnBoard = initialstate;
        board.message = null;

        turnPlayer = Player.white;

        moveHistory = new ArrayList<>();
    }

    public static String positionFile(String position) {
        return position.charAt(0) + "";
    }

    public static int positionRank(String position) {
        return Integer.parseInt(position.substring(1));
    }

    public static boolean openSquare(String position) {
        return (pieceAt(position) == null);
    }

    public static ReturnPiece pieceAt(String position) {
        for (ReturnPiece activePiece : board.piecesOnBoard) {
            if (activePiece instanceof Piece && ((activePiece.pieceFile).name()).equals(positionFile(position))
                    && activePiece.pieceRank == Chess.positionRank(position)) {
                return activePiece;
            }
        }
        return null;
    }

    private static ReturnPiece findKing(String color) {
        for (ReturnPiece boardPiece : board.piecesOnBoard) {
            if (boardPiece instanceof King && (((Piece) boardPiece).getColor()).equals(color)) {
                return boardPiece;// design to choose to return king piece itself so we can set inCheck field
            }
        }
        return null;
    }

    private static boolean isEnpassant(Piece currentPiece, String moveFinal) {
        if (moveHistory.size() == 0)
            return false;

        String[] lastMove = moveHistory.get(moveHistory.size() - 1).split(" ");
        String lmInitial = lastMove[0];
        String lmFinal = lastMove[1];
        PieceType lmPiece = PieceType.valueOf(lastMove[2]);
        boolean pieceAgreement = ((lmPiece == PieceType.WP && currentPiece.pieceType == PieceType.BP)
                || (lmPiece == PieceType.BP && currentPiece.pieceType == PieceType.WP));
        boolean rankAgreement = (Math.abs(Chess.positionRank(lmInitial) - Chess.positionRank(lmFinal)) == 2)
                && Chess.positionRank(lmFinal) == currentPiece.pieceRank;
        boolean fileAgreement = (Math
                .abs(Chess.positionFile(currentPiece.pieceFile.name()).compareTo(Chess.positionFile(lmFinal))) == 1)
                && Chess.positionFile(lmFinal).equals(Chess.positionFile(moveFinal));

        return pieceAgreement && rankAgreement && fileAgreement;
    }

    private static boolean checkmate(String enemyKingLoc) {
        String enemyColor = (turnPlayer == Player.white) ? "black" : "white";
        for (int i = 0; i < board.piecesOnBoard.size(); i++) {
            ReturnPiece defendPiece = (board.piecesOnBoard).get(i);
            if (((Piece) defendPiece).getColor().equals(enemyColor)) {
                ArrayList<String> moveSet = new ArrayList<>();
                for (char r = 'a'; r < 'i'; r++) {
                    for (int c = 1; c < 9; c++) {
                        if (((Piece) defendPiece).canMove(r + "" + c)) {
                            moveSet.add(r + "" + c);
                        }
                    }
                }

                String defendPieceLoc = ((Piece) defendPiece).pieceFile.name() + ((Piece) defendPiece).pieceRank;

                for (int m = 0; m < moveSet.size(); m++) {
                    String potentialMove = moveSet.get(m);
                    ReturnPiece tempCapture = pieceAt(potentialMove);
                    if (tempCapture != null) {
                        board.piecesOnBoard.remove(tempCapture);
                        i--;
                    }

                    ((Piece) defendPiece).move(potentialMove);

                    String oldKingLoc = "";
                    if (defendPiece instanceof King) {
                        oldKingLoc = enemyKingLoc;
                        enemyKingLoc = potentialMove;
                    }

                    boolean enemyKingInCheck = false;
                    for (ReturnPiece atkPiece : board.piecesOnBoard) {
                        if (Player.valueOf(((Piece) atkPiece).getColor()) != turnPlayer) {
                        } else if (((Piece) atkPiece).canMove(enemyKingLoc)) {
                            enemyKingInCheck = true;
                            break;
                        }

                    }
                    ((Piece) defendPiece).move(defendPieceLoc);
                    if (tempCapture != null) {
                        board.piecesOnBoard.add(tempCapture);
                        i++;
                    }

                    if (defendPiece instanceof King) {
                        enemyKingLoc = oldKingLoc;
                    }
                    if (!enemyKingInCheck) {
                        return false;
                    }

                }
            }
        }
        return true;
    }

    private static boolean isCastle(Piece currentPiece, String moveFinal) {
        if (board.message == Message.CHECK)
            return false;

        ArrayList<String> bRLocation = new ArrayList<String>();
        ArrayList<String> wRLocation = new ArrayList<String>();
        for (int i = 3; i <= 7; i += 4) {
            wRLocation.add(Character.toString(i + 96) + "1");
            bRLocation.add(Character.toString(i + 96) + "8");
        }

        String rookPos = ('e' > moveFinal.charAt(0)) ? ("a" + currentPiece.pieceRank) : ("h" + currentPiece.pieceRank);
        if ((currentPiece.pieceType.toString().equals("WK") && (wRLocation.contains(moveFinal)))
                || (currentPiece.pieceType.toString().equals("BK") && (bRLocation.contains(moveFinal)))
                        && Chess.pieceAt(rookPos) != null && ((Rook) Chess.pieceAt(rookPos)).hasFirstMove()
                        && ((King) currentPiece).hasFirstMove()) {
            int start = (moveFinal.charAt(0) < currentPiece.pieceFile.toString().charAt(0)) ? 1 : 5;
            int stop = (start == 1) ? 5 : 8;
            for (int i = start + 1; i < stop; i++) {
                if (!Chess.openSquare(Character.toString(((char) (i + 96))) + currentPiece.pieceRank)) {
                    return false;
                }
            }

            for (ReturnPiece atkPiece : board.piecesOnBoard) {
                if (start == 5
                        && ((Piece) atkPiece).canMove("f" + currentPiece.pieceRank)
                        && !(currentPiece.getColor().equals(((Piece) atkPiece).getColor()))) {
                    return false;
                } else if (start == 1
                        && ((Piece) atkPiece).canMove("d" + currentPiece.pieceRank)
                        && !(currentPiece.getColor().equals(((Piece) atkPiece).getColor()))) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }
}