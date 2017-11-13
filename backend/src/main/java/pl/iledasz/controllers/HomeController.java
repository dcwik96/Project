package pl.iledasz.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.Photo;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    @RequestMapping(value = "/api/hello")
    public String hello() {
        return "gello";
    }

    @RequestMapping(value = "api/advert")
    public List<Advertisement> getAdverts() {
        List<Advertisement> adverts = new ArrayList<>();

        for (int i = 0 ; i < 10 ; i++){
            Long id = Long.valueOf(i);
            String title = "Tytul" + i;
            String description = "Opis" + i;
            adverts.add(new Advertisement(title,description ));
        }

        return adverts;
    }
}