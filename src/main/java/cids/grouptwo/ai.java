package cids.grouptwo;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.pieces.Piece;

public class ai {

    private static final int TABLE_POWER = 20;
    private final TranspositionTable transpositionTable = new TranspositionTable(TABLE_POWER);


    /**
     * initializes the ai and sets the Zorbist Hash value
     */
    public ai() {
        ZorbHash.initialise();
    }

    /**
     * This method takes a board state, a search depth, and a variable to tell who's turn it is and 
     * returns the best move it can find. For white let "isMaximisingPlayer" be true, for black let it be false. 
     * @param board
     * @param depth
     * @param isMaximisingPlayer
     * @return board with the optimal move for the given side
     */
    public Board minimaxRoot(Board board, int depth, boolean isMaximisingPlayer){
        Board tempBoard = new Board(board);
        double bestMove = isMaximisingPlayer ? -10000 : 10000;
        Coordinate bestStart = null;
        Coordinate bestEnd = null;
        List<Piece> pieceList = generatePieces(tempBoard, isMaximisingPlayer);
        long zhash = ZorbHash.computeHash(tempBoard, isMaximisingPlayer);
        for(Piece piece : pieceList){
            Coordinate start = piece.getPosition();
            int startX = start.X;
            int startY = start.Y;
            List<Coordinate> newGameMoves = piece.getValidMoves(board.getBoard());
            newGameMoves.sort((a, b) -> {
                double evalA = quickEvaluateMove(tempBoard, piece.getX(), piece.getY(), a.X, a.Y);
                double evalB = quickEvaluateMove(tempBoard, piece.getX(), piece.getY(), b.X, b.Y);
                return isMaximisingPlayer
                    ? Double.compare(evalB, evalA) 
                    : Double.compare(evalA, evalB);
            });
            for(Coordinate end : newGameMoves){
                int endX = end.X;
                int endY = end.Y;
                Piece captured = tempBoard.getPieceFromCoordinate(end);
                tempBoard.makeMove(startX, startY, endX, endY);
                long zhash2 = ZorbHash.updateHash(zhash, tempBoard, start.X, start.Y, end.X, end.Y);
                double value = minimax(tempBoard, depth-1, -10000, 10000, !isMaximisingPlayer, zhash2);
                tempBoard.undoMove(startX, startY, endX, endY, captured);
                if((isMaximisingPlayer && value > bestMove) || (!isMaximisingPlayer && value < bestMove)){
                    bestMove = value;
                    bestStart = start;
                    bestEnd = end;
                }
            }
        }
        Board resultBoard = new Board(board);
        resultBoard.makeMove(bestStart.X, bestStart.Y, bestEnd.X, bestEnd.Y);
        return resultBoard;
    }

    private double minimax(Board board, int depth, double alpha, double beta, boolean isMaximisingPlayer, long hash){
        TTEntry entry = transpositionTable.retrieve(hash, depth);
        if (entry != null && entry.depth >= depth) {
            return entry.evaluation;
        }

        if(depth == 0){
            double eval = evaluateBoard(board);
            transpositionTable.store(hash, eval, depth);
            return eval;
        }
            List<Piece> pieceList = generatePieces(board, isMaximisingPlayer);
            double bestMove = isMaximisingPlayer ? -9999 : 9999;
            boolean firstMove = true;
        
            for(Piece piece : pieceList){
                Coordinate start = piece.getPosition();
                int startX = start.X;
                int startY = start.Y;
                List<Coordinate> newGameMoves = piece.getValidMoves(board.getBoard());
                newGameMoves.sort((a, b) -> {
                    double evalA = quickEvaluateMove(board, startX, startY, a.X, a.Y);
                    double evalB = quickEvaluateMove(board, startX, startY, b.X, b.Y);
                    return isMaximisingPlayer ? Double.compare(evalB, evalA) : Double.compare(evalA, evalB);
                });
                for(Coordinate end : newGameMoves){
                    int endX = end.X;
                    int endY = end.Y;
                    Piece captured = board.getPieceFromCoordinate(end);
                    board.makeMove(startX, startY, endX, endY);
                    long zhash2 = ZorbHash.updateHash(hash, board, start.X, start.Y, end.X, end.Y);
                    double value;
                    if (firstMove) {
                        value = minimax(board, depth - 1, alpha, beta, !isMaximisingPlayer, zhash2);
                        firstMove = false;
                    } else {
                        value = minimax(board, depth - 1, alpha, alpha + 1, !isMaximisingPlayer, zhash2);
                        if ((isMaximisingPlayer && value > alpha) || (!isMaximisingPlayer && value < alpha)) {
                            value = minimax(board, depth - 1, alpha, beta, !isMaximisingPlayer, zhash2);
                        }
                    }

                    board.undoMove(startX, startY, endX, endY, captured);
                    if (isMaximisingPlayer) {
                        bestMove = Math.max(bestMove, value);
                        alpha = Math.max(alpha, bestMove);
                    } else {
                        bestMove = Math.min(bestMove, value);
                        beta = Math.min(beta, bestMove);
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        transpositionTable.store(hash, bestMove, depth);
        return bestMove;
    }

    private List<Piece> generatePieces(Board board, boolean isMaximisingPlayer){
        List<Piece> pieces = new ArrayList<>();
        String color;
        if(isMaximisingPlayer){
            color = "WHITE";
        }
        else{
            color = "BLACK";
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceFromXY(i, j);
                if(piece != null){
                    if(piece.getColor().toString().equals(color)){
                        pieces.add(piece);
                    }
                }
            }
        }
        return pieces;
    }
    
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

    private double quickEvaluateMove(Board board, int x1, int y1, int x2, int y2) {
        Piece moving = board.getPieceFromXY(x1, y1);
        Piece target = board.getPieceFromXY(x2, y2);
    
        double value = 0;
        if (target != null) {
            value += target.getValue() - 0.1 * moving.getValue(); // MVV-LVA logic
        }
    
        return value;
    }

    //Gives the value of a piece being on a certain space
    private double getPieceValue(Piece piece, int x, int y){
        if(piece == null){
            return 0;
        }
        double absoluteValue= piece.getValue() + piece.getValueOfSpace(x,y);
            if(piece.getColor().toString().equals("WHITE")){
                return absoluteValue;
            }
            else{
                return -absoluteValue;
            }
    }

}
