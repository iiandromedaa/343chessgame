package cids.grouptwo;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.pieces.Piece;

public class ai {

    /**
     * This method takes a board state, a search depth, and a variable to tell who's turn it is and 
     * returns the best move it can find. For white let "isMaximisingPlayer" be true, for black let it be false. 
     * @param board
     * @param depth
     * @param isMaximisingPlayer
     * @return board with the optimal move for the given side
     */
    public static Board minimaxRoot(Board board, int depth, boolean isMaximisingPlayer){
        double bestMove = isMaximisingPlayer ? -10000 : 10000;
        Coordinate bestStart = null;
        Coordinate bestEnd = null;
        List<Piece> pieceList = generatePieces(board, isMaximisingPlayer);
        for(Piece piece : pieceList){
            Coordinate start = piece.getPosition();
            List<Coordinate> newGameMoves = piece.getValidMoves(board.getBoard());
            for(Coordinate end : newGameMoves){
                Board tempBoard = new Board(board);
                Piece tempPiece = tempBoard.getPieceFromCoordinate(start);
                tempBoard.clearPosition(start);
                tempPiece.piecePosition(end);
                tempBoard.setPiece(tempPiece);
                double value = minimax(tempBoard, depth-1, -10000, 10000, !isMaximisingPlayer);
                if((isMaximisingPlayer && value > bestMove) || (!isMaximisingPlayer && value < bestMove)){
                    bestMove = value;
                    bestStart = start;
                    bestEnd = end;
                }
            }
        }
        Board resultBoard = new Board(board);
        Piece pieceToMove = resultBoard.getPieceFromCoordinate(bestStart);
        resultBoard.clearPosition(bestStart);
        pieceToMove.piecePosition(bestEnd);
        resultBoard.setPiece(pieceToMove);
        return resultBoard;
    }

    private static double minimax(Board board, int depth, double alpha, double beta, boolean isMaximisingPlayer){
        if(depth == 0){
            return evaluateBoard(board);
        }
            List<Piece> pieceList = generatePieces(board, isMaximisingPlayer);
            double bestMove;
        if(isMaximisingPlayer){
            bestMove = -9999;
            for(Piece piece : pieceList){
                Coordinate start = piece.getPosition();
                List<Coordinate> newGameMoves = piece.getValidMoves(board.getBoard());
                for(Coordinate end : newGameMoves){
                    Board tempBoard = new Board(board);
                    Piece tempPiece = tempBoard.getPieceFromCoordinate(start);
                    tempBoard.clearPosition(start);
                    tempPiece.piecePosition(end);
                    tempBoard.setPiece(tempPiece);
                    double value = minimax(tempBoard, depth-1, alpha, beta, !isMaximisingPlayer);
                    bestMove = Math.max(bestMove, value);
                    alpha = Math.max(bestMove, alpha);
                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
        }
        else{
            bestMove = 9999;
            for(Piece piece : pieceList){
                Coordinate start = piece.getPosition();
                List<Coordinate> newGameMoves = piece.getValidMoves(board.getBoard());
                for(Coordinate end : newGameMoves){
                    Board tempBoard = new Board(board);
                    Piece tempPiece = tempBoard.getPieceFromCoordinate(start);
                    tempBoard.clearPosition(start);
                    tempPiece.piecePosition(end);
                    tempBoard.setPiece(tempPiece);
                    double value = minimax(tempBoard, depth-1, alpha, beta, !isMaximisingPlayer);
                    bestMove = Math.min(bestMove, value);
                    beta = Math.min(beta, bestMove);
                    if (beta <= alpha) {
                        return bestMove;
                    }
                }
            }
        }
        return bestMove;
    }

    private static List<Piece> generatePieces(Board board, boolean isMaximisingPlayer){
        List<Piece> pieces = new ArrayList<>();
        String color;
        if(isMaximisingPlayer == true){
            color = "WHITE";
        }
        else{
            color = "BLACK";
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board.getPieceFromXY(i, j) != null){
                    if(board.getPieceFromXY(i, j).getColor().toString().equals(color)){
                        pieces.add(board.getPieceFromXY(i, j));
                    }
                }
            }
        }
        return pieces;
    }
    
        

