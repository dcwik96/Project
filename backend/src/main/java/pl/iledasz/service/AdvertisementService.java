package pl.iledasz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.repository.AdvertisementRepository;

import java.util.Collections;
import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    public List<Advertisement> orderedList() {
        return advertisementRepository.findAll();
    }

    public List<Advertisement> randomList() {
        List<Advertisement> list = advertisementRepository.findAll();
        Collections.shuffle(list);

        return list;
    }

    public Advertisement findOneById(Long id) {
        return advertisementRepository.findOneById(id);
    }

}
