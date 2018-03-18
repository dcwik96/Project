package pl.iledasz.service;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.iledasz.DTO.OnlyIdDTO;
import pl.iledasz.DTO.PhotoDTO;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.PhotoRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AdvertPhotoRepository advertPhotoRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;
    private ModelMapper modelMapper = new ModelMapper();

    public List<PhotoDTO> list() {

        ModelMapper modelMapper = new ModelMapper();

        List<Photo> photos = photoRepository.findAll();
        List<PhotoDTO> photosDTO = new ArrayList<>();
        for (Photo photo : photos) {
            PhotoDTO photoDTO = modelMapper.map(photo, PhotoDTO.class);
            photosDTO.add(photoDTO);
        }

        return photosDTO;
    }

    public Photo findPhotoById(Long id) {
        return photoRepository.findByAdvertphoto_Id(id);
    }

    @Transactional
    public void addPhotoToAdvert(Long advertId , String desc, byte[] image, Principal principal) throws IOException {

        Advertisement advertisement = advertisementRepository.findAdvertisementsByAppUser_LoginAndId(principal.getName(), advertId);

        if( advertisement == null)
            throw new SecurityException("Advert don't exist");

        AdvertPhoto advertPhoto = new AdvertPhoto(advertisement, desc);
        advertPhotoRepository.save(advertPhoto);

        Photo photo = new Photo( image, advertPhoto);
        photoRepository.save(photo);
    }

    public List<OnlyIdDTO> getadvertPhotoIdsForAdvert(Long id) {
        List<AdvertPhoto> list = advertPhotoRepository.findAllByAdvertisement_Id(id);

        return modelMapper.map(list, new TypeToken<List<OnlyIdDTO>>() {}.getType());
    }
}
