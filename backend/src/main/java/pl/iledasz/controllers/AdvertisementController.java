package pl.iledasz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.DTO.AdvertisementDTO;
import pl.iledasz.DTO.LightAdvertisementDTO;
import pl.iledasz.DTO.NewAdvertDTO;
import pl.iledasz.service.AdvertisementService;
import pl.iledasz.validator.NewAdvertValidator;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;


    @Autowired
    NewAdvertValidator newAdvertValidator;

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "api/adverts")
    public List<AdvertisementDTO> getAdverts() {
        return advertisementService.getLatestAdverts();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "api/lightAdverts")
    public List<LightAdvertisementDTO> getLightAdverts() {

        return advertisementService.getLatestLightAdverts();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "api/userAdverts")
    public List<LightAdvertisementDTO> getUserLightAdverts(Principal principal, HttpServletResponse httpServletResponse) {

        if (principal == null)
        {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        return advertisementService.getUserLightAdverts(principal);
    }

    @RequestMapping(value = "api/oneadvert/{id}")
    public AdvertisementDTO getAdvertById(@PathVariable("id") Long id) {

        return advertisementService.findOneById(id);
    }

    @PostMapping(value = "/api/newadvert")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> addNewAdvert(@ModelAttribute("advertForm") NewAdvertDTO newAdvertForm, BindingResult bindingResult, Principal principal) throws IOException {

        if (principal.getName().isEmpty())
            return new ResponseEntity<>("Wystąpił błąd przy próbie pobrania danych użytkownika.", HttpStatus.METHOD_NOT_ALLOWED);

        newAdvertValidator.validate(newAdvertForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        advertisementService.createNewAdvertisement(newAdvertForm, principal);

        return new ResponseEntity<>("Nowe ogłoszenie zostało dodane", HttpStatus.OK);
    }

    @RequestMapping(value = "api/randomAdvert")
            public AdvertisementDTO randomAdvert()
    {
        return advertisementService.randomAdvert();
    }

    @RequestMapping(value = "api/advert/{id}/verify")
    public void checkAdvertOwnerIsLoggedUser(@PathVariable("id") Long id, Principal principal, HttpServletResponse httpServletResponse) {
        if(advertisementService.checkAdvertOwnerIsLoggedUser(principal, id))
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        else
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
