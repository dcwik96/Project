package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.DTO.AdvertPhotoDTO;
import pl.iledasz.DTO.AppUserDTO;
import pl.iledasz.entities.AppUser;
import pl.iledasz.service.AdvertPhotoService;
import pl.iledasz.service.AppUserService;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private AdvertPhotoService advertPhotoService;

    @Autowired
    private AppUserService appUserService;


    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(6);

    @RequestMapping(value = "/hello")
    public String hello() {
        return "gello";
    }

    @RequestMapping(value = "/hellosecure")
    public String hellos() {
        return "gello secure";
    }

    @RequestMapping(value = "/helloadmin")
    public String helloa() {
        return "gello admin";
    }

    @RequestMapping(value = "api/advertphoto")
    public List<AdvertPhotoDTO> getAll() {
        return advertPhotoService.list();
    }

    @RequestMapping(value = "api/user/{id}")
    public AppUserDTO getUserById(@PathVariable("id") Long id) {

        return appUserService.getUser(id);
    }

    @RequestMapping(value = "/users")
    public List<AppUser> getUsers() {
        return appUserService.getUsers();
    }

    //Temporary request for getting password hash
    @RequestMapping(value = "api/passwd/{passwd}")
    public String passhash(@PathVariable("passwd") String pass) {
        return bcrypt.encode(pass);
    }


}