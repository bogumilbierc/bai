package pl.bogumil.bai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bogumil.bai.helper.SessionHelper;
import pl.bogumil.bai.service.LoginService;

/**
 * Created by bbierc on 2016-03-31.
 */
@Controller
public class LoginController extends AbstractController {

    @Autowired
    private SessionHelper sessionHelper;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public String login(@RequestParam("login") String login, @RequestParam("password") String password) {
        loginService.loginUser(login, password);
        return REDIRECT_TO_HOME_PAGE;
    }

    @RequestMapping("/logout")
    public String logout() {
        sessionHelper.removeUserInSession();
        return REDIRECT_TO_HOME_PAGE;
    }

}
