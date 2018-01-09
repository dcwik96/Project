package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.DTO.AppUserDTO;
import pl.iledasz.DTO.OfferDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Offer;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.OfferRepository;
import pl.iledasz.service.AdvertisementService;
import pl.iledasz.service.AppUserService;
import pl.iledasz.service.OfferService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class OfferController {

    @Autowired
    OfferService offerService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    AdvertisementRepository advertisementRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    AdvertisementService advertisementService;


    //We need to remember about secure this endpoint, only advert owner has to get response.
    @RequestMapping(value = "api/advert/{id}/offers")
    public List<OfferDTO> getOffersForAdvert(@PathVariable("id") Long id, Principal principal, HttpServletResponse httpServletResponse)
    {
        AppUser loggedUser = appUserService.findByLogin(principal.getName());
        Advertisement advertisement = advertisementRepository.findOneById(id);

        if(advertisement != null && advertisement.getAppUser().getId() == loggedUser.getId())
            return offerService.getOffersFor(id);
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return null;
    }

    @RequestMapping(value = "api/advert/{id}/UserOffer")
    public OfferDTO getUserOfferForAdvert(@PathVariable("id") Long id, Principal principal, HttpServletResponse httpServletResponse)
    {
        Advertisement advertisement = advertisementRepository.findOneById(id);
        if(advertisement != null)
            return offerService.getUserOffer(id, principal.getName());
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return null;
    }


    @RequestMapping(value = "api/offer/{id}")
    public OfferDTO getOfferDetails(@PathVariable("id") Long id, Principal principal, HttpServletResponse httpServletResponse)
    {
        Offer offer = offerRepository.findOneById(id);
        if ( offer != null)
        {
            AppUser owner = offer.getAdvertisement().getAppUser();
            AppUser loggedUser = appUserService.findByLogin(principal.getName());
            if(owner.getId() == loggedUser.getId())
                return offerService.getOfferDetails(id);
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return null;
    }

    @RequestMapping(value = "api/advert/{id}/newOffer")
    @PostMapping
    public ResponseEntity<String> putNewOffer(@PathVariable("id") Long id, @ModelAttribute("offerForm")OfferDTO offerDTO, Principal principal)
    {
        Advertisement advertisement = advertisementRepository.findOneById(id);

        if(advertisement == null ||
                advertisement.getAppUser().getLogin().equals(principal.getName()) ||
                offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(id, principal.getName()) != null ||
                offerDTO.getOffer() == null ||
                offerDTO.getOffer().compareTo(BigDecimal.valueOf(0.00)) <= 0 )
        {
            return new ResponseEntity<>("Something goes wrong!", HttpStatus.FORBIDDEN);
        }
        offerService.saveNewOffer(principal,offerDTO, id);
        return new ResponseEntity<>("Accepted", HttpStatus.OK);
    }


    @RequestMapping(value = "api/offer/{id}/editOffer")
    @PutMapping
    public void editOffer (@PathVariable("id") Long id,@ModelAttribute("offerForm")OfferDTO offerDTO, Principal principal, HttpServletResponse httpServletResponse)
    {
        Offer offer = offerRepository.getOne(id);
        if(offerDTO.getOffer() != null && offer.getAppUser().getLogin().equals(principal.getName()))
        {
            offer.setOffer(offerDTO.getOffer());
            offerRepository.save(offer);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }else
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }


    @RequestMapping(value = "api/advert/{advertID}/offer/{offerID}")
    public AppUserDTO chooseOffer(@PathVariable("advertID") Long advertID, @PathVariable("offerID") Long offerID, Principal principal){

        if (advertisementRepository.findOneById(advertID).getAppUser().getLogin().equals(principal.getName())){
            AppUserDTO appUserDTO = advertisementService.chooseOneOfferAndCloseAdvertisement(offerID);
            return appUserDTO;
        } else
            return null;
    }


}
