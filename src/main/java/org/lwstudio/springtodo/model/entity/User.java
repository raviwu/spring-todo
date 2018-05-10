package org.lwstudio.springtodo.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 6805190293149476515L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
