package pl.bogumil.bai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bogumil.bai.service.MessageService;

/**
 * Created by bbierc on 2016-03-31.
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageController {

    private final MessageService messageService;

    @RequestMapping("/removeMessage")
    public String removeMessage(@RequestParam("messageId") Integer messageId) {
        messageService.removeMessage(messageId);
        return "redirect:/";
    }

    @RequestMapping("/manageAllowance")
    public String manageAllowancePage(@RequestParam("messageId") Integer messageId, Model model) {
        model.addAttribute("message", messageService.getMessageForManagement(messageId));
        return "manageAllowance";
    }

}
