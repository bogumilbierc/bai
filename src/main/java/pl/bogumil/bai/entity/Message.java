package pl.bogumil.bai.entity;

import lombok.Getter;
import lombok.Setter;
import pl.bogumil.bai.entity.common.EntityBase;

import javax.persistence.*;

/**
 * Created by bbierc on 2016-03-31.
 */
@Entity
@Table(name = "MESSAGE")
@Getter
@Setter
public class Message extends EntityBase{

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private UserProfile owner;

}
