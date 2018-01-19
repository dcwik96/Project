package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import pl.iledasz.DTO.OfferDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.Offer;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.OfferRepository;

import javax.servlet.http.HttpServletResponse;
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

    private ModelMapper modelMapper = new ModelMapper();

    public List<OfferDTO> getOffersForAdvert(long id, Principal principal )
    {
        Advertisement advertisement = advertisementRepository.findAdvertisementsByAppUser_LoginAndId(id, principal.getName());

        if(advertisement != null)
            return mapOfferListToOfferDTOList(advertisement.getOffers());
        return null;
    }

    private List<OfferDTO> mapOfferListToOfferDTOList(List<Offer> offerList)
    {
        return modelMapper.map(offerList, new TypeToken<List<OfferDTO>>() {}.getType());
    }

    public OfferDTO getOfferDetails(Long id)
    {
         return modelMapper.map( offerRepository.findOneById(id),OfferDTO.class);
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
        Offer offer = offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(advertId, login);
        if( offer == null)
            return null;
        return modelMapper.map(offer,OfferDTO.class);
    }
}
