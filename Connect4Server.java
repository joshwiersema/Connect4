import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.lang.NumberFormatException;

/**
 * @author JM and JW
 * @version 1.7
 * Server part of Connect 4; It connects to the server, displays the board, playing the game
 * Creates the "board" and regulates rules
 */

public class Connect4Server {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final char EMPTY = '.';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    private char[][] board = new char[ROWS][COLUMNS];
    private boolean gameWon = false;

    /**
     * What creates the board and the empty spaces to be filled with symbols later on
     */
    public Connect4Server() {
        for (char[] row : board) {
            Arrays.fill(row, EMPTY);
        }
    }

    /**
     * Starts off server. Waits for 2 players
     */
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(6789)) {
            System.out.println("Server started. Waiting for two players to connect...");

            //Player 1 or X
            Socket player1Socket = serverSocket.accept();
            PrintWriter out1 = new PrintWriter(player1Socket.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
            out1.println("You are Player X");

            //Player 2 or O
            Socket player2Socket = serverSocket.accept();
            PrintWriter out2 = new PrintWriter(player2Socket.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
            out2.println("You are Player O");

            //Flag to control the while loop
            boolean playAgain = true;

            while(playAgain){

                //clears the board for a new game
                for (char[] row : board) {
                    Arrays.fill(row, EMPTY);
                }
                //controls the 2nd while loop of gameWon
                gameWon = false;
                //Player 1 starts. The person who connects to the server 1st
                char currentPlayer = PLAYER1;
                PrintWriter currentOut = out1;
                BufferedReader currentIn = in1;
                PrintWriter otherOut = out2;

                //main loop
                while (!gameWon) {
                    //sends the board to corresponding playeer
                    sendBoard(out1);
                    sendBoard(out2);

                    currentOut.println("Your turn! Enter a column (1-7):");
                    otherOut.println("Waiting for the other player...");

                    int column;
                    try {
                        String input = currentIn.readLine();
                        column = Integer.parseInt(input) - 1;

                        //Checks if the column input is valid, if not, throws custom exception
                        if (column < 0 || column >= COLUMNS) {
                            throw new InputMismatchException("Column out of range.");
                        }
                    } catch (NumberFormatException | InputMismatchException e) {
                        currentOut.println("Invalid input. Please enter a number between 1 and 7.");
                        continue;
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }

                    //
                    if (!dropPiece(column, currentPlayer)) {
                        currentOut.println("Invalid move. Try again.");
                        continue;
                    }

                    //Check for the win by another player
                    if (checkWin(currentPlayer)) {
                        sendBoard(out1);
                        sendBoard(out2);
                        out1.println("Player " + currentPlayer + " wins!");
                        out2.println("Player " + currentPlayer + " wins!");
                        gameWon = true;
                        break;
                    }

                    // Switch players
                    if (currentPlayer == PLAYER1) {
                        currentPlayer = PLAYER2;
                        currentOut = out2;
                        currentIn = in2;
                        otherOut = out1;
                    } else {
                        currentPlayer = PLAYER1;
                        currentOut = out1;
                        currentIn = in1;
                        otherOut = out2;
                    }
                }
            //closes the sockets opeded up in the beginning
            player1Socket.close();
            player2Socket.close();
        }} catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * current state of the board
     *
     *
     * @param outPrintWriter
     */
    private void sendBoard(PrintWriter out) {
        for (char[] row : board) {
            for (char cell : row) {
                out.print(cell + " ");
            }
            out.println();
        }
        out.println("1 2 3 4 5 6 7");
    }

    /**
     * Dropping a symbol into the column in the board via the player
     * @param column
     * @param player
     * @return if piece goes in successfully or not: true to false
     */
    private boolean dropPiece(int column, char player) {
        if (column < 0 || column >= COLUMNS) return false;
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column] == EMPTY) {
                board[i][column] = player;
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param player
     * @return Whether or no the player wins: true to false
     */
    private boolean checkWin(char player) {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j <= COLUMNS - 4; j++)
                if (board[i][j] == player && board[i][j + 1] == player &&
                    board[i][j + 2] == player && board[i][j + 3] == player)
                    return true;

        for (int i = 0; i <= ROWS - 4; i++)
            for (int j = 0; j < COLUMNS; j++)
                if (board[i][j] == player && board[i + 1][j] == player &&
                    board[i + 2][j] == player && board[i + 3][j] == player)
                    return true;

        for (int i = 3; i < ROWS; i++)
            for (int j = 0; j <= COLUMNS - 4; j++)
                if (board[i][j] == player && board[i - 1][j + 1] == player &&
                    board[i - 2][j + 2] == player && board[i - 3][j + 3] == player)
                    return true;

        for (int i = 0; i <= ROWS - 4; i++)
            for (int j = 0; j <= COLUMNS - 4; j++)
                if (board[i][j] == player && board[i + 1][j + 1] == player &&
                    board[i + 2][j + 2] == player && board[i + 3][j + 3] == player)
                    return true;

        return false;
    }

    /**
     * Connection shaleod tnj
     * @param args
     */
    public static void main(String[] args) {
        new Connect4Server().startServer();
    }
}