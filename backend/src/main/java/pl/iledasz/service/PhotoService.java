package pl.iledasz.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.DTO.PhotoDTO;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.PhotoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public List<PhotoDTO> list() {

        ModelMapper modelMapper = new ModelMapper();

        List<Photo> photos = photoRepository.findAll();
        List<PhotoDTO> photosDTO= new ArrayList<>();
        for (Photo photo : photos)
        {
            PhotoDTO photoDTO = modelMapper.map(photo,PhotoDTO.class);
            photosDTO.add(photoDTO);
        }

        return photosDTO;
    }

    public Photo findPhotoById(Long id) {
        return photoRepository.findOneById(id);
    }
}
