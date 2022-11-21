package com.example.site.entity;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {
    public static final String TABLE_NAME="USERS";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="USERNAME", length=50, nullable=false, unique=true)
    private String name;

    @Column(name="USER_PASSWORD", length = 300, nullable = false, unique=false)
    private String password;

    public User(){}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
