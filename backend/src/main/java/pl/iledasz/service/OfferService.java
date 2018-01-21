package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.DTO.AppUserDTO;
import pl.iledasz.DTO.OfferDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.Offer;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.OfferRepository;

import java.security.Principal;
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
        Advertisement advertisement = advertisementRepository.findAdvertisementsByAppUser_LoginAndId(principal.getName(), id);

        if(advertisement != null)
            return mapOfferListToOfferDTOList(advertisement.getOffers());
        return null;
    }

    private List<OfferDTO> mapOfferListToOfferDTOList(List<Offer> offerList)
    {
        return modelMapper.map(offerList, new TypeToken<List<OfferDTO>>() {}.getType());
    }

    public boolean saveNewOfferOrUpdate(Principal principal, OfferDTO offerDTO, Long advertId)
    {
        Advertisement advertisement = advertisementRepository.findAdvertisementByAppUser_LoginNotLikeAndId(principal.getName(),advertId);
        if(advertisement == null)
            return false;

        Offer newOffer = offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(advertId, principal.getName());
        if(newOffer == null)
        {
            newOffer = new Offer();
            newOffer.setAdvertisement(advertisement);
            newOffer.setAppUser(appUserRepository.findByLogin(principal.getName()));
        }
        newOffer.setOffer(offerDTO.getOffer());
        offerRepository.save(newOffer);
        return true;
    }

    public OfferDTO getUserOfferForAdvert(Long advertId, Principal principal)
    {
        Offer offer = offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(advertId, principal.getName());
        if( offer == null)
            return null;
        return modelMapper.map(offer,OfferDTO.class);
    }

    public AppUserDTO chooseOneOfferAndCloseAdvertisement(Long offerID, Principal principal) {
        Offer selectedOffer = offerRepository.findOfferByIdAndAdvertisement_AppUser_Login(offerID, principal.getName());
        if(selectedOffer != null)
        {
            Advertisement advertisement = selectedOffer.getAdvertisement();
            advertisement.setAvailable(false);
            advertisementRepository.save(advertisement);
            return modelMapper.map(selectedOffer.getAppUser(), AppUserDTO.class);
        }
        return null;
    }
}
