package pl.bogumil.bai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bogumil.bai.service.UserProfileService;

/**
 * Created by bbierc on 2016-04-01.
 */
@Controller
public class RegistrationController extends AbstractController {

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @RequestMapping("/registerUser")
    public String registerUser(@RequestParam String login, @RequestParam String password, @RequestParam Integer delay, @RequestParam Integer attempts) {
        userProfileService.registerUser(login, password, delay, attempts);
        return REDIRECT_TO_HOME_PAGE;
    }

}
