package database.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String login;

    public User() {}

    public User(String firstName, String lastName, String login) {
        id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
    }

    public User(int id, String firstName, String lastName, String login) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
       User user = (User) o;
        return getId() == user.getId() &&
                getLogin() == user.getLogin() &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getLogin());
    }
}
