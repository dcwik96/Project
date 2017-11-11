package pl.iledasz.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.app.entities.Photo;
import pl.iledasz.app.repository.PhotoReposiory;

import java.util.List;

@Service
public class PhotoServiceImpl {

    @Autowired
    private PhotoReposiory photoReposiory;

    public void addPhoto(Photo photo) {
        photoReposiory.save(photo);
    }

    public List<Photo> findAllPhotos() {
        return photoReposiory.findAll();
    }

    public Photo findById(Long id) {
        return photoReposiory.findById(id);
    }

    public Photo findByFilename(String filename) {
        return photoReposiory.findByFilename(filename);
    }

}
