package pl.bogumil.bai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bogumil.bai.dto.UserInSession;
import pl.bogumil.bai.entity.Message;
import pl.bogumil.bai.exception.Unauthorized403Exception;
import pl.bogumil.bai.helper.SessionHelper;
import pl.bogumil.bai.repositories.MessageRepository;

import java.util.List;

/**
 * Created by bbierc on 2016-03-31.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final SessionHelper sessionHelper;

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Transactional
    public void removeMessage(Integer messageId) {
        UserInSession userInSession = sessionHelper.getUserFromSession();
        if (userInSession == null) {
            throw new Unauthorized403Exception();
        }

        Message message = messageRepository.findOne(messageId);
        if (message.getOwner().getLogin().equals(userInSession.getLogin())) {
            messageRepository.delete(messageId);
            messageRepository.flush();
            log.info("usunieto wiadomosc o id: " + messageId);
        } else {
            log.warn("próba usuniecia cudzej wiadomosci, messageId: " + messageId + " user: " + userInSession.getLogin());
            throw new Unauthorized403Exception();
        }

    }

    public Message getMessageForManagement(Integer id) {

        UserInSession userInSession = sessionHelper.getUserFromSession();
        if (userInSession == null) {
            throw new Unauthorized403Exception();
        }

        Message message = messageRepository.findOne(id);
        if (message.getOwner().getLogin().equals(userInSession.getLogin())) {
            return message;
        } else {
            log.warn("próba zarzadzania cudza wiadomoscia, messageId: " + id + " user: " + userInSession.getLogin());
            throw new Unauthorized403Exception();
        }
    }

    public void grantAccessToMessage(Integer messageId, Integer userId) {

    }
}
