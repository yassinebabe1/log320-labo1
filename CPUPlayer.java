import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class CPUPlayer
{



    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;
    private Mark cpu;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        this.cpu = cpu;
    }

    // Ne pas changer cette méthode
    public int  getNumOfExploredNodes(){
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board)
    {
        numExploredNodes = 0;
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;

        for (Move move : getAllPossibleMoves(board)) {
            int score = minimax(board, move, 0, false);
            if (score > bestScore) {
                bestScore = score;
                possibleMoves.clear();
                possibleMoves.add(move);
            } else if (score == bestScore) {
                possibleMoves.add(move);
            }
        }

        return possibleMoves;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board){
        numExploredNodes = 0;
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Move move : getAllPossibleMoves(board)) {
            int score = alphaBeta(board, move, 0, alpha, beta, false);
            if (score > bestScore) {
                bestScore = score;
                possibleMoves.clear();
                possibleMoves.add(move);
            } else if (score == bestScore) {
                possibleMoves.add(move);
            }
            alpha = Math.max(alpha, bestScore);
        }

        return possibleMoves;
    }

    private int minimax(Board board, Move move, int depth, boolean isMaximizing) {
        numExploredNodes++;

        // Perform the move on a copy of the board
        Board newBoard = copyBoard(board);
        newBoard.play(move, cpu);

        // Check for terminal states (win, lose, draw)
        int score = newBoard.evaluate(cpu);

        if (score != 0 || depth == 0) {
            return score;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (Move childMove : getAllPossibleMoves(newBoard)) {
                int childScore = minimax(newBoard, childMove, depth - 1, false);
                bestScore = Math.max(bestScore, childScore);
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Move childMove : getAllPossibleMoves(newBoard)) {
                int childScore = minimax(newBoard, childMove, depth - 1, true);
                bestScore = Math.min(bestScore, childScore);
            }
            return bestScore;
        }
    }

    private int alphaBeta(Board board, Move move, int depth, int alpha, int beta, boolean isMaximizing) {
        numExploredNodes++;

        // Perform the move on a copy of the board
        Board newBoard = copyBoard(board);
        newBoard.play(move, cpu);

        // Check for terminal states (win, lose, draw)
        int score = newBoard.evaluate(cpu);

        if (score != 0 || depth == 0) {
            return score;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (Move childMove : getAllPossibleMoves(newBoard)) {
                int childScore = alphaBeta(newBoard, childMove, depth - 1, alpha, beta, false);
                bestScore = Math.max(bestScore, childScore);
                alpha = Math.max(alpha, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (Move childMove : getAllPossibleMoves(newBoard)) {
                int childScore = alphaBeta(newBoard, childMove, depth - 1, alpha, beta, true);
                bestScore = Math.min(bestScore, childScore);
                beta = Math.min(beta, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }
    }

    private ArrayList<Move> getAllPossibleMoves(Board board) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
    
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getMark(i, j) == Mark.EMPTY) {
                    possibleMoves.add(new Move(i, j));
                }
            }
        }
    
        return possibleMoves;
    }
    

    private Board copyBoard(Board originalBoard) {
        Board newBoard = new Board();
    
        // Copier le contenu du tableau
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newBoard.board[i][j] = originalBoard.getMark(i, j);
            }
        }
    
        return newBoard;
    }
    

}
