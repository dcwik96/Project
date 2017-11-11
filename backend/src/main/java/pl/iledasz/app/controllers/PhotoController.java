package pl.iledasz.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.iledasz.app.service.PhotoServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Controller
public class PhotoController {

    @Autowired
    private PhotoServiceImpl photoService;

    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getPhoto() throws IOException {
        byte[] image = photoService.findPhotoAndConvertToByte(URL);//wrzuc path do zdj jak chcesz sprawdzic
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}
