import java.io.*;

public class Save {
    private static final String SAVE_FILE = "save.data";

    // Save the board and the next player to the file
    public static void saveBoard(char[][] board, char nextPlayer) {
        try (FileOutputStream fos = new FileOutputStream(SAVE_FILE, false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(board); // Save the board
            oos.writeChar(nextPlayer); // Save the next player
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    // Load the board and the next player from the file
    public static Object[] loadBoard() throws GameStateException {
        ensureSaveFileExists(); // Ensure the file exists before loading
        try (FileInputStream fis = new FileInputStream(SAVE_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            char[][] board = (char[][]) ois.readObject(); // Load the board
            char nextPlayer = ois.readChar(); // Load the next player
            System.out.println("Game loaded successfully.");
            return new Object[]{board, nextPlayer};
        } catch (IOException | ClassNotFoundException e) {
            throw new GameStateException("Failed to load the saved game: " + e.getMessage());
        }
    }

    // Clear the saved game by deleting the file
    public static void clearSavedGame() {
        File saveFile = new File(SAVE_FILE);
        if (saveFile.exists() && saveFile.delete()) {
            System.out.println("Saved game cleared.");
        } else {
            System.out.println("No saved game to clear.");
        }
    }

    // Ensure the save file exists; create it if it doesn't
    private static void ensureSaveFileExists() {
        File saveFile = new File(SAVE_FILE);
        if (!saveFile.exists()) {
            try {
                if (saveFile.createNewFile()) {
                    System.out.println("Save file created: " + SAVE_FILE);
                }
            } catch (IOException e) {
                System.out.println("Failed to create save file: " + e.getMessage());
            }
        }
    }
}