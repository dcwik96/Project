package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.entities.Photo;
import pl.iledasz.service.PhotoService;

import java.util.List;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "api/photos")
    public List<Photo> getPhotos() {
        return photoService.list();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "api/photos/{id}")
    public Photo getPhotoById(@PathVariable("id") Long id) {
        return photoService.findPhotoById(id);
    }
}
