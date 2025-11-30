import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * @author JM and JW
 * @version 1.7
 * 
 */
public class Connect4Client {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter server IP or hostname: ");
        String serverAddress = kb.nextLine();

        try (Socket socket = new Socket(serverAddress, 6789)) {
            //Input and outsput streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println(in.readLine()); // Initial player message (e.g., You are Player X)

            //main loop
            while (true) {
                // Display the board
                for (int i = 0; i < 6; i++) {
                    System.out.println(in.readLine());
                }
                System.out.println(in.readLine()); // Column numbers

                String message = in.readLine();
                if (message == null) break;
                System.out.println(message);

                if (message.contains("wins")) {
                    break;
                }

                //If the player's turn its next yours
                if (message.contains("Your turn")) {
                    int column;
                    while (true) {
                        System.out.print("Enter a column (1-7): ");
                        try {
                            column = kb.nextInt();
                            kb.nextLine(); // Clear newline
                            if (column < 1 || column > 7) {
                                throw new InvalidInputException("Invalid input. Please enter a number between 1 and 7.");
                            }
                            break;//extting the loop
                        } catch (InvalidInputException e) {
                            System.out.println(e.getMessage());
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number between 1 and 7.");
                            kb.nextLine(); // Clear invalid input
                        }
                    }
                    out.println(column);
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}