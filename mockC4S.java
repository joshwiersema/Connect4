import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.lang.NumberFormatException;
import java.util.Scanner;

public class mockC4S {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final char EMPTY = '.';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    private char[][] board = new char[ROWS][COLUMNS];
    private boolean gameWon = false;

    public Connect4Server() {
        for (char[] row : board) {
            Arrays.fill(row, EMPTY);
        }
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(6789)) {
            System.out.println("Server started. Waiting for two players to connect...");

            System.out.println("Waiting for Player 1 to connect...");
            Socket player1Socket = serverSocket.accept();
            System.out.println("Player 1 connected.");
            Save.clearSavedGame();

            PrintWriter out1 = new PrintWriter(player1Socket.getOutputStream(), true);
            Scanner in1 = new Scanner(new InputStreamReader(player1Socket.getInputStream()));
            out1.println("You are Player X.");

            Socket player2Socket = serverSocket.accept();
            PrintWriter out2 = new PrintWriter(player2Socket.getOutputStream(), true);
            Scanner in2 = new Scanner(new InputStreamReader(player2Socket.getInputStream()));
            out2.println("You are Player O");

            boolean playAgain = true;
            while(playAgain){
                
                //char currentPlayer = PLAYER1;
                char currentPlayer = PLAYER1; // Start with Player 1
                // Check if a saved game exists
                // try {
                //     Object[] savedGame = Save.loadBoard();
                //     board = (char[][]) savedGame[0];
                //     currentPlayer = (char) savedGame[1];
                //     System.out.println("Loaded saved game.");
                // } catch (GameStateException e) {
                //     System.out.println(e.getMessage());
                // }
                
                PrintWriter currentOut = out1;
                Scanner currentIn = in1;
                PrintWriter otherOut = out2;

                while (!gameWon) {
                    sendBoard(out1);
                    sendBoard(out2);

                    currentOut.println("Your turn! Enter a column (1-7):");
                    otherOut.println("Waiting for the other player...");

                    int column;
                    try {
                        String input = currentIn.nextLine();
                        if (!input.matches("\\d+")) {
                            throw new InvalidInputException("Input is not a valid number.");
                        }
                        column = Integer.parseInt(input) - 1;
                    } catch (InvalidInputException e) {
                        currentOut.println(e.getMessage());
                        continue;
                    } catch (NumberFormatException e) {
                        currentOut.println("Invalid input. Please enter a number between 1 and 7.");
                        continue;
                    }

                    if (!dropPiece(column, currentPlayer)) {
                        currentOut.println("Invalid move. Try again.");
                        continue;
                    }

                    // Save the updated board and next player
                    Save.saveBoard(board, currentPlayer);

                    //checks for a winner winner chicken dinner
                    //also asks if want to play again
                    if (checkWin(currentPlayer)) {
                        sendBoard(out1);
                        sendBoard(out2);
                        out1.println("You win! Would you like to play again? (Y/N)");
                        out2.println("layer " + currentPlayer + " wins! Waiting for their decision...");
                        String response = currentIn.nextLine();

                        if (response.equalsIgnoreCase("Y")) {
                            // Reset the board and continue the game
                            Save.clearSavedGame(); // Clear the saved game
                            gameWon = false; // Reset game state
                            currentOut.println("We are playin again boys");
                            otherOut.println("We are playin again boys");
                            break; // Restart the game loop
                        } else {
                            currentOut.println("Game over. Thanks for playing!");
                            otherOut.println("Game over. Thanks for playing!");
                            break; // Exit the game loop
                        }
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

                player1Socket.close();
                player2Socket.close();
                in1.close();
                in2.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void sendBoard(PrintWriter out) {
        for (char[] row : board) {
            for (char cell : row) {
                out.print(cell + " ");
            }
            out.println();
        }
        out.println("1 2 3 4 5 6 7");
    }

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

    public static void main(String[] args) {
        new Connect4Server().startServer();
    }
}