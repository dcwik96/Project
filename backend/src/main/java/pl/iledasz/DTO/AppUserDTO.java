package pl.iledasz.DTO;

public class AppUserDTO {

    private String name;

    private String surname;

    private String login;

    private String email;

    private String phone_number;

    public AppUserDTO(String name, String surname, String login, String email, String phone_number) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.phone_number = phone_number;
    }

    public AppUserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
