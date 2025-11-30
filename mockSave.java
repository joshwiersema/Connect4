import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class mockSave {
    public static void saveBoard(char[][] board, char nextPlayer) {
        try (FileOutputStream fos = new FileOutputStream("save.data", false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(board); // Save the board
            oos.writeChar(nextPlayer); // Save the next player
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }
    
    public static Object[] loadBoard() throws GameStateException {
        try (FileInputStream fis = new FileInputStream("save.data");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            char[][] board = (char[][]) ois.readObject(); // Load the board
            char nextPlayer = ois.readChar(); // Load the next player
            return new Object[]{board, nextPlayer};
        } catch (IOException | ClassNotFoundException e) {
            throw new GameStateException("Failed to load the saved game: " + e.getMessage());
        }
    }

    public static void clearSavedGame() {
        try (FileOutputStream fos = new FileOutputStream("save.data", false)) {
            // Overwrite the file with no content
            System.out.println("Saved game cleared.");
        } catch (IOException e) {
            System.out.println("Failed to clear saved game: " + e.getMessage());
        }
    }
}