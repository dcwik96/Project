package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.PhotoRepository;

import java.util.List;

@RestController
public class HomeController {

    @RequestMapping(value = "/api/hello")
    public String hello() {
        return "gello";
    }

}