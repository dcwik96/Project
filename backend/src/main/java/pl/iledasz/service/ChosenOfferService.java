package pl.iledasz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.Offer;
import pl.iledasz.entities.ChosenOffer;
import pl.iledasz.repository.OfferRepository;
import pl.iledasz.repository.ChosenOfferRepository;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ChosenOfferService {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    ChosenOfferRepository chosenOfferRepository;

    public boolean chooseOneOffer(Long offerID, Principal principal) {
        Offer offer = offerRepository.findOfferByIdAndAdvertisement_AppUser_LoginAndAdvertisement_AvailableTrue(offerID, principal.getName());

        if(offer != null )
        {

            List<ChosenOffer> chosenOffers = offer.getAdvertisement().getChosenOffers();
            if (chosenOffers != null)
                for ( ChosenOffer so : chosenOffers){

                    if(so.getOffer().getId() == offerID) return false;
                    if (so.getApproved() == null && so.getExpiredDate().isBefore(OffsetDateTime.now())){
                        so.setApproved(false);
                        chosenOfferRepository.save(so);
                    }
                    if( so.getApproved() == null || so.getApproved().booleanValue() ) return false;
                }


            ChosenOffer chosenOffer = new ChosenOffer();
            chosenOffer.setAdvertisement(offer.getAdvertisement());
            chosenOffer.setOffer(offer);
            chosenOffer.setExpiredDate(OffsetDateTime.now().plusDays(1));
            chosenOfferRepository.save(chosenOffer);
            return true;
        }
        return false;
    }
}
