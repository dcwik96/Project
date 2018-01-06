package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.DTO.OfferDTO;
import pl.iledasz.entities.Offer;
import pl.iledasz.repository.OfferRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferService {

    @Autowired
    OfferRepository offerRepository;

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
}
