public class InvalidInputException extends Exception {
    /**
     * Custom exception when an opposing player attmepts to make an invalid move.
     * Non-number, number ouside scope, etc.
     * @autor JM
     * @param message
     */
    public InvalidInputException(String message) {
        super(message);//passing to superclass constructor
    }
}
