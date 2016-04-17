package pl.bogumil.bai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.bogumil.bai.dto.UserInSession;
import pl.bogumil.bai.entity.*;
import pl.bogumil.bai.exception.*;
import pl.bogumil.bai.helper.SessionHelper;
import pl.bogumil.bai.repositories.NotExistingUserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by bbierc on 2016-04-01.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
@Slf4j
public class LoginService {

    private static final QNotExistingUserProfile qnesp = QNotExistingUserProfile.notExistingUserProfile;

    private final UserProfileService userProfileService;
    private final SessionHelper sessionHelper;
    private final PasswordEncoder passwordEncoder;
    private final NotExistingUserRepository notExistingUserRepository;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 16;
    public static final int NUMBER_OF_PASSWORD_FRAGMENTS = 10;
    public static final int MIN_LENGTH_OF_PASSWORD_FRAGMENT = 5;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional(noRollbackFor = {BadPartialCredentialsException.class, UserDoesNotExistsException.class})
    public void loginUserPartialPassword(String login, String[] password) throws IOException {
        UserProfile userProfile = loginUserCommon(login);
        if(userProfile==null){
            throw new BadPartialCredentialsException(login);
        }
        PasswordFragment passwordFragment = userProfile.getCurrentPasswordFragment();
        String concatenatedPassword = "";
        List<Integer> passwordMask = passwordFragment.getPasswordMask();
        for (int i = 0; i < password.length; i++) {
            if (passwordMask.contains(i)) {
                if (password[i] != null && !password[i].isEmpty()) {
                    concatenatedPassword += password[i].charAt(0);

                }
            }
        }
        if (passwordEncoder.matches(concatenatedPassword, passwordFragment.getPasswordHash())) {
            fillUserInSession(userProfile);
            int previousFragmentId = userProfile.getCurrentPasswordFragmentId();
            int currentFragmentId;

            do {
                currentFragmentId = userProfile.getPasswordFragments().get(ThreadLocalRandom.current().nextInt(1, 5)).getId();
            } while (previousFragmentId == currentFragmentId);
            userProfile.setCurrentPasswordFragmentId(currentFragmentId);
            userProfileService.save(userProfile);
            return;
        }
        manageBlocks(userProfile);
        throw new BadPartialCredentialsException(userProfile.getLogin());
    }


    @Transactional(noRollbackFor = {BadCredentialsException.class, UserDoesNotExistsException.class})
    public void loginUser(String login, String password) throws JsonProcessingException {
        UserProfile userProfile = loginUserCommon(login);
        if (passwordEncoder.matches(password, userProfile.getPassword())) {
            fillUserInSession(userProfile);
            return;
        }
        manageBlocks(userProfile);
        throw new BadCredentialsException();
    }

    private void fillUserInSession(UserProfile userProfile) {
        UserInSession userInSession = new UserInSession();
        userInSession.setLogin(userProfile.getLogin());
        userInSession.setId(userProfile.getId());
        userInSession.setLastLoginDate(userProfile.getLastLoginDate());
        sessionHelper.setUserInSession(userInSession);
        userProfile.setLastLoginDate(LocalDateTime.now());
        userProfile.setNumberOfFailedLoginsSinceLast(0);
        userProfileService.save(userProfile);
    }

