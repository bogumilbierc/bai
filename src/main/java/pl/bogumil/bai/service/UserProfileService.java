package pl.bogumil.bai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bogumil.bai.dto.UserInSession;
import pl.bogumil.bai.entity.QUserProfile;
import pl.bogumil.bai.entity.UserProfile;
import pl.bogumil.bai.helper.SessionHelper;
import pl.bogumil.bai.repositories.UserProfileRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bbierc on 2016-03-31.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class UserProfileService {

    private final static QUserProfile qup = QUserProfile.userProfile;

    private final UserProfileRepository userProfileRepository;
    private final SessionHelper sessionHelper;

    public List<Integer> getMessagesThatCurrentUserCanEdit() {
        UserInSession userInSession = sessionHelper.getUserFromSession();
        if (userInSession == null) {
            return Collections.emptyList();
        }
        UserProfile userProfile = userProfileRepository.findOne(qup.login.eq(userInSession.getLogin()));
        return userProfile.getAllowedMessages()
                .stream()
                .map(message -> message.getId())
                .collect(Collectors.toList());
    }
}
