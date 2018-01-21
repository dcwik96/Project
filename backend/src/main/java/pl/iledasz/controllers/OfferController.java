package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.DTO.AppUserDTO;
import pl.iledasz.DTO.OfferDTO;
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
    public List<OfferDTO> getOffersForAdvert(@PathVariable("id") long id, Principal principal, HttpServletResponse httpServletResponse)
    {
        List<OfferDTO> offerDTOList = offerService.getOffersForAdvert(id, principal);
        if(offerDTOList == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        return offerDTOList;
    }

    @RequestMapping(value = "api/advert/{id}/UserOffer")
    public OfferDTO getUserOfferForAdvert(@PathVariable("id") Long id, Principal principal, HttpServletResponse httpServletResponse)
    {
            return offerService.getUserOfferForAdvert(id, principal);
    }

    @PostMapping(value = "api/advert/{id}/newOffer")
    public ResponseEntity<String> putNewOffer(@PathVariable("id") Long id, @ModelAttribute OfferDTO offerDTO, Principal principal)
    {

        if( offerDTO.getOffer() != null &&
                offerDTO.getOffer().compareTo(BigDecimal.valueOf(0.00)) >= 0 &&
                        offerService.saveNewOfferOrUpdate(principal, offerDTO, id))
            return new ResponseEntity<>("Accepted", HttpStatus.OK);
        return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "api/advert/select/{offerID}")
    public AppUserDTO chooseOffer( @PathVariable("offerID") Long offerID, Principal principal, HttpServletResponse httpServletResponse){
        AppUserDTO selectedUser = offerService.chooseOneOfferAndCloseAdvertisement(offerID, principal);
        if (selectedUser == null)
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return selectedUser;
    }


}
