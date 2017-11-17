package pl.iledasz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.DTO.AdvertisementDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.service.AdvertisementService;

import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @RequestMapping(value = "api/adverts")
    public List<AdvertisementDTO> getAdverts() {
        return advertisementService.randomList();
    }


}
