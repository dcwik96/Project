package pl.iledasz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.repository.AdvertPhotoRepository;

import java.util.List;

@Service
public class AdvertPhotoService {

    @Autowired
    private AdvertPhotoRepository advertPhotoRepository;

    public List<AdvertPhoto> list() {
        return advertPhotoRepository.findAll();
    }

}
