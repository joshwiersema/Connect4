public class GameStateException extends Exception  {
    /**
     * Custom exception for the game state
     * Cheaters, game logic issues, etc.
     * @autor JM
     * @param message
     */
    public GameStateException(String message) {
        super(message);//passing to superclass constructor
    }
}
