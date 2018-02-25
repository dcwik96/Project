package pl.iledasz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.Offer;
import pl.iledasz.entities.SelectOffer;
import pl.iledasz.repository.OfferRepository;
import pl.iledasz.repository.SelectOfferRepository;

import java.security.Principal;
import java.time.OffsetDateTime;

@Service
public class SelectOfferService {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    SelectOfferRepository selectOfferRepository;

    public boolean chooseOneOffer(Long offerID, Principal principal) {
        Offer offer = offerRepository.findOfferByIdAndAdvertisement_AppUser_Login(offerID, principal.getName());
        if(offer != null)
        {
            SelectOffer selectOffer = new SelectOffer();
            selectOffer.setAdvertisement(offer.getAdvertisement());
            selectOffer.setOffer(offer);
            selectOffer.setExpiredDate(OffsetDateTime.now().plusDays(1));
            selectOfferRepository.save(selectOffer);
            return true;
        }
        return false;
    }
}
