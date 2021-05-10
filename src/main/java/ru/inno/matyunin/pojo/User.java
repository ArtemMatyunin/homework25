package ru.inno.matyunin.pojo;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String pass;
    private String prompt;


    public User() {
    }

    public User(int id, String name, String pass, String prompt) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.prompt = prompt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(pass, user.pass) && Objects.equals(prompt, user.prompt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pass, prompt);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
