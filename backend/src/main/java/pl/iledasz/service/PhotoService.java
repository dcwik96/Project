package pl.iledasz.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.PhotoRepository;

import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public List<Photo> list() {
        return photoRepository.findAll();
    }

    public Photo findPhotoById(Long id) {
        return photoRepository.findOneById(id);
    }
}
