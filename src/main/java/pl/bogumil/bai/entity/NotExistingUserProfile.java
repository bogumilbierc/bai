package pl.bogumil.bai.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import pl.bogumil.bai.entity.common.EntityBase;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by bbierc on 2016-03-31.
 */
@Table(name = "NOT_EXISTING_USER_PROFILE")
@Entity
@Getter
@Setter
public class NotExistingUserProfile extends EntityBase {
    @Column(name = "LOGIN", unique = true)
    private String login;
    @Column(name = "LAST_FAILED_LOGIN_DATE", columnDefinition = "DATETIME")
    private LocalDateTime lastFailedLoginDate;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @Column(name = "NO_OF_FAILED_LOGINS_SINCE_LAST")
    private Integer numberOfFailedLoginsSinceLast;
    @Column(name = "NO_OF_ATTEMPTS_BEFORE_BLOCKADE")
    private Integer numberOfAttempsBeforeBlockade;
    @Column(name = "DELAY_IN_SECONDS")
    private Integer delayInSeconds;
    @Column(name = "BLOCKADE_DEADLINE", columnDefinition = "DATETIME")
    private LocalDateTime blockadeDeadline;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PasswordFragmentNotExistingUser> passwordFragments;

    @Column(name = "CURRENT_PASSWORD_FRAGMENT_ID")
    private Integer currentPasswordFragmentId;

    @Column(name = "PASSWORD_LENGTH")
    private Integer passwordLength;


    public List<Integer> getCurrentPasswordMask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PasswordFragmentNotExistingUser passwordFragment = passwordFragments
                .stream()
                .filter(passwordFragment1 ->
                        passwordFragment1.getId().equals(currentPasswordFragmentId))
                .findFirst().get();
        return passwordFragment.getPasswordMask();
    }

    public PasswordFragmentNotExistingUser getCurrentPasswordFragment() {
        return passwordFragments
                .stream()
                .filter(passwordFragment1 ->
                        passwordFragment1.getId().equals(currentPasswordFragmentId))
                .findFirst().get();
    }

}
