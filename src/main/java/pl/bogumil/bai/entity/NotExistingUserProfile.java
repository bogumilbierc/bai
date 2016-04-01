package pl.bogumil.bai.entity;

import lombok.Getter;
import lombok.Setter;
import pl.bogumil.bai.entity.common.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
}
