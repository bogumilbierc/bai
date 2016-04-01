package pl.bogumil.bai.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.bogumil.bai.exception.*;

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

    @ExceptionHandler(UserIsBlockedException.class)
    public String userIsBlockedHandler() {
        return "userIsBlocked";
    }

    @ExceptionHandler(DelayNeededException.class)
    public String delayNeededHandler(DelayNeededException exception, Model model) {
        model.addAttribute("blockadeDeadline", exception.getMessage());
        return "delayNeeded";
    }


}
