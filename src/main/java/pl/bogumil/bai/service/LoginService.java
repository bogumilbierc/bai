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
import pl.bogumil.bai.exception.DelayNeededException;
import pl.bogumil.bai.exception.UserDoesNotExistsException;
import pl.bogumil.bai.exception.UserIsBlockedException;
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

    @Transactional(noRollbackFor = BadCredentialsException.class)
    public void loginUser(String login, String password) {
        UserProfile userProfile = userProfileService.findByLogin(login);
        if (userProfile == null) {
            throw new UserDoesNotExistsException();
        }
        if (!userProfile.getIsActive()) {
            throw new UserIsBlockedException();
        }
        if (userProfile.getBlockadeDeadline() != null && userProfile.getBlockadeDeadline().isAfter(LocalDateTime.now())) {
            throw new DelayNeededException(userProfile.getBlockadeDeadline().toString());
        }
        if (passwordEncoder.matches(password, userProfile.getPassword())) {
            UserInSession userInSession = new UserInSession();
            userInSession.setLogin(login);
            userInSession.setId(userProfile.getId());
            userInSession.setLastLoginDate(userProfile.getLastLoginDate());
            sessionHelper.setUserInSession(userInSession);
            userProfile.setLastLoginDate(LocalDateTime.now());
            userProfile.setNumberOfFailedLoginsSinceLast(0);
            userProfileService.save(userProfile);
            return;
        }
        manageBlocks(userProfile);
        throw new BadCredentialsException();
    }

    private void manageBlocks(UserProfile userProfile) {
        userProfile.setLastFailedLoginDate(LocalDateTime.now());
        Integer previousFailedLogins = userProfile.getNumberOfFailedLoginsSinceLast();
        if (previousFailedLogins == null) {
            previousFailedLogins = 0;
        }
        userProfile.setNumberOfFailedLoginsSinceLast(previousFailedLogins + 1);
        userProfile.setBlockadeDeadline(LocalDateTime.now().plusSeconds(userProfile.getNumberOfFailedLoginsSinceLast() * userProfile.getDelayInSeconds()));
        if (userProfile.getNumberOfFailedLoginsSinceLast() > userProfile.getNumberOfAttempsBeforeBlockade()) {
            userProfile.setIsActive(false);
        }
        userProfileService.save(userProfile);
    }

}
