package com.technograd.technograd.dao.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = -1267527510838107826L;
    private int id;
    private String lastname;
    private String name;
    private String email;
    private Post post;
    private String password;
    private String salt;
    private String localeName;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Post getPost() {
        return post;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getLocaleName() {
        return localeName;
    }

    public User(int id, String lastname, String name, String email, Post post, String password, String salt, String localeName) {
        this.id = id;
        this.lastname = lastname;
        this.name = name;
        this.email = email;
        this.post = post;
        this.password = password;
        this.salt = salt;
        this.localeName = localeName;
    }
    public User() {}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", post=" + post +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", localeName='" + localeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && lastname.equals(user.lastname) && name.equals(user.name) && email.equals(user.email)
                && post == user.post && password.equals(user.password) && salt.equals(user.salt) && localeName.equals(user.localeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastname, name, email, post, password, salt, localeName);
    }


    public void setPost(String string) {
        if(string.equalsIgnoreCase(Post.MANAGER.toString())){
            this.post = Post.MANAGER;
        } else if(string.equalsIgnoreCase(Post.CUSTOMER.toString())){
            this.post = Post.CUSTOMER;
        }
    }
}
