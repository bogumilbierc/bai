package pl.bogumil.bai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bogumil.bai.dto.UserInSession;
import pl.bogumil.bai.helper.SessionHelper;

/**
 * Created by bbierc on 2016-03-31.
 */
@Controller
public class LoginController extends AbstractController{

    @Autowired
    private SessionHelper sessionHelper;

    @RequestMapping("/login")
    public String login(@RequestParam("login") String login, @RequestParam("password") String password) {
        UserInSession userInSession = new UserInSession();
        userInSession.setLogin(login);
        userInSession.setId(-1);
        sessionHelper.setUserInSession(userInSession);
        return REDIRECT_TO_HOME_PAGE;
    }

    @RequestMapping("/logout")
    public String logout() {
        sessionHelper.removeUserInSession();
        return REDIRECT_TO_HOME_PAGE;
    }

}
