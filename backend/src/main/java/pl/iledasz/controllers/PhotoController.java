package pl.iledasz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.iledasz.DTO.PhotoDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.service.AdvertisementService;
import pl.iledasz.service.PhotoService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    private  static String default_image_desc = "desc";

    @RequestMapping(value = "api/photos")
    public List<PhotoDTO> getPhotos() {
        return photoService.list();
    }

    @RequestMapping(value = "api/photos/{id}")
    public ResponseEntity<byte[]> getPhotoById(@PathVariable("id") Long id) {

        Photo photo = photoService.findPhotoById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(photo.getImage().length);
        return new ResponseEntity<>(photo.getImage(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "api/{advertId}/sendNudes")
    public void addPhotoToAdvert(@PathVariable("advertId") Long advertID,Principal principal, @ModelAttribute MultipartFile image, HttpServletResponse httpServletResponse){
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        try {
            photoService.addPhotoToAdvert(advertID, default_image_desc, image.getBytes(), principal);
        }catch (IOException | SecurityException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
