package pl.bogumil.bai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bogumil.bai.dto.UserInSession;
import pl.bogumil.bai.entity.QUserProfile;
import pl.bogumil.bai.entity.UserProfile;
import pl.bogumil.bai.exception.PasswordDoesNotMeetSpecifiedCriteriaException;
import pl.bogumil.bai.exception.UserWithThatLoginAlreadyExistsException;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserProfile save(UserProfile userProfile) {
        userProfileRepository.saveAndFlush(userProfile);
        return userProfile;
    }

    public UserProfile findByLogin(String login) {
        return userProfileRepository.findOne(qup.login.eq(login));
    }

    @Transactional
    public void registerUser(String login, String password, Integer delay, Integer attempts) {
        UserProfile userProfile = findByLogin(login);
        if (userProfile != null) {
            throw new UserWithThatLoginAlreadyExistsException();
        }
        validatePassword(password);
        userProfile = new UserProfile();
        userProfile.setLogin(login);
        userProfile.setDelayInSeconds(delay);
        userProfile.setNumberOfAttempsBeforeBlockade(attempts);
        userProfile.setNumberOfFailedLoginsSinceLast(0);
        userProfile.setPassword(passwordEncoder.encode(password));
        userProfile.setIsActive(true);
        userProfileRepository.saveAndFlush(userProfile);
    }

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

    private void validatePassword(String password) {
        if (password.length() < 8 || password.length() > 16) {
            throw new PasswordDoesNotMeetSpecifiedCriteriaException();
        }
    }
}
