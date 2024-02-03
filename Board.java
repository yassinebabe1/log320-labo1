import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
class Board
{
    Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {
        board = new Mark[3][3];
        // Initialize the board with Mark.EMPTY
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    public Mark getMark(int row, int col) {
        return board[row][col];
    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark){
    if (board[m.getRow()][m.getCol()] == Mark.EMPTY) { // Check if the position is empty
        board[m.getRow()][m.getCol()] = mark; // Place the mark
    } else {
        throw new IllegalArgumentException("Position already occupied");
    }
    }


    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
        public int evaluate(Mark mark) {
            
            Mark opponentMark = (mark == Mark.X) ? Mark.O : Mark.X;
        
            // Check rows for victory or defeat
            for (int i = 0; i < 3; i++) {
                if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) {
                    return 100; // Victory
                }
                if (board[i][0] == opponentMark && board[i][1] == opponentMark && board[i][2] == opponentMark) {
                    return -100; // Defeat
                }
            }
        
            // Check columns for victory or defeat
            for (int i = 0; i < 3; i++) {
                if (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark) {
                    return 100; // Victory
                }
                if (board[0][i] == opponentMark && board[1][i] == opponentMark && board[2][i] == opponentMark) {
                    return -100; // Defeat
                }
            }
        
            // Check diagonals for victory or defeat
            if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark ||
                board[0][2] == mark && board[1][1] == mark && board[2][0] == mark) {
                return 100; // Victory
            }
            if (board[0][0] == opponentMark && board[1][1] == opponentMark && board[2][2] == opponentMark ||
                board[0][2] == opponentMark && board[1][1] == opponentMark && board[2][0] == opponentMark) {
                return -100; // Defeat
            }
        
            // If no victory or defeat, it's a draw
            return 0;
        }
}
