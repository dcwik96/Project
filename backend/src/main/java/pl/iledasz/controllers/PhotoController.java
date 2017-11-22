package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.iledasz.DTO.PhotoDTO;
import pl.iledasz.entities.Photo;
import pl.iledasz.service.PhotoService;

import java.util.List;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "api/photos")
    public List<PhotoDTO> getPhotos() {
        return photoService.list();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "api/photos/{id}")
    public ResponseEntity<byte[]> getPhotoById(@PathVariable("id") Long id) {

         Photo photo = photoService.findPhotoById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(photo.getImage().length);
        return new ResponseEntity<>(photo.getImage(), headers, HttpStatus.OK);

    }
}
