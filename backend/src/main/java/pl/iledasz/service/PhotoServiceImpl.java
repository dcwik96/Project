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
}
