package pl.bogumil.bai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bogumil.bai.service.MessageService;
import pl.bogumil.bai.service.UserProfileService;

/**
 * Created by bbierc on 2016-03-31.
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController extends AbstractController {

    private static final String INDEX_FTL = "index";
    private final MessageService messageService;
    private final UserProfileService userProfileService;

    @RequestMapping("/")
    public String indexPage(Model model) {
        model.addAttribute(MESSAGES, messageService.findAll());
        model.addAttribute(ALLOWED_MESSAGES, userProfileService.getMessagesThatCurrentUserCanEdit());
        return INDEX_FTL;
    }

}
