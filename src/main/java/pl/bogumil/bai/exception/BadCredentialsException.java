package pl.bogumil.bai.exception;

/**
 * Created by bbierc on 2016-04-01.
 */
public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
