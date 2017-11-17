package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.service.AdvertPhotoService;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private AdvertPhotoService advertPhotoService;

    @RequestMapping(value = "/api/hello")
    public String hello() {
        return "gello";
    }

    @RequestMapping(value = "api/advertphoto")
    public List<AdvertPhoto> getAll() {
        return advertPhotoService.list();
    }

}