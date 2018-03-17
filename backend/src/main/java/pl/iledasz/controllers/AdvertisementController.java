package pl.iledasz.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.DTO.Adverts.AdvertisementDTO;
import pl.iledasz.DTO.Adverts.LightAdvertisementDTO;
import pl.iledasz.DTO.Adverts.NewAdvertDTO;
import pl.iledasz.DTO.Adverts.NewMinimalAdvertDTO;
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

    @RequestMapping(value = "api/adverts")
    @ResponseBody
    public List<AdvertisementDTO> getAdverts() {
        return advertisementService.getLatestAdverts();
    }

    @RequestMapping(value = "api/lightAdverts")
    @ResponseBody
    public List<LightAdvertisementDTO> getLightAdverts() {

        return advertisementService.getLatestLightAdverts();
    }

    @RequestMapping(value = "api/userAdverts")
    @ResponseBody
    public List<LightAdvertisementDTO> getUserLightAdverts(Principal principal, HttpServletResponse httpServletResponse) {

        if (principal == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        return advertisementService.getUserLightAdverts(principal);
    }

    @RequestMapping(value = "api/oneadvert/{id}")
    @ResponseBody
    public AdvertisementDTO getAdvertById(@PathVariable("id") Long id) {

        return advertisementService.findOneById(id);
    }

    @PostMapping(value = "/api/newadvert")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> addNewAdvert(@ModelAttribute("advertForm") NewAdvertDTO newAdvertForm, BindingResult bindingResult, Principal principal) throws IOException {

        newAdvertValidator.validate(newAdvertForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        advertisementService.createNewAdvertisement(newAdvertForm, principal);

        return new ResponseEntity<>("Nowe ogłoszenie zostało dodane", HttpStatus.OK);
    }

    @PostMapping(value = "api/newAdvert")
    @JsonView(String.class)
    public String addNewMinimalAdvert(@ModelAttribute NewMinimalAdvertDTO newMinimalAdvertDTO, Principal principal, HttpServletResponse httpServletResponse, Model model) throws IOException {

       if((newMinimalAdvertDTO.getTitle() == null ||
               newMinimalAdvertDTO.getDescription() == null ||
               newMinimalAdvertDTO.getDuration() == null ||
               newMinimalAdvertDTO.getDuration() < 0)){
           httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
           return "0";
       }

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        Long id = advertisementService.createNewMinimalAdvertisement(newMinimalAdvertDTO, principal);

        return String.valueOf(id);


    }

    @RequestMapping(value = "api/randomAdvert")
    public AdvertisementDTO randomAdvert() {
        return advertisementService.randomAdvert();
    }

    @RequestMapping(value = "api/advert/{id}/verify")
    public void checkAdvertOwnerIsLoggedUser(@PathVariable("id") Long id, Principal principal, HttpServletResponse httpServletResponse) {
        if (advertisementService.checkAdvertOwnerIsLoggedUser(principal, id))
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        else
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
