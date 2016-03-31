package pl.bogumil.bai.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.bogumil.bai.exception.Unauthorized403Exception;

/**
 * Created by bbierc on 2016-03-31.
 */
@ControllerAdvice
public class BaiControllerAdvice {

    @ExceptionHandler(Unauthorized403Exception.class)
    public String unauthorizedHandler(Unauthorized403Exception exception) {
        return "403";
    }

}
