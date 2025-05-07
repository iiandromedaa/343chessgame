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
        double bestMove = isMaximisingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
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
                Piece captured;
                Piece movingPiece = tempBoard.getPieceFromXY(startX, startY);
                if (movingPiece.returnNumber() == 0 && Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 1 &&
                        tempBoard.getPieceFromXY(endX, endY) == null) {
                    captured = tempBoard.getPieceFromXY(endX, startY);
                    } 
                else {
                    captured = tempBoard.getPieceFromXY(endX, endY);
                    }
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
            double bestMove = isMaximisingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
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
                    Piece captured;
                    Piece movingPiece = board.getPieceFromXY(startX, startY);
                    if (movingPiece.returnNumber() == 0 && Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 1 &&
                            board.getPieceFromXY(endX, endY) == null) {
                        captured = board.getPieceFromXY(endX, startY);
                    } 
                    else {
                        captured = board.getPieceFromXY(endX, endY);
                    }
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
        int whiteMobility = 0;
        int blackMobility = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceFromXY(i, j);
                if (piece != null) {
                    double pieceValue = getPieceValue(piece, i, j);
                    totalEvaluation += pieceValue;
                    int mobility = piece.getValidMoves(board.getBoard()).size();
                    if (piece.getColor().toString().equals("WHITE")) {
                        whiteMobility += mobility;
                    } 
                    else {
                        blackMobility += mobility;
                    }
                    if (piece.returnNumber() == 0) {
                        totalEvaluation += evaluatePawnStructure(board, piece);

                    }
                    if (piece.returnNumber() == 5) {
                        totalEvaluation += evaluateKingSafety(board, piece);
                    }
                }
            }
        }

        totalEvaluation += 0.1 * (whiteMobility - blackMobility); 
        return totalEvaluation;
    };

    private double evaluatePawnStructure(Board board, Piece pawn) {
        int x = pawn.getX();
        int y = pawn.getY();
        String color = pawn.getColor().toString();
        int direction = color.equals("WHITE") ? 1 : -1;
        double score = 0;

        for (int j = 0; j < 8; j++) {
            if (j != y) {
                Piece other = board.getPieceFromXY(x, j);
                if (other != null && other.returnNumber() == 0 &&
                    other.getColor().toString().equals(color)) {
                    score -= 0.15;
                    break;
                }
            }
        }
    
        boolean isolated = true;
        for (int dx = -1; dx <= 1; dx += 2) {
            int nx = x + dx;
            if (nx >= 0 && nx < 8) {
                for (int j = 0; j < 8; j++) {
                    Piece other = board.getPieceFromXY(nx, j);
                    if (other != null && other.returnNumber() == 0 &&
                        other.getColor().toString().equals(color)) {
                        isolated = false;
                        break;
                    }
                }
            }
        }
        if (isolated) {
            score -= 0.2;
        }
    
        boolean backward = true;
        if (y - direction >= 0 && y - direction < 8) {
            Piece front = board.getPieceFromXY(x, y + direction);
            if (front == null) {
                for (int dx = -1; dx <= 1; dx += 2) {
                    int nx = x + dx;
                    if (nx >= 0 && nx < 8) {
                        Piece support = board.getPieceFromXY(nx, y);
                        if (support != null && support.returnNumber() == 0 &&
                            support.getColor().toString().equals(color)) {
                            backward = false;
                            break;
                        }
                    }
                }
            } else {
                backward = false; 
            }
        }
        if (backward) {
            score -= 0.15;
        }
    
        boolean isPassed = true;
        for (int dx = -1; dx <= 1; dx++) {
            int nx = x + dx;
            if (nx >= 0 && nx < 8) {
                int j = y + direction;
                while (j >= 0 && j < 8) {
                    Piece opp = board.getPieceFromXY(nx, j);
                    if (opp != null && opp.returnNumber() == 0 &&
                        !opp.getColor().toString().equals(color)) {
                        isPassed = false;
                        break;
                    }
                    j += direction;
                }
            }
        }
        if (isPassed) {
            double rankBonus = color.equals("WHITE") ? y : 7 - y;
            score += 0.1 + (0.05 * rankBonus); 
        }
    
        
        return color.equals("WHITE") ? score : -score;
    }

    private double evaluateKingSafety(Board board, Piece king) {
        int x = king.getX();
        int y = king.getY();
        double penalty = 0;
        for (int x2 = -1; x2 <= 1; x2++) {
            for (int y2 = -1; y2 <= 1; y2++) {
                if (x2 == 0 && y2 == 0) continue;
                int nx = x + x2;
                int ny = y + y2;
                if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
                    if (board.getPieceFromXY(nx, ny) == null) {
                        penalty -= 0.1;
                    }
                }
            }
        }
        return king.getColor().toString().equals("WHITE") ? penalty : -penalty;
    }

     /**
     * Gives a quick evaluation of a move by use MVV-LVA logic
     * @param board
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return quick evalutation of a move
     */
    private double quickEvaluateMove(Board board, int x1, int y1, int x2, int y2) {
        Piece moving = board.getPieceFromXY(x1, y1);
        Piece target = board.getPieceFromXY(x2, y2);
    
        double value = 0;
        if (target != null) {
            value += (target.getValue() * 10) - moving.getValue();
        }

        if (x2 >= 2 && x2 <= 5 && y2 >= 2 && y2 <= 5) {
            value += 0.2;
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
