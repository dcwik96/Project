package pl.iledasz.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enable")
    private boolean enable;

    public AppUser(Role role, String name, String surname, String login, String email, String phone_number, String password, boolean enable) {
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.enable = enable;
    }
}
