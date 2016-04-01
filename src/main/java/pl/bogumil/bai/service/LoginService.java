package pl.bogumil.bai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bogumil.bai.dto.UserInSession;
import pl.bogumil.bai.entity.UserProfile;
import pl.bogumil.bai.exception.BadCredentialsException;
import pl.bogumil.bai.exception.UserDoesNotExistsException;
import pl.bogumil.bai.helper.SessionHelper;

import java.time.LocalDateTime;

/**
 * Created by bbierc on 2016-04-01.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
@Slf4j
public class LoginService {

    private final UserProfileService userProfileService;
    private final SessionHelper sessionHelper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void loginUser(String login, String password) {
        UserProfile userProfile = userProfileService.findByLogin(login);
        if (userProfile == null) {
            throw new UserDoesNotExistsException();
        }
        if (passwordEncoder.matches(password, userProfile.getPassword())) {
            UserInSession userInSession = new UserInSession();
            userInSession.setLogin(login);
            userInSession.setId(userProfile.getId());
            userInSession.setLastLoginDate(userProfile.getLastLoginDate());
            sessionHelper.setUserInSession(userInSession);
            userProfile.setLastLoginDate(LocalDateTime.now());
            userProfileService.save(userProfile);
            return;
        }
        throw new BadCredentialsException();
    }

}
