package pl.iledasz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.repository.AdvertisementRepository;

import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @RequestMapping(value = "api/adverts")
    public List<Advertisement> getAdverts() {
        return advertisementRepository.findAll();
    }

}
