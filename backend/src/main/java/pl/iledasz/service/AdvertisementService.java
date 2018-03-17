package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.iledasz.DTO.AdvertPhotoDTO;
import pl.iledasz.DTO.Adverts.AdvertisementDTO;
import pl.iledasz.DTO.Adverts.LightAdvertisementDTO;
import pl.iledasz.DTO.Adverts.NewAdvertDTO;
import pl.iledasz.DTO.Adverts.NewMinimalAdvertDTO;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.OfferRepository;
import pl.iledasz.repository.PhotoRepository;

import java.io.IOException;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    public AdvertisementDTO randomAdvert() {
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
            AdvertPhoto advertPhoto = new AdvertPhoto(newAdvertisement, imageWithDescription.getKey());
            advertPhotoRepository.save(advertPhoto);
            Photo photo = new Photo(imageWithDescription.getValue().getBytes(), advertPhoto);
            photoRepository.save(photo);
        }

    }

    public Long createNewMinimalAdvertisement(NewMinimalAdvertDTO newMinimalAdvertDTO, Principal principal) throws IOException {

        Advertisement newAdvertisement = new Advertisement();

        newAdvertisement.setDescription(newMinimalAdvertDTO.getDescription());
        newAdvertisement.setTitle(newMinimalAdvertDTO.getTitle());
        newAdvertisement.setStartDate(OffsetDateTime.now());
        newAdvertisement.setEndDate(newAdvertisement.getStartDate().plusDays(newMinimalAdvertDTO.getDuration()));
        newAdvertisement.setDuration(newMinimalAdvertDTO.getDuration());
        newAdvertisement.setAvailable(true);

        AppUser appUser = appUserService.findByLogin(principal.getName());

        newAdvertisement.setAppUser(appUser);

        //Setting newAdvertisement here is Not mandatory but this is hacky way to set id value for tests (save() method updates given object and sets it ID value)
        newAdvertisement = advertisementRepository.save(newAdvertisement);

        return newAdvertisement.getId();

    }
    public boolean checkAdvertOwnerIsLoggedUser(Principal principal, Long id) {
        AppUser appUser = appUserService.findByLogin(principal.getName());
        return advertisementRepository.findAdvertisementsByAppUserAndId(appUser, id) != null;
    }

    private List<LightAdvertisementDTO> mapToLightAdvertisement(List<Advertisement> adverts) {
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
