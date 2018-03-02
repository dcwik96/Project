package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.iledasz.DTO.ChosenOfferDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.Offer;
import pl.iledasz.entities.ChosenOffer;
import pl.iledasz.repository.AdvertisementRepository;
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

    @Autowired
    AdvertisementRepository advertisementRepository;

    ModelMapper modelMapper = new ModelMapper();

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

    @Transactional
    public boolean confirmOrReject (Long requestId, Principal principal, boolean reaction){
        ChosenOffer chosenOffer = chosenOfferRepository.findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter(principal.getName(), requestId, OffsetDateTime.now());
        if(chosenOffer == null) return false;
        chosenOffer.setApproved(reaction);
        if(reaction == true){
            chosenOffer.setExpiredDate(OffsetDateTime.now().plusDays(7));
            Advertisement advertisement = chosenOffer.getAdvertisement();
            advertisement.setAvailable(false);
            advertisementRepository.save(advertisement);

        }

        chosenOfferRepository.save(chosenOffer);

        return true;
    }

    public List<ChosenOfferDTO> getAwaitingRequests (Principal principal){

        List<ChosenOffer> list = chosenOfferRepository.findAllByOffer_AppUser_LoginAndApprovedIsNullAndExpiredDateAfter(principal.getName(), OffsetDateTime.now());

        return modelMapper.map(list,new TypeToken<List<ChosenOfferDTO>>() {}.getType() );

    }
}


