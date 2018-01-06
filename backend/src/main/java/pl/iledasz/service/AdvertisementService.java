package pl.iledasz.service;

import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.iledasz.DTO.AdvertisementDTO;
import pl.iledasz.DTO.NewAdvertDTO;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.PhotoRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AdvertPhotoRepository advertPhotoRepository;

    public AdvertisementDTO findOneById(Long id) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(
                advertisementRepository.findOneById(id), AdvertisementDTO.class);
    }

    public List<AdvertisementDTO> getLatestAdverts() {
        ModelMapper modelMapper = new ModelMapper();

        List<Advertisement> adverts = advertisementRepository.findAll();
        adverts.sort(Comparator.comparing(Advertisement::getEndDate));
        List<AdvertisementDTO> advertisementDTOS = new ArrayList<>();

        for (Advertisement advertisement : adverts) {
            AdvertisementDTO advertisementDTO = modelMapper.map(advertisement, AdvertisementDTO.class);
            advertisementDTOS.add(advertisementDTO);
        }

        return advertisementDTOS;
    }


    public void createNewAdvertisement(NewAdvertDTO newAdvertForm, Principal principal) throws IOException {

        Advertisement newAdvertisement = new Advertisement();

        newAdvertisement.setDescription(newAdvertForm.getDescription());
        newAdvertisement.setTitle(newAdvertForm.getTitle());
        newAdvertisement.setStartDate(OffsetDateTime.now());
        newAdvertisement.setEndDate(newAdvertisement.getStartDate().plusDays(newAdvertForm.getDuration()));
        newAdvertisement.setDuration(newAdvertForm.getDuration());
        newAdvertisement.setAvailable(true);

        AppUser appUser = appUserService.findByLogin(principal.getName());

        newAdvertisement.setAppUser(appUser);

        advertisementRepository.save(newAdvertisement);

        TreeMap<String, MultipartFile> imagesWithDescriptions = newAdvertForm.getPhotosWithDescriptions();

        for (Map.Entry<String, MultipartFile> imageWithDescription : imagesWithDescriptions.entrySet()) {
            InputStream in = new ByteArrayInputStream(imageWithDescription.getValue().getBytes());
            BufferedImage image = ImageIO.read(in);

            BufferedImage scaledImg = Thumbnails.of(image)
                    .size(800, 600)
                    .asBufferedImage();
            byte[] imageBytes = ((DataBufferByte) scaledImg.getData().getDataBuffer()).getData();
            AdvertPhoto advertPhoto = new AdvertPhoto(newAdvertisement, imageWithDescription.getKey());
            advertPhotoRepository.save(advertPhoto);
            Photo photo = new Photo(imageBytes, advertPhoto);
            photoRepository.save(photo);
        }

    }
}
