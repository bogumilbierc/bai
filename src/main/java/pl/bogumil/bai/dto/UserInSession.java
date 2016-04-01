package pl.bogumil.bai.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by bbierc on 2016-03-31.
 */
@Data
public class UserInSession implements Serializable {

    private static final long serialVersionUID = -7997778607911141102L;
    private String login;
    private Integer id;
    private LocalDateTime lastLoginDate;
}
