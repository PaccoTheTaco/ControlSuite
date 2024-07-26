package tictactoe;

import org.bukkit.entity.Player;

public class TicTacToeManager {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private char[][] board;
    private boolean gameActive;

    public TicTacToeManager() {
        board = new char[3][3];
        resetBoard();
        gameActive = false;
    }

    public void startGame(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = p1;
        resetBoard();
        gameActive = true;

        player1.sendMessage("Tic Tac Toe game started! You are X.");
        player2.sendMessage("Tic Tac Toe game started! You are O.");
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void makeMove(Player player, int row, int col) {
        if (!gameActive || player != currentPlayer || board[row][col] != '-') {
            player.sendMessage("Invalid move.");
            return;
        }

        board[row][col] = player == player1 ? 'X' : 'O';
        if (checkWin()) {
            gameActive = false;
            currentPlayer.sendMessage("You win!");
            (currentPlayer == player1 ? player2 : player1).sendMessage("You lose.");
            return;
        }

        if (isBoardFull()) {
            gameActive = false;
            player1.sendMessage("It's a draw!");
            player2.sendMessage("It's a draw!");
            return;
        }

        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') return false;
            }
        }
        return true;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true;
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true;
        }
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;
        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true;
        return false;
    }

    public char[][] getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean isGameActive() {
        return gameActive;
    }
}
