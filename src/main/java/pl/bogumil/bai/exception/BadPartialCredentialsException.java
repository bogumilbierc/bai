package pl.bogumil.bai.exception;

/**
 * Created by bbierc on 2016-04-01.
 */
public class BadPartialCredentialsException extends RuntimeException {

    public BadPartialCredentialsException() {
    }

    public BadPartialCredentialsException(String message) {
        super(message);
    }
}
