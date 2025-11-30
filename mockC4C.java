// public import java.io.*;
// import java.net.*;
// import java.util.Scanner;
// import java.util.InputMismatchException;
// import java.util.NoSuchElementException;

// public class mockC4C {
//     public static void main(String[] args) {
//         Scanner kb = new Scanner(System.in);
//         System.out.print("Enter server IP or hostname: ");
//         String serverAddress = kb.nextLine();

//         try (Socket socket = new Socket(serverAddress, 6789)) {
//             Scanner in = new Scanner(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//             //Scanner scanner = new Scanner(System.in);

//             if(in.hasNextLine()){
//                 System.out.println(in.nextLine());
//             }else{
//                 System.out.println("No input from server. Connection Closed");
//                 return;
//             }

//              // Initial player message (e.g., You are Player X)
//             boolean gamePlaying = true;
//             while (gamePlaying) {
//                 // Read and display the board
//                 for (int i = 0; i < 6; i++) {
//                     System.out.println(in.nextLine());
//                     if(in.hasNextLine()){
//                         System.out.println(in.nextLine());
//                     }else{
//                         System.out.println("No message from server.");
//                         return;
//                     }
//                 }
//                 System.out.println(in.nextLine()); // Column numbers

//                 String message = in.nextLine(); // Read the server's prompt
//                 if (message != null) {
//                     System.out.println("Server says: " + message); // Debugging statement
//                     String response = kb.nextLine(); // Get the user's input
//                     out.println(response); // Send the response to the server
//                     out.flush(); // Ensure the message is sent immediately
//                 }

//                 if (message.contains("wins")) {
//                     System.out.println(message);
                
//                     if (message.contains("Would you like to play again?")) {
//                         System.out.print("Enter your response (Y/N): ");
//                         String response = kb.nextLine();
//                         out.println(response); // Send response to the server
//                         if (response.equalsIgnoreCase("N")) {
//                             gamePlaying = false;
//                         }
//                     } else {
//                         System.out.println("Waiting for the winner's decision...");
//                     }
//                 }

//                 if (message.contains("Your turn")) {
//                     int column;
//                     while (true) {
//                         System.out.print("Enter a column (1-7): ");
//                         try {
//                             column = kb.nextInt();
//                             kb.nextLine(); // Clear the newline
//                             if (column < 1 || column > 7) {
//                                 throw new InvalidInputException("Invalid input. Please enter a number between 1 and 7.");
//                             }
//                             break;
//                         } catch (InvalidInputException e) {
//                             System.out.println(e.getMessage());
//                         } catch (InputMismatchException e) {
//                             System.out.println("Invalid input. Please enter a number between 1 and 7.");
//                             kb.nextLine(); // Clear invalid input
//                         }
//                     }
//                     out.println(column);
//                     out.flush();
//                 }

//             }
//             //scanner.close();
//             in.close();
//             out.close();
//         } catch (IOException e) {
//             System.out.println("Connection error: " + e.getMessage());
//         }
//         kb.close();
//     }
// } {
    
// }
