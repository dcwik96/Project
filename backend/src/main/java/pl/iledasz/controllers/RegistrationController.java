package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.entities.AppUser;
import pl.iledasz.service.AppUserService;
import pl.iledasz.validator.AppUserValidator;

@RestController
public class RegistrationController {

    @Autowired
    private AppUserValidator appUserValidator;
    @Autowired
    private AppUserService appUserService;

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(Model model) {
//        model.addAttribute("userForm", new AppUser());
//
//        return "registration";
//    }

    @PostMapping(value = "/registration")
    public ResponseEntity<String> registration(@ModelAttribute("userForm") AppUser userForm, BindingResult bindingResult) {
        appUserValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        appUserService.save(userForm);

        return new ResponseEntity<>("Nowy użytkownik został poprawnie dodany, możesz się teraz zalogować", HttpStatus.OK);
    }
}
