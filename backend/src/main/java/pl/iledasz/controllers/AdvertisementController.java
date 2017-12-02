package pl.iledasz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.DTO.AdvertisementDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.service.AdvertisementService;

import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @CrossOrigin(origins="http://localhost:8081")
    @RequestMapping(value = "api/adverts")
    public List<AdvertisementDTO> getAdverts() {
        return advertisementService.randomList();
    }

    @RequestMapping(value = "api/advert/{id}")
    public AdvertisementDTO getAdvertById(@PathVariable("id") Long id) {

        return advertisementService.findOneById(id);
    }

}
