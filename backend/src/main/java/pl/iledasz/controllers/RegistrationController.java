package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.entities.AppUser;
import pl.iledasz.service.AppUserService;
import pl.iledasz.validator.AppUserValidator;
import pl.iledasz.validator.EditAppUserValidator;

import java.security.Principal;

@RestController
public class RegistrationController {

    @Autowired
    private AppUserValidator appUserValidator;
    @Autowired
    private EditAppUserValidator editAppUserValidator;
    @Autowired
    private AppUserService appUserService;

    @PostMapping(value = "/registration")
    public ResponseEntity<String> registration(@ModelAttribute("userForm") AppUser userForm, BindingResult bindingResult) {
        appUserValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        appUserService.save(userForm);

        return new ResponseEntity<>("Nowy użytkownik został poprawnie dodany, możesz się teraz zalogować", HttpStatus.OK);
    }

    @PostMapping(value = "api/user/edit")
    public ResponseEntity<String> editUser(@ModelAttribute("userForm") AppUser userForm, BindingResult bindingResult, Principal principal) {
        editAppUserValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        appUserService.modify(userForm, principal);

        return new ResponseEntity<>("Edytowano", HttpStatus.OK);
    }
}
