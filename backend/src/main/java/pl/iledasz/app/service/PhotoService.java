package pl.iledasz.app.service;

import pl.iledasz.app.entities.Photo;

import java.util.List;

public interface PhotoService {

    Photo findById(Long id);

    Photo findByFilename(String filename);

    List<Photo> findAllPhotos();

}
