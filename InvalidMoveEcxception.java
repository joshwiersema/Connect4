public class InvalidMoveEcxception extends Exception {
    /**
     * Custom exception an oposing player attmepts to make an invalid move.
     * Column out of range, full column, etc. 
     * @autor JM
     * @param message
     */
    public InvalidMoveEcxception(String message) {
        super(message);//passing to superclass constructor
    }
}
