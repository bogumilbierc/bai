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
public class MessageController extends AbstractController {

    private final MessageService messageService;

    @RequestMapping("/removeMessage")
    public String removeMessage(@RequestParam("messageId") Integer messageId) {
        messageService.removeMessage(messageId);
        return REDIRECT_TO_HOME_PAGE;
    }

    @RequestMapping("/manageAllowance")
    public String manageAllowancePage(@RequestParam("messageId") Integer messageId, Model model) {
        model.addAttribute(MESSAGE, messageService.getMessageForManagement(messageId));
        model.addAttribute("notAllowedUsers", messageService.getUsersThatDontHaveGrantsToMesage(messageId));
        return "manageAllowance";
    }

    @RequestMapping("/grantAccess")
    public String grantAccessToMessage(@RequestParam("messageId") Integer messageId, @RequestParam("userId") Integer userId) {
        messageService.grantAccessToMessage(messageId, userId);
        return "redirect:/manageAllowance?messageId=" + messageId;
    }

    @RequestMapping("/revokeAccess")
    public String revokeAccessToMessage(@RequestParam("messageId") Integer messageId, @RequestParam("userId") Integer userId) {
        messageService.revokeAccessToMessage(messageId, userId);
        return "redirect:/manageAllowance?messageId=" + messageId;
    }

    @RequestMapping("/editMessagePage")
    public String editMessagePage(@RequestParam("messageId") Integer messageId, Model model) {
        model.addAttribute(MESSAGE, messageService.getMessageForEdit(messageId));
        return "editMessage";
    }

    @RequestMapping("/editMessage")
    public String editMessage(@RequestParam("messageId") Integer messageId, @RequestParam("content") String content) {
        messageService.editMessage(messageId, content);
        return REDIRECT_TO_HOME_PAGE;
    }

}
