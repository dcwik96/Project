package pl.iledasz.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue

    private Long id;

    private String username;
    private String password;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}