package pl.iledasz.service;

import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.iledasz.DTO.*;
import pl.iledasz.entities.*;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.OfferRepository;
import pl.iledasz.repository.PhotoRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    OfferRepository offerRepository;
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

    public AdvertisementDTO randomAdvert()
    {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(advertisementRepository.randomOne(), AdvertisementDTO.class);
    }

    public List<AdvertisementDTO> getLatestAdverts() {
        ModelMapper modelMapper = new ModelMapper();
        OffsetDateTime now = OffsetDateTime.now();
        List<Advertisement> adverts = advertisementRepository.findAllByEndDateAfterAndAndAvailableTrueOrderByEndDateAsc(now);

        List<AdvertisementDTO> advertisementDTOS = new ArrayList<>();

        for (Advertisement advertisement : adverts) {
            AdvertisementDTO advertisementDTO = modelMapper.map(advertisement, AdvertisementDTO.class);
            advertisementDTOS.add(advertisementDTO);
        }

        return advertisementDTOS;
    }

    public List<LightAdvertisementDTO> getLatestLightAdverts() {

        OffsetDateTime now = OffsetDateTime.now();
        List<Advertisement> adverts = advertisementRepository.findAllByEndDateAfterAndAndAvailableTrueOrderByEndDateAsc(now);

        return mapToLightAdvertisement(adverts);
    }

    public List<LightAdvertisementDTO> getUserLightAdverts(Principal principal) {

        AppUser appUser = appUserService.findByLogin(principal.getName());
        List<Advertisement> adverts = advertisementRepository.findAllByAppUser(appUser);
        return mapToLightAdvertisement(adverts);
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

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaledImg, "jpg", baos);

            AdvertPhoto advertPhoto = new AdvertPhoto(newAdvertisement, imageWithDescription.getKey());
            advertPhotoRepository.save(advertPhoto);
            Photo photo = new Photo(baos.toByteArray(), advertPhoto);
            photoRepository.save(photo);
        }

    }

    public boolean checkAdvertOwnerIsLoggedUser(Principal principal, Long id)
    {
        AppUser appUser = appUserService.findByLogin(principal.getName());
        if(advertisementRepository.findAdvertisementsByAppUserAndId(appUser, id) == null)
            return false;
        return true;
    }

    private List<LightAdvertisementDTO> mapToLightAdvertisement(List<Advertisement> adverts)
    {
        List<LightAdvertisementDTO> advertisementDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Advertisement advertisement : adverts) {

            LightAdvertisementDTO advertisementDTO = modelMapper.map(advertisement, LightAdvertisementDTO.class);
            advertisementDTO.setPhoto(modelMapper.map(advertisement.getPhotos().get(0), AdvertPhotoDTO.class));
            advertisementDTOS.add(advertisementDTO);
        }

        return advertisementDTOS;
    }

}
