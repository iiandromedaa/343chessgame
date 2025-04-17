package cids.grouptwo;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.pieces.Piece;

public class ai {

    public Board minimaxRoot(Board board, int depth, boolean isMaximisingPlayer){
        List<Board> newGameMoves = generateMoves(board);
        double bestMove = -9999;
        Board bestMoveFound = board;
        for(int i=0; i < newGameMoves.size(); i++){
            Board newGameMove = newGameMoves.get(i);
            double value = minimax(newGameMove, depth-1, -10000, 10000, !isMaximisingPlayer);
            if(value >= bestMove){
                bestMove = value;
                bestMoveFound = newGameMove;
            }
        }
        return bestMoveFound;
    }
    /**
     * This is the algorithm meant to give the best move, where the intial alpha should be -9999
     * and the initial beta should be 9999
    **/
    private double minimax(Board board, int depth, double alpha, double beta, boolean isMaximisingPlayer){
        if(depth == 0){
            return evaluateBoard(board);
        }
            List<Board> newGameMoves = generateMoves(board);
        if(isMaximisingPlayer){
            double bestMove = -9999;
            for(int i = 0; i < newGameMoves.size(); i++){
                Board newGameMove = newGameMoves.get(i);
                bestMove = Math.max(bestMove, minimax(newGameMove, depth - 1, alpha, beta, !isMaximisingPlayer));
                alpha = Math.max(alpha, bestMove);
                if (beta <= alpha) {
                    return bestMove;
                }
            }  

        }
        else{
            double bestMove = 9999;
            for(int i = 0; i < newGameMoves.size(); i++){
                Board newGameMove = newGameMoves.get(i);
                bestMove = Math.max(bestMove, minimax(newGameMove, depth - 1, alpha, beta, !isMaximisingPlayer));
                beta = Math.max(beta, bestMove);
                if (beta <= alpha) {
                    return bestMove;
                }
            }
        }


        return 0;
    }

    //need to impliment a method to find every available move 
    private List<Board> generateMoves(Board board){
        List<Board> boardList = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board.getPieceFromXY(i, j) != null){
                    pieces.add(board.getPieceFromXY(i, j));
                }
            }
        }
        for(int k = 0; k < pieces.size(); k++){
           List<Coordinate> possibleMoves = pieces.get(k).getValidMoves(board.getBoard());
           Board tempBoard = new Board(board);
           for(int r = 0; r < possibleMoves.size(); r++){
                tempBoard.getPieceFromCoordinate(pieces.get(k).getPosition()).piecePosition(possibleMoves.get(r));
                boardList.add(tempBoard);
           }
        }
        return boardList;
    }

    //need a way to get the pieces from a board
    private double evaluateBoard(Board board) {
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

    //Helper method to reverse certain states for black
    private double[][] reverseArray(double[][] array){
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
    private final double[][] pawnEvalWhite =  {
            {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
            {5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
            {1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
            {0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5},
            {0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0},
            {0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5},
            {0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5},
            {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
        };
    
        private final double[][] pawnEvalBlack = reverseArray(pawnEvalWhite);
    
    private  final double[][] knightEval =
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
    
        private final double[][] bishopEvalWhite = {
        { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
        { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0},
        { -1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0},
        { -1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
        { -1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
        { -1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0},
        { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
    };
    
    private final double[][] bishopEvalBlack = reverseArray(bishopEvalWhite);
    
    private final double[][] rookEvalWhite = {
        {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
        {  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        {  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0}
    };
    
    private final double[][] rookEvalBlack = reverseArray(rookEvalWhite);
    
    private final double[][] evalQueen = {
        { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
        { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
        { -0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
        {  0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
        { -1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
    };
    
    private final double[][] kingEvalWhite = {
    
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
        { -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
        {  2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0 },
        {  2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0 }
    };
    
    private final double[][] kingEvalBlack = reverseArray(kingEvalWhite);

    //Gives the value of a piece being on a certain space
    private double getPieceValue(Piece piece, int x, int y){
        if(piece == null){
            return 0;
        }
        double absoluteValue =0;
        String name = piece.getClass().getName();
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
