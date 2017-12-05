package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.entities.AppUser;
import pl.iledasz.service.AppUserService;
import pl.iledasz.validator.AppUserValidator;

@RestController
public class RegistrationController {

    @Autowired
    private AppUserValidator appUserValidator;
    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new AppUser());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") AppUser userForm, BindingResult bindingResult, Model model) {
        appUserValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return "registration";
        }

        appUserService.save(userForm);

        return "redirect:/hellosecure";
    }
}
