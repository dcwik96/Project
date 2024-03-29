package pl.iledasz.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private String name;

    private String surname;

    private String login;

    private String email;

    private String phone_number;

}
