package pl.bogumil.bai.entity;

import lombok.Getter;
import lombok.Setter;
import pl.bogumil.bai.entity.common.EntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by bbierc on 2016-03-31.
 */
@Table(name = "USER_PROFILE")
@Entity
@Getter
@Setter
public class UserProfile extends EntityBase {
    @Column(name = "LOGIN", unique = true)
    private String login;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "LAST_LOGIN_DATE", columnDefinition = "DATETIME")
    private LocalDateTime lastLoginDate;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> ownMessages;

    @ManyToMany
    @JoinTable(name = "ALLOWED_MESSAGE", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID", referencedColumnName = "ID"))
    private List<Message> allowedMessages;
}
