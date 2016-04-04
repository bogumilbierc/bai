package pl.bogumil.bai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bogumil.bai.helper.SessionHelper;
import pl.bogumil.bai.service.UserProfileService;

/**
 * Created by bbierc on 2016-04-01.
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final SessionHelper sessionHelper;

    @RequestMapping("/userAccountPage")
    public String userAccountPage(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            switch (error) {
                case "oldPass":
                    model.addAttribute("error", "Błędne stare hasło");
                    break;
                case "newPass":
                    model.addAttribute("error", "Błędny format hasła (min 8 max 16 znaków)");
                    break;
            }

        }
        model.addAttribute("user", userProfileService.getCurrentUser());
        model.addAttribute("sessionUser", sessionHelper.getUserFromSession());
        return "userAccount";
    }

    @RequestMapping("/editUser")
    public String editAccount(@RequestParam Integer delay, @RequestParam Integer attempts) {
        userProfileService.editUserAccount(delay, attempts);
        return "redirect:/userAccountPage";
    }

    @RequestMapping("/changePassword")
    public String changePassword(@RequestParam String password, @RequestParam String oldPassword) throws JsonProcessingException {
        userProfileService.changePassword(password, oldPassword);
        return "redirect:/userAccountPage";
    }
}
