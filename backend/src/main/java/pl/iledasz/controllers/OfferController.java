package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.iledasz.DTO.OfferDTO;
import pl.iledasz.entities.Offer;
import pl.iledasz.service.OfferService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class OfferController {

    @Autowired
    OfferService offerService;

    //We need to remember about secure this endpoint, only advert owner has to get response.
    @RequestMapping(value = "api/advert/{id}/offers")
    public List<OfferDTO> getOffersForAdvert(@PathVariable("id") Long id)
    {
        return offerService.getOffersFor(id);
    }

    @RequestMapping(value = "api/offer/{id}")
    public OfferDTO getOfferDetails(@PathVariable("id") Long id)
    {
        return offerService.getOfferDetails(id);
    }

    @RequestMapping(value = "api/advert/{id}/newOffer")
    @PostMapping
    public ResponseEntity<String> putNewOffer(@PathVariable("id") Long id, @ModelAttribute("offerForm")OfferDTO offerDTO, Principal principal)
    {
        System.out.println(offerDTO.getOffer());
        if(principal == null || offerDTO.getOffer() == null || offerDTO.getOffer().compareTo(BigDecimal.valueOf(0.00)) <= 0 )
        {
            return new ResponseEntity<>("Something goes wrong!", HttpStatus.NOT_ACCEPTABLE);
        }
        offerService.saveNewOffer(principal,offerDTO, id);
        return new ResponseEntity<>("Accepted", HttpStatus.OK);
    }

}
