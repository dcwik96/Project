package pl.iledasz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.DTO.AdvertisementDTO;
import pl.iledasz.DTO.NewAdvertDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.service.AdvertisementService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @CrossOrigin(origins="http://localhost:8081")
    @RequestMapping(value = "api/adverts")
    public List<AdvertisementDTO> getAdverts() {
        return advertisementService.randomList();
    }

    @RequestMapping(value = "api/advert/{id}")
    public AdvertisementDTO getAdvertById(@PathVariable("id") Long id) {

        return advertisementService.findOneById(id);
    }

    @PostMapping(value = "/api/newadvert")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> registration(@ModelAttribute("advertForm") NewAdvertDTO newAdvertForm, BindingResult bindingResult, Principal principal) {
        if(principal.getName().isEmpty())
            return new ResponseEntity<>("Wystąpił błąd przy próbie pobrania danych użytkownika.",HttpStatus.METHOD_NOT_ALLOWED);
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
//        }
        Advertisement newAdvertisement = new Advertisement();
        newAdvertisement.setDescription(newAdvertForm.getDescription());
        newAdvertisement.setTitle(newAdvertForm.getTitle());
        AppUser appUser = appUserRepository.findByLogin(principal.getName());
        newAdvertisement.setAppUser(appUser);
        advertisementRepository.save(newAdvertisement);


        return new ResponseEntity<>("Nowe ogłoszenie zostało dodane", HttpStatus.OK);
    }

}