    //need a way to get the pieces from a board
    private static double evaluateBoard(Board board) {
        double totalEvaluation = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board.getPieceFromXY(i, j) != null){
                    Piece piece = board.getPieceFromXY(i, j);
                    totalEvaluation = totalEvaluation + getPieceValue(piece, i ,j);
                }
            }
        }
        return totalEvaluation;
    };

    //Helper method to reverse certain states for black, no longer used because I hard coded them but might be
    //useful in the future.
    private static double[][] reverseArray(double[][] array){
        double[][] newArray = new double[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8 / 2; j++) {
                newArray[i][j] = array[i][7 - j];
                newArray[i][7 - j] = array[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            newArray[i] = array[7 - i];
            newArray[7 - i] = array[i];
        }
        return newArray;
    };
    
    //These are the lists of how good it is for a piece to be on a given square
    private final static double[][] pawnEvalWhite =  {
            {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
            {5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
            {1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
            {0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5},
            {0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0},
            {0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5},
            {0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5},
            {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
        };
    
        private final static double[][] pawnEvalBlack = {
            {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
            {0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5},
            {0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5},
            {0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0},
            {0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5},
            {1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
            {5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
            {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
        };;
    
    private  final static double[][] knightEval =
        {
            {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
            {-4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0},
            {-3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0},
            {-3.0,  0.5,  1.5,  2.0,  2.0,  1.5,  0.5, -3.0},
            {-3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0},
            {-3.0,  0.5,  1.0,  1.5,  1.5,  1.0,  0.5, -3.0},
            {-4.0, -2.0,  0.0,  0.5,  0.5,  0.0, -2.0, -4.0},
            {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
        };
    
        private final static double[][] bishopEvalWhite = {
        { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
        { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0},
        { -1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0},
        { -1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
        { -1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
        { -1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0},
        { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
        };
    
    private final static double[][] bishopEvalBlack = {
        { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
        { -1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0},
        { -1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
        { -1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
        { -1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0},
        { -1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0},
        { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
        
        };;
    
    private final static double[][] rookEvalWhite = {
        {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
        {  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        {  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0}
    };
    
    private final static double[][] rookEvalBlack = {
        {  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        {  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
        {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
        
    };
    
    private final static double[][] evalQueen = {
        { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
        { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
        { -0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
        {  0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
        { -1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
    };
    
    private final static double[][] kingEvalWhite = {
    
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
        { -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
        {  2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0 },
        {  2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0 }
    };
    
    private final static double[][] kingEvalBlack = {
    
        {  2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0 },
        {  2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0 },
        { -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
        { -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0}
    };

    //Gives the value of a piece being on a certain space
    private static double getPieceValue(Piece piece, int x, int y){
        if(piece == null){
            return 0;
        }
        double absoluteValue=0;
        String name = piece.returnName();
        switch (name) {
            case "Pawn":
                if(piece.getColor().toString().equals("WHITE")){
                    absoluteValue = 10 + pawnEvalWhite[y][x];
                }
                else{
                    absoluteValue = 10 + pawnEvalBlack[y][x];
                }
                break;
            case "Rook":
                if(piece.getColor().toString().equals("WHITE")){
                    absoluteValue = 50 + rookEvalWhite[y][x];
                }
                else{
                    absoluteValue = 50 + rookEvalBlack[y][x];
                }
                break;
            case "Knight":
                return 30 + knightEval[y][x];
            case "Bishop":
                if(piece.getColor().toString().equals("WHITE")){
                    absoluteValue = 50 + bishopEvalWhite[y][x];
                }
                else{
                    absoluteValue = 50 + bishopEvalBlack[y][x];
                }
                break;
            case "Queen":
                return 90 + evalQueen[y][x];
            case "King":
                if(piece.getColor().toString().equals("WHITE")){
                    absoluteValue = 900 + kingEvalWhite[y][x];
                }
                else{
                    absoluteValue = 900 + kingEvalBlack[y][x];
                }
                break;
            default:
                break;
        }
            if(piece.getColor().toString().equals("WHITE")){
                return absoluteValue;
            }
            else{
                return -absoluteValue;
            }
    }

}
