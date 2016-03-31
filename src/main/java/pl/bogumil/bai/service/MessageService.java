package pl.bogumil.bai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bogumil.bai.dto.UserInSession;
import pl.bogumil.bai.entity.Message;
import pl.bogumil.bai.entity.QUserProfile;
import pl.bogumil.bai.entity.UserProfile;
import pl.bogumil.bai.exception.Unauthorized403Exception;
import pl.bogumil.bai.helper.SessionHelper;
import pl.bogumil.bai.repositories.MessageRepository;
import pl.bogumil.bai.repositories.UserProfileRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by bbierc on 2016-03-31.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
@Slf4j
public class MessageService {

    private static final QUserProfile qup = QUserProfile.userProfile;

    private final MessageRepository messageRepository;
    private final UserProfileRepository userProfileRepository;
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

    @Transactional
    public void grantAccessToMessage(Integer messageId, Integer userId) {
        UserInSession userInSession = sessionHelper.getUserFromSession();
        if (userInSession == null) {
            throw new Unauthorized403Exception();
        }
        Message message = messageRepository.findOne(messageId);
        if (message.getOwner().getLogin().equals(userInSession.getLogin())) {
            UserProfile user = userProfileRepository.findOne(userId);
            message.getUsers().add(user);
            messageRepository.saveAndFlush(message);
            user.getAllowedMessages().add(message);
            userProfileRepository.saveAndFlush(user);
            log.info("nadano uprawnienia dostepu do wiadomosci: " + messageId + " dla usera: " + userId);
        } else {
            log.warn("próba nadawania dostepu dla cudzej wiadomosci, messageId: " + messageId + " user: " + userInSession.getLogin());
            throw new Unauthorized403Exception();
        }
    }

    @Transactional
    public void revokeAccessToMessage(Integer messageId, Integer userId) {
        UserInSession userInSession = sessionHelper.getUserFromSession();
        if (userInSession == null) {
            throw new Unauthorized403Exception();
        }
        Message message = messageRepository.findOne(messageId);
        if (message.getOwner().getLogin().equals(userInSession.getLogin())) {
            UserProfile user = userProfileRepository.findOne(userId);
            message.getUsers().remove(user);
            messageRepository.saveAndFlush(message);
            user.getAllowedMessages().remove(message);
            userProfileRepository.saveAndFlush(user);
            log.info("zabrano uprawnienia dostepu do wiadomosci: " + messageId + " dla usera: " + userId);
        } else {
            log.warn("próba zabrania dostepu dla cudzej wiadomosci, messageId: " + messageId + " user: " + userInSession.getLogin());
            throw new Unauthorized403Exception();
        }
    }

    public List<UserProfile> getUsersThatDontHaveGrantsToMesage(Integer messageId) {
        Message message = messageRepository.findOne(messageId);
        Iterable<UserProfile> userProfileIterable = userProfileRepository.findAll(qup.notIn(message.getUsers()).and(qup.ne(message.getOwner())));
        return StreamSupport
                .stream(userProfileIterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
