package pl.iledasz.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.PhotoReposiory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    public byte[] findPhotoAndConvertToByte(String path) throws IOException{
        return Files.readAllBytes(new File(path).toPath());
    }

}
