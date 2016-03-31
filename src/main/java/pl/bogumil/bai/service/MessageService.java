package pl.bogumil.bai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bogumil.bai.entity.Message;
import pl.bogumil.bai.repositories.MessageRepository;

import java.util.List;

/**
 * Created by bbierc on 2016-03-31.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
