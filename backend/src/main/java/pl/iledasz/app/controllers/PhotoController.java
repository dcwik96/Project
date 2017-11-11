package pl.iledasz.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.iledasz.app.entities.Photo;
import pl.iledasz.app.service.PhotoService;
import pl.iledasz.app.service.PhotoServiceImpl;

import java.util.List;

@Controller
public class PhotoController {

    @Autowired
    private PhotoServiceImpl photoService;

    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    @ResponseBody
    public List<Photo> getPhotos(){
        photoService.addPhoto(new Photo("asdf"));
        return photoService.findAllPhotos();
    }


}
