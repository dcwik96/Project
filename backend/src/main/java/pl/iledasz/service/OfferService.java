package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.DTO.OfferDTO;
import pl.iledasz.entities.Offer;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.OfferRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferService {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AdvertisementRepository advertisementRepository;

    public List<OfferDTO> getOffersFor(Long id)
    {
        ModelMapper mapper = new ModelMapper();
        List<Offer> offersList = offerRepository.findAllByAdvertisement_Id(id);
        List<OfferDTO> offersDTOList = new ArrayList<>();
        for(Offer x : offersList)
            offersDTOList.add(mapper.map(x,OfferDTO.class));
        return offersDTOList;
    }

    public OfferDTO getOfferDetails(Long id)
    {
        ModelMapper mapper = new ModelMapper();
         return mapper.map( offerRepository.findOneById(id),OfferDTO.class);
    }

    public void saveNewOffer(Principal principal, OfferDTO offerDTO, Long AdvertId)
    {
        Offer newOffer = new Offer();
        newOffer.setAdvertisement(advertisementRepository.findOneById(AdvertId));
        newOffer.setAppUser(appUserRepository.findByLogin(principal.getName()));
        newOffer.setOffer(offerDTO.getOffer());
        offerRepository.save(newOffer);
    }

    public OfferDTO getUserOffer( Long advertId, String login)
    {
        ModelMapper model = new ModelMapper();
        Offer offer = offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(advertId, login);
        if( offer == null)
            return null;
        return model.map(offer,OfferDTO.class);
    }
}
