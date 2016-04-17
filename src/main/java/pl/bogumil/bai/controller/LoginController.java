package pl.bogumil.bai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bogumil.bai.LoginFragmentViewModel;
import pl.bogumil.bai.entity.NotExistingUserProfile;
import pl.bogumil.bai.entity.QNotExistingUserProfile;
import pl.bogumil.bai.entity.UserProfile;
import pl.bogumil.bai.exception.UserDoesNotExistsException;
import pl.bogumil.bai.helper.SessionHelper;
import pl.bogumil.bai.repositories.NotExistingUserRepository;
import pl.bogumil.bai.service.LoginService;
import pl.bogumil.bai.service.UserProfileService;

import java.io.IOException;

/**
 * Created by bbierc on 2016-03-31.
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class LoginController extends AbstractController {

    private final SessionHelper sessionHelper;
    private final LoginService loginService;
    private final UserProfileService userProfileService;
    private final NotExistingUserRepository notExistingUserRepository;

    private final static QNotExistingUserProfile qnp = QNotExistingUserProfile.notExistingUserProfile;

    @RequestMapping("/login")
    public String login(@RequestParam("login") String login, @RequestParam("password") String password) throws JsonProcessingException {
        loginService.loginUser(login, password);
        return REDIRECT_TO_HOME_PAGE;
    }

    @RequestMapping("/logout")
    public String logout() {
        sessionHelper.removeUserInSession();
        return REDIRECT_TO_HOME_PAGE;
    }

    @RequestMapping("/loginFragment")
    public String loginFragment() {
        return "loginFragmentEnterLogin";
    }


    @RequestMapping("/loginWithFragmentLoginCheck")

    public String loginWithFragmentEnterLoginPage(@RequestParam("login") String login, @RequestParam(name = "error", required = false) String error, Model model) throws IOException {
        UserProfile userProfile = userProfileService.findByLogin(login);
        if (userProfile != null) {
            model.addAttribute("login", login);
            model.addAttribute("mask", userProfile.getCurrentPasswordMask());
            model.addAttribute("length", userProfile.getPasswordLength());
        } else {
            loginService.manageNotExistingUser(login);
            NotExistingUserProfile notExistingUserProfile = notExistingUserRepository.findOne(qnp.login.eq(login));
            model.addAttribute("login", login);
            model.addAttribute("mask", notExistingUserProfile.getCurrentPasswordMask());
            model.addAttribute("length", notExistingUserProfile.getPasswordLength());
        }
        if (StringUtils.isNotBlank(error)) {
            model.addAttribute("error", "yes");
        }

        return "loginFragmentEnterPassword";
    }

    @RequestMapping("/loginWithFragmentPasswordCheck")
    public String loginPagePasswordFragment(@ModelAttribute LoginFragmentViewModel loginFragmentViewModel, Model model) throws IOException {
        loginService.loginUserPartialPassword(loginFragmentViewModel.getLogin(), loginFragmentViewModel.getPassword());
        return REDIRECT_TO_HOME_PAGE;
    }

}
