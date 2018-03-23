package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.DTO.AdvertPhotoDTO;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.repository.AdvertPhotoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertPhotoService {

    @Autowired
    private AdvertPhotoRepository advertPhotoRepository;

    public List<AdvertPhotoDTO> list() {
        ModelMapper modelMapper = new ModelMapper();

        List<AdvertPhoto> advertPhotos = advertPhotoRepository.findAll();

        List<AdvertPhotoDTO> advertPhotoDTOS = new ArrayList<>();

        for (AdvertPhoto advertPhoto : advertPhotos)
            advertPhotoDTOS.add(modelMapper.map(advertPhoto, AdvertPhotoDTO.class));

        return advertPhotoDTOS;
    }

    public List<AdvertPhotoDTO> getAllPhotosOfItem(long id) {
        ModelMapper modelMapper = new ModelMapper();

        List<AdvertPhoto> advertPhotos = advertPhotoRepository.findAdvertPhotosByAdvertisement_Id(id);
        List<AdvertPhotoDTO> advertPhotoDTOS = new ArrayList<>();

        for (AdvertPhoto advertPhoto : advertPhotos)
            advertPhotoDTOS.add(modelMapper.map(advertPhoto, AdvertPhotoDTO.class));

        return advertPhotoDTOS;
    }


}