    public UserProfile loginUserCommon(String login) throws JsonProcessingException {
        UserProfile userProfile = userProfileService.findByLogin(login);
        if (userProfile == null) {
            manageNotExistingUser(login);
            return null;
        }
        if (!userProfile.getIsActive()) {
            throw new UserIsBlockedException();
        }
        if (userProfile.getBlockadeDeadline() != null && userProfile.getBlockadeDeadline().isAfter(LocalDateTime.now())) {
            throw new DelayNeededException(userProfile.getBlockadeDeadline().toString());
        }
        return userProfile;
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

    @Transactional
    public void manageNotExistingUser(String login) throws JsonProcessingException {
        NotExistingUserProfile userProfile = notExistingUserRepository.findOne(qnesp.login.eq(login));
        if (userProfile == null) {
            userProfile = new NotExistingUserProfile();
            userProfile.setLogin(login);
            userProfile.setIsActive(true);
            userProfile.setLastFailedLoginDate(LocalDateTime.now());
            userProfile.setNumberOfFailedLoginsSinceLast(0);
            userProfile.setDelayInSeconds(ThreadLocalRandom.current().nextInt(1, 5));
            userProfile.setNumberOfAttempsBeforeBlockade(ThreadLocalRandom.current().nextInt(1, 5));
            userProfile.setPasswordLength(ThreadLocalRandom.current().nextInt(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
            userProfile.setPasswordFragments(generatePasswordFragments(userProfile));
            notExistingUserRepository.saveAndFlush(userProfile);
            userProfile.setCurrentPasswordFragmentId(userProfile.getPasswordFragments().get(0).getId());
            notExistingUserRepository.saveAndFlush(userProfile);
            return;
        }

        if (!userProfile.getIsActive()) {
            throw new UserIsBlockedException();
        }
        if (userProfile.getBlockadeDeadline() != null && userProfile.getBlockadeDeadline().isAfter(LocalDateTime.now())) {
            throw new DelayNeededException(userProfile.getBlockadeDeadline().toString());
        }

        userProfile.setLastFailedLoginDate(LocalDateTime.now());
        Integer previousFailedLogins = userProfile.getNumberOfFailedLoginsSinceLast();
        userProfile.setNumberOfFailedLoginsSinceLast(previousFailedLogins + 1);
        userProfile.setBlockadeDeadline(LocalDateTime.now().plusSeconds(userProfile.getNumberOfFailedLoginsSinceLast() * userProfile.getDelayInSeconds()));
        if (userProfile.getNumberOfFailedLoginsSinceLast() > userProfile.getNumberOfAttempsBeforeBlockade()) {
            userProfile.setIsActive(false);
        }
        notExistingUserRepository.saveAndFlush(userProfile);
    }


    private List<PasswordFragmentNotExistingUser> generatePasswordFragments(NotExistingUserProfile userProfile) throws JsonProcessingException {
        List<List<Integer>> passwordMasks = generatePasswordFragmentsMasks(userProfile.getPasswordLength());
        List<PasswordFragmentNotExistingUser> passwordFragments = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PASSWORD_FRAGMENTS; i++) {
            PasswordFragmentNotExistingUser fragment = new PasswordFragmentNotExistingUser();
            fragment.setJsonMask(objectMapper.writeValueAsString(passwordMasks.get(i)));
            fragment.setUser(userProfile);
            passwordFragments.add(fragment);
        }
        return passwordFragments;
    }

    private List<List<Integer>> generatePasswordFragmentsMasks(Integer length) {
        List<List<Integer>> fragmentsMasks = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            positions.add(i);
        }
        Integer maxFragmentLenght = length > 11 ? length / 2 : MIN_LENGTH_OF_PASSWORD_FRAGMENT;
        for (int i = 0; i < NUMBER_OF_PASSWORD_FRAGMENTS; i++) {
            boolean added = false;
            while (!added) {
                Collections.shuffle(positions);

                Integer currentFragmentLength = maxFragmentLenght == MIN_LENGTH_OF_PASSWORD_FRAGMENT ? MIN_LENGTH_OF_PASSWORD_FRAGMENT : ThreadLocalRandom.current().nextInt(MIN_LENGTH_OF_PASSWORD_FRAGMENT, maxFragmentLenght);
                List<Integer> currentFragmentPositions = positions.subList(0, currentFragmentLength);
                Collections.sort(currentFragmentPositions);
                if (!fragmentsMasks.contains(currentFragmentPositions)) {
                    fragmentsMasks.add(new ArrayList<Integer>(currentFragmentPositions));
                    added = true;
                }
            }
        }
        return fragmentsMasks;
    }


}
