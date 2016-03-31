package pl.bogumil.bai.entity.common;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bbierc on 2016-03-24.
 */
@MappedSuperclass
public abstract class Entity<ID extends Serializable> implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }
}
