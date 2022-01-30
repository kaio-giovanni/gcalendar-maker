package com.gcalendar.maker.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "GOOGLE_ACCESS_TOKEN")
    private String googleAccessToken;

    public User () {
    }

    public User (String name, String email, String googleAccessToken) {
        this.name = name;
        this.email = email;
        this.googleAccessToken = googleAccessToken;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getGoogleAccessToken () {
        return googleAccessToken;
    }

    public void setGoogleAccessToken (String googleAccessToken) {
        this.googleAccessToken = googleAccessToken;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(googleAccessToken, user.googleAccessToken);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, name, email, googleAccessToken);
    }

    @Override
    public String toString () {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", googleAccessToken='" + googleAccessToken + '\'' +
                '}';
    }
}
