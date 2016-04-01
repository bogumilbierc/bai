package pl.bogumil.bai.controller;

import org.springframework.stereotype.Controller;

/**
 * Created by bbierc on 2016-04-01.
 */
@Controller
public class RegistrationController {

    public String registrationPage(){
        return "registration";
    }

}
