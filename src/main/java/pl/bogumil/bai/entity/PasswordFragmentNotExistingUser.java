package pl.bogumil.bai.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import pl.bogumil.bai.entity.common.EntityBase;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by bbierc on 2016-04-04.
 */
@Table(name = "PASSWORD_FRAGMENT_NOT_EXISTING_USER")
@Entity
@Getter
@Setter
public class PasswordFragmentNotExistingUser extends EntityBase {


    @Column(name = "JSON_MASK")
    private String jsonMask;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private NotExistingUserProfile user;


    public List<Integer> getPasswordMask() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonMask, new TypeReference<List<Integer>>() {
        });
    }

}
