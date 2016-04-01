package pl.bogumil.bai.exception;

/**
 * Created by bbierc on 2016-04-01.
 */
public class PasswordDoesNotMeetSpecifiedCriteriaException extends RuntimeException {
    public PasswordDoesNotMeetSpecifiedCriteriaException() {
    }

    public PasswordDoesNotMeetSpecifiedCriteriaException(String message) {
        super(message);
    }
}
