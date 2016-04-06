package pl.bogumil.bai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bogumil.bai.dto.UserInSession;
import pl.bogumil.bai.entity.PasswordFragment;
import pl.bogumil.bai.entity.QUserProfile;
import pl.bogumil.bai.entity.UserProfile;
import pl.bogumil.bai.exception.BadCredentialsException;
import pl.bogumil.bai.exception.PasswordDoesNotMeetSpecifiedCriteriaException;
import pl.bogumil.bai.exception.Unauthorized403Exception;
import pl.bogumil.bai.exception.UserWithThatLoginAlreadyExistsException;
import pl.bogumil.bai.helper.SessionHelper;
import pl.bogumil.bai.repositories.UserProfileRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by bbierc on 2016-03-31.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
@Slf4j
public class UserProfileService {

    private final static QUserProfile qup = QUserProfile.userProfile;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 16;
    public static final int NUMBER_OF_PASSWORD_FRAGMENTS = 10;
    public static final int MIN_LENGTH_OF_PASSWORD_FRAGMENT = 5;

    private final UserProfileRepository userProfileRepository;
    private final SessionHelper sessionHelper;
    private final PasswordEncoder passwordEncoder;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public UserProfile save(UserProfile userProfile) {
        userProfileRepository.saveAndFlush(userProfile);
//        log.info("zapisano uzytkownika: " + userProfile.getLogin());
        return userProfile;
    }

    @Transactional
    public void editUserAccount(Integer delay, Integer attempts) {
        UserProfile userProfile = getCurrentUser();
        userProfile.setDelayInSeconds(delay);
        userProfile.setNumberOfAttempsBeforeBlockade(attempts);
        userProfileRepository.saveAndFlush(userProfile);
        log.info("edytowano uzytkownika: " + userProfile.getLogin());
    }

    @Transactional
    public void changePassword(String password, String oldPassword) throws JsonProcessingException {

        UserProfile userProfile = getCurrentUser();
        if (passwordEncoder.matches(oldPassword, userProfile.getPassword())) {
            validatePassword(password, true);
            userProfile.setPassword(passwordEncoder.encode(password));
            userProfile.getPasswordFragments().clear();
            userProfile.getPasswordFragments().addAll(generatePasswordFragments(password, userProfile));
            userProfileRepository.saveAndFlush(userProfile);
            userProfile.setCurrentPasswordFragmentId(userProfile.getPasswordFragments().get(0).getId());
            userProfileRepository.saveAndFlush(userProfile);
            log.info("zmiana hasla dla uzytkownika " + userProfile.getLogin());
            return;
        }
        throw new BadCredentialsException("edycja");
    }

    public UserProfile getCurrentUser() {
        UserInSession userInSession = sessionHelper.getUserFromSession();
        if (userInSession == null) {
            throw new Unauthorized403Exception();
        }
        return findByLogin(userInSession.getLogin());
    }

    public UserProfile findByLogin(String login) {
        return userProfileRepository.findOne(qup.login.eq(login));
    }

    @Transactional
    public void registerUser(String login, String password, Integer delay, Integer attempts) throws JsonProcessingException {
        UserProfile userProfile = findByLogin(login);
        if (userProfile != null) {
            throw new UserWithThatLoginAlreadyExistsException();
        }
        validatePassword(password, false);
        userProfile = new UserProfile();
        userProfile.setLogin(login);
        userProfile.setDelayInSeconds(delay);
        userProfile.setNumberOfAttempsBeforeBlockade(attempts);
        userProfile.setNumberOfFailedLoginsSinceLast(0);
        userProfile.setPassword(passwordEncoder.encode(password));
        userProfile.setIsActive(true);
        userProfile.setPasswordFragments(generatePasswordFragments(password, userProfile));
        userProfile.setPasswordLength(password.length());

        userProfileRepository.saveAndFlush(userProfile);
        userProfile.setCurrentPasswordFragmentId(userProfile.getPasswordFragments().get(0).getId());
        userProfileRepository.saveAndFlush(userProfile);
        log.info("zarejestrowano uzytkownika: " + userProfile.getLogin());
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

    private void validatePassword(String password, boolean accountEdit) {
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            if (accountEdit) {
                throw new PasswordDoesNotMeetSpecifiedCriteriaException("edycja");
            }
            throw new PasswordDoesNotMeetSpecifiedCriteriaException();
        }
    }

    private List<PasswordFragment> generatePasswordFragments(String rawPassword, UserProfile userProfile) throws JsonProcessingException {
        List<List<Integer>> passwordMasks = generatePasswordFragmentsMasks(rawPassword);
        List<PasswordFragment> passwordFragments = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PASSWORD_FRAGMENTS; i++) {
            PasswordFragment fragment = new PasswordFragment();
            fragment.setJsonMask(objectMapper.writeValueAsString(passwordMasks.get(i)));
            fragment.setPasswordHash(generateHashForPasswordFragment(rawPassword, passwordMasks.get(i)));
            fragment.setUser(userProfile);
            passwordFragments.add(fragment);
        }
        return passwordFragments;
    }

    private List<List<Integer>> generatePasswordFragmentsMasks(String rawPassword) {
        List<List<Integer>> fragmentsMasks = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < rawPassword.length(); i++) {
            positions.add(i);
        }
        Integer maxFragmentLenght = rawPassword.length() > 11 ? rawPassword.length() / 2 : MIN_LENGTH_OF_PASSWORD_FRAGMENT;
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

    private String generateHashForPasswordFragment(String rawPassword, List<Integer> mask) {
        String maskedPassword = "";
        for (Integer position : mask) {
            maskedPassword += rawPassword.charAt(position);
        }
        return passwordEncoder.encode(maskedPassword);
    }

}
