package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.DTO.AdvertisementDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.repository.AdvertisementRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public List<Advertisement> orderedList() {
        return advertisementRepository.findAll();
    }

    public List<AdvertisementDTO> randomList() {
        ModelMapper modelMapper = new ModelMapper();

        List<Advertisement> adverts = advertisementRepository.findAll();
        Collections.shuffle(adverts);
        List<AdvertisementDTO> advertisementDTOS = new ArrayList<>();

        for (Advertisement advertisement : adverts) {
            AdvertisementDTO advertisementDTO = modelMapper.map(advertisement, AdvertisementDTO.class);
            advertisementDTOS.add(advertisementDTO);
        }

        return advertisementDTOS;
    }

    public AdvertisementDTO findOneById(Long id) {

        ModelMapper modelMapper = new ModelMapper();
        AdvertisementDTO advertisementDTO = modelMapper.map(
                advertisementRepository.findOneById(id), AdvertisementDTO.class);
        return advertisementDTO;
    }

    public List<AdvertisementDTO> getLatestAdverts() {
        ModelMapper modelMapper = new ModelMapper();

        List<Advertisement> adverts = advertisementRepository.findAll();
        adverts.sort(Comparator.comparing(Advertisement::getEndDate));
        List<AdvertisementDTO> advertisementDTOS = new ArrayList<>();

        for (Advertisement advertisement : adverts) {
            AdvertisementDTO advertisementDTO = modelMapper.map(advertisement, AdvertisementDTO.class);
            advertisementDTOS.add(advertisementDTO);
        }

        return advertisementDTOS;
    }


}
