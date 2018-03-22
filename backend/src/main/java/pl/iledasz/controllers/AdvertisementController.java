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

    @RequestMapping(value = "api/items")
    public List<AdvertisementDTO> getAdverts() {
        return advertisementService.getLatestAdverts();
    }

    @RequestMapping(value = "api/lightItems")
    public List<LightAdvertisementDTO> getLightAdverts() {

        return advertisementService.getLatestLightAdverts();
    }

    @RequestMapping(value = "api/userItems")
    public List<LightAdvertisementDTO> getUserLightAdverts(Principal principal, HttpServletResponse httpServletResponse) {

        if (principal == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        return advertisementService.getUserLightAdverts(principal);
    }

    @RequestMapping(value = "api/oneItem/{id}")
    public AdvertisementDTO getAdvertById(@PathVariable("id") Long id) {

        return advertisementService.findOneById(id);
    }

    @PostMapping(value = "/api/newItem")
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

    @RequestMapping(value = "api/randomItem")
    public AdvertisementDTO randomAdvert() {
        return advertisementService.randomAdvert();
    }

    @RequestMapping(value = "api/item/{id}/verify")
    public void checkAdvertOwnerIsLoggedUser(@PathVariable("id") Long id, Principal principal, HttpServletResponse httpServletResponse) {
        if (advertisementService.checkAdvertOwnerIsLoggedUser(principal, id))
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        else
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
