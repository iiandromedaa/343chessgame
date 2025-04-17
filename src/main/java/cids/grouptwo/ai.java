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
     * @return
     */
    public static Board minimaxRoot(Board board, int depth, boolean isMaximisingPlayer){
        List<Board> newGameMoves;
        newGameMoves = generateMoves(board, isMaximisingPlayer);
        double bestMove = -9999;
        Board bestMoveFound = null;
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

    private static double minimax(Board board, int depth, double alpha, double beta, boolean isMaximisingPlayer){
        if(depth == 0){
            return evaluateBoard(board);
        }
            List<Board> newGameMoves = generateMoves(board, isMaximisingPlayer);
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
                bestMove = Math.min(bestMove, minimax(newGameMove, depth - 1, alpha, beta, !isMaximisingPlayer));
                beta = Math.min(beta, bestMove);
                if (beta <= alpha) {
                    return bestMove;
                }
            }
        }
        return evaluateBoard(board);
    }

    //need to impliment a method to find every available move 
    private static List<Board> generateMoves(Board board, boolean isMaximisingPlayer){
        List<Board> boardList = new ArrayList<>();
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
        for(int k = 0; k < pieces.size(); k++){
            List<Coordinate> possibleMoves = pieces.get(k).getValidMoves(board.getBoard());
           for(int r = 0; r < possibleMoves.size(); r++){
                Board tempBoard = new Board(board);
                Piece tempPiece = tempBoard.getPieceFromCoordinate(pieces.get(k).getPosition());
                Coordinate tempCoordinate = tempPiece.getPosition();
                tempBoard.clearPosition(tempCoordinate);
                tempPiece.piecePosition(possibleMoves.get(r));
                tempBoard.setPiece(tempPiece);
                //tempBoard.displayBoard();
                boardList.add(tempBoard);
           }
        }
        return boardList;
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

    //Helper method to reverse certain states for black
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
    
        private final static double[][] pawnEvalBlack = reverseArray(pawnEvalWhite);
    
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
    
    private final static double[][] bishopEvalBlack = reverseArray(bishopEvalWhite);
    
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
    
    private final static double[][] rookEvalBlack = reverseArray(rookEvalWhite);
    
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
    
    private final static double[][] kingEvalBlack = reverseArray(kingEvalWhite);

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
