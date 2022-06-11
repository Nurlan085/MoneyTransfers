package dev.nurlan.test;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerEntity {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
