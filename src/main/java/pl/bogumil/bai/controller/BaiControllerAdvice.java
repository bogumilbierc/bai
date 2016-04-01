package pl.bogumil.bai.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.bogumil.bai.exception.BadCredentialsException;
import pl.bogumil.bai.exception.Unauthorized403Exception;
import pl.bogumil.bai.exception.UserDoesNotExistsException;

/**
 * Created by bbierc on 2016-03-31.
 */
@ControllerAdvice
public class BaiControllerAdvice {

    @ExceptionHandler(Unauthorized403Exception.class)
    public String unauthorizedHandler() {
        return "403";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String badCredentialsHandler() {
        return "badCredentials";
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    public String userDoesNotExistsHandler() {
        return "userDoesNotExists";
    }

}
