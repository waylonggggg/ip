package sonder;

/**
 * Represents a custom exception for the Sonder application.
 * This exception is thrown when an error specific to the application occurs.
 */
public class SonderException extends Exception {
    /**
     * Constructs a {@code SonderException} with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public SonderException(String message) {
        super(message);
    }
}
