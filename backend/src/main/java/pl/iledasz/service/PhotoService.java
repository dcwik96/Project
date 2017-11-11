package pl.iledasz.service;

import pl.iledasz.entities.Photo;

import java.util.List;

public interface PhotoService {

    Photo findById(Long id);

    Photo findByFilename(String filename);

    List<Photo> findAllPhotos();

}
