package pl.iledasz.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.iledasz.DTO.AdvertisementDTO;
import pl.iledasz.DTO.NewAdvertDTO;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.PhotoRepository;
import pl.iledasz.service.AdvertisementService;
import pl.iledasz.validator.NewAdvertValidator;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AdvertPhotoRepository advertPhotoRepository;

    @Autowired
    NewAdvertValidator newAdvertValidator;

    @CrossOrigin(origins="http://localhost:8081")
    @RequestMapping(value = "api/adverts")
    public List<AdvertisementDTO> getAdverts() {
        return advertisementService.getLatestAdverts();
    }

    @RequestMapping(value = "api/advert/{id}")
    public AdvertisementDTO getAdvertById(@PathVariable("id") Long id) {

        return advertisementService.findOneById(id);
    }

    @PostMapping(value = "/api/newadvert")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> addNewAdvert(@ModelAttribute("advertForm") NewAdvertDTO newAdvertForm, BindingResult bindingResult, Principal principal) throws IOException {
        if(principal.getName().isEmpty())
            return new ResponseEntity<>("Wystąpił błąd przy próbie pobrania danych użytkownika.",HttpStatus.METHOD_NOT_ALLOWED);

        newAdvertValidator.validate(newAdvertForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        Advertisement newAdvertisement = new Advertisement();

        newAdvertisement.setDescription(newAdvertForm.getDescription());
        newAdvertisement.setTitle(newAdvertForm.getTitle());
        newAdvertisement.setStartDate(OffsetDateTime.now());
        newAdvertisement.setEndDate(newAdvertisement.getStartDate().plusDays(newAdvertForm.getDuration()));
        newAdvertisement.setDuration(newAdvertForm.getDuration());

        AppUser appUser = appUserRepository.findByLogin(principal.getName());

        newAdvertisement.setAppUser(appUser);

        advertisementRepository.save(newAdvertisement);

        TreeMap <String, MultipartFile> imagesWithDescriptions = newAdvertForm.getPhotosWithDescriptions();

        for (Map.Entry<String, MultipartFile> imageWithDescription : imagesWithDescriptions.entrySet())
        {
            AdvertPhoto advertPhoto = new AdvertPhoto(newAdvertisement,imageWithDescription.getKey());
            advertPhotoRepository.save(advertPhoto);
            Photo photo = new Photo(imageWithDescription.getValue().getBytes(), advertPhoto);
            photoRepository.save(photo);
        }

        return new ResponseEntity<>("Nowe ogłoszenie zostało dodane", HttpStatus.OK);
    }

}
