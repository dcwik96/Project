package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.DTO.OfferDTO;
import pl.iledasz.service.OfferService;

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

}
