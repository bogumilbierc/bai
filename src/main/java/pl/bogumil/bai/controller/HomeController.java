package pl.bogumil.bai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bogumil.bai.service.MessageService;

/**
 * Created by bbierc on 2016-03-31.
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {
    private static final String INDEX_FTL = "index";
    private static final String MESSAGES = "messages";

    private final MessageService messageService;

    @RequestMapping("/")
    public String indexPage(Model model) {
        model.addAttribute(MESSAGES, messageService.findAll());
        return INDEX_FTL;
    }

}
