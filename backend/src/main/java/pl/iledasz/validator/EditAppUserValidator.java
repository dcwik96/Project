package pl.iledasz.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.iledasz.entities.AppUser;

@Component
public class EditAppUserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return AppUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AppUser user = (AppUser) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty", "Imie nie moze byc pusty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "NotEmpty", "Nazwisko nie moze byc pusty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone_number", "NotEmpty", "Telefon nie moze byc pusty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "Haslo nie moze byc puste!");


        if (!errors.hasErrors()) {
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "Size.userForm.password", "Za krótkie haslo!");
            }
            if (!user.getEmail().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                errors.rejectValue("email", "Email.form", "Zły mail!");

            }
        }
    }
}
