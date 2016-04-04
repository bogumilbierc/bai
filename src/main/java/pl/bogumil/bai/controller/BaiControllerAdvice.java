package pl.bogumil.bai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public String badCredentialsHandler(BadCredentialsException exception) {

        if (exception.getMessage() != null && exception.getMessage().equals("edycja")) {
            return "redirect:/userAccountPage?error=oldPass";
        }
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

    @ExceptionHandler(UserWithThatLoginAlreadyExistsException.class)
    public String userWithThatLoginAlreadyExistsHandler(Model model) {
        model.addAttribute("error", "Login zajęty");
        return "registration";
    }

    @ExceptionHandler(PasswordDoesNotMeetSpecifiedCriteriaException.class)
    public String passwordDoesNotMeetSpecifiedCriteriaHandler(Model model, PasswordDoesNotMeetSpecifiedCriteriaException exception) {
        if (exception.getMessage() != null && exception.getMessage().equals("edycja")) {
            return "redirect:/userAccountPage?error=newPass";
        }
        model.addAttribute("error", "Błędny format hasła (min 8 max 16 znaków)");
        return "registration";
    }

    @ExceptionHandler(JsonProcessingException.class)
    public String jsonProcessingErrorHandler() {
        return "jsonProcessingError";
    }

    @ExceptionHandler(BadPartialCredentialsException.class)
    public String badPartialCredentialsHandler(BadPartialCredentialsException exception) {
        return "redirect:/loginWithFragmentLoginCheck?login=" + exception.getMessage() + "&error=yes";
    }


}
