package com.example.site.entity;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {
    public static final String TABLE_NAME="USERS";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="USER_NAME", length=50, nullable=false, unique=false)
    private String name;

    public User(){}

    public User(String name)
    {
        this.name = name;
    }

    public long getCustomerId() {
        return id;
    }
    public void setCustomerId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
