package pl.iledasz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.Offer;
import pl.iledasz.entities.SelectOffer;
import pl.iledasz.repository.OfferRepository;
import pl.iledasz.repository.SelectOfferRepository;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class SelectOfferService {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    SelectOfferRepository selectOfferRepository;


    //Dopisz sprawdzanie daty, jezeli jest po, to przekręć na false i elo
    //Dopisz sprawdzenie czy nie została  wybrana ponownie ta sama oferta? Albo automatyczne usuwanie
    public boolean chooseOneOffer(Long offerID, Principal principal) {
        Offer offer = offerRepository.findOfferByIdAndAdvertisement_AppUser_LoginAndAdvertisement_AvailableTrue(offerID, principal.getName());

        if(offer != null )
        {
            List<SelectOffer> selectOffers = offer.getAdvertisement().getSelectOffers();
            if (selectOffers != null)
                for ( SelectOffer so : selectOffers){
                    if (so.getApproved() == null && so.getExpiredDate().isBefore(OffsetDateTime.now())){
                        so.setApproved(false);
                        selectOfferRepository.save(so);
                    }
                    if( so.getApproved() == null || so.getApproved().booleanValue() ) return false;
                }


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
