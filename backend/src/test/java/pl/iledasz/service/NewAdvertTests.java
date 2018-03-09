package pl.iledasz.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.iledasz.Application;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.PhotoRepository;

import javax.servlet.Filter;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class NewAdvertTests {

    @MockBean
    AdvertisementRepository advertisementRepository;
    @MockBean
    AdvertPhotoRepository advertPhotoRepository;
    @MockBean
    AppUserRepository appUserRepository;
    @MockBean
    PhotoRepository photoRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String USER = "user";
    private static final String PASSWORD = "SpringBootKing";
    private static final String ROLE_USER = "USER";

    private static final String TITLE = "Tytul";
    private static final String DESCRIPTION = "Description";
    private static final Long DURATION = 3L;
    private static final String IMAGE_DESCRIPTION = "Description of photo";
    private static final MockMultipartFile FIRST_PHOTO = new MockMultipartFile("images", "filename.txt", "image/jpeg", "Lets see how W.Korol is great".getBytes());

    private static final String defaultMessageOfNullTitle = "Tytuł nie moze byc pusty!";
    private static final String defaultMessageOfNullDescription = "Opis nie moze byc pusty!";
    private static final String defaultMessageOfNullDuration = "Czas trwania oferty nie moze byc pusty!";
    private static final String defaultMessageOfNullImages = "Przynajmniej jedno zdjęcie!";
    private static final String defaultMessageOfNullImageDescription = "Opis  zdjęcia nie może byc pusty!";
    private static final String defaultMessageOfNotEqualsImageAndImageDescription = "Liczba opisów zdjęć nie odpowiada liczbie zdjęć";

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void checkAddAdvertWithOneImageProperly() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/newadvert")
                        .file(FIRST_PHOTO)
                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION))
                        .param("imagesDescriptions", IMAGE_DESCRIPTION);

        AppUser appUser = new AppUser();
        appUser.setLogin(USER);
        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(null);
        Mockito.when(appUserRepository.findByLogin(USER)).thenReturn(appUser);
        Mockito.when(advertPhotoRepository.save(Mockito.any(AdvertPhoto.class))).thenReturn(null);
        Mockito.when(photoRepository.save(Mockito.any(Photo.class))).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();
        ArgumentCaptor<Advertisement> argumentCaptor = ArgumentCaptor.forClass(Advertisement.class);
        ArgumentCaptor<Photo> photoArgumentCaptor = ArgumentCaptor.forClass(Photo.class);

        Mockito.verify(advertisementRepository).save(argumentCaptor.capture());
        Mockito.verify(appUserRepository, Mockito.times(1)).findByLogin(USER);
        Mockito.verify(advertPhotoRepository).save(Mockito.any(AdvertPhoto.class));
        Mockito.verify(photoRepository).save(photoArgumentCaptor.capture());

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(TITLE, argumentCaptor.getValue().getTitle());
        assertEquals(DESCRIPTION, argumentCaptor.getValue().getDescription());
        assertEquals(DURATION, argumentCaptor.getValue().getDuration());
        assertEquals(OffsetDateTime.now().getDayOfYear(), argumentCaptor.getValue().getStartDate().getDayOfYear());
        assertEquals(OffsetDateTime.now().getDayOfYear() + DURATION, argumentCaptor.getValue().getEndDate().getDayOfYear());
        assertEquals(USER, argumentCaptor.getValue().getAppUser().getLogin());
        assertEquals(FIRST_PHOTO.getBytes(), photoArgumentCaptor.getValue().getImage());
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void checkAddAdvertWithoutTitle() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/newadvert")
                        .file(FIRST_PHOTO)
//                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION))
                        .param("imagesDescriptions", IMAGE_DESCRIPTION);

        AppUser appUser = new AppUser();
        appUser.setLogin(USER);
        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(null);
        Mockito.when(appUserRepository.findByLogin(USER)).thenReturn(appUser);
        Mockito.when(advertPhotoRepository.save(Mockito.any(AdvertPhoto.class))).thenReturn(null);
        Mockito.when(photoRepository.save(Mockito.any(Photo.class))).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        Mockito.verify(appUserRepository, Mockito.times(0)).findByLogin(USER);
        Mockito.verify(advertPhotoRepository, Mockito.times(0)).save(Mockito.any(AdvertPhoto.class));

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
        assertEquals(defaultMessageOfNullTitle, response.getContentAsString());
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void checkAddAdvertWithoutDescription() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/newadvert")
                        .file(FIRST_PHOTO)
                        .param("title", TITLE)
//                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION))
                        .param("imagesDescriptions", IMAGE_DESCRIPTION);

        AppUser appUser = new AppUser();
        appUser.setLogin(USER);
        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(null);
        Mockito.when(appUserRepository.findByLogin(USER)).thenReturn(appUser);
        Mockito.when(advertPhotoRepository.save(Mockito.any(AdvertPhoto.class))).thenReturn(null);
        Mockito.when(photoRepository.save(Mockito.any(Photo.class))).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        Mockito.verify(appUserRepository, Mockito.times(0)).findByLogin(USER);
        Mockito.verify(advertPhotoRepository, Mockito.times(0)).save(Mockito.any(AdvertPhoto.class));

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
        assertEquals(defaultMessageOfNullDescription, response.getContentAsString());
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void checkAddAdvertWithoutDuration() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/newadvert")
                        .file(FIRST_PHOTO)
                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
//                        .param("duration", String.valueOf(DURATION))
                        .param("imagesDescriptions", IMAGE_DESCRIPTION);

        AppUser appUser = new AppUser();
        appUser.setLogin(USER);
        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(null);
        Mockito.when(appUserRepository.findByLogin(USER)).thenReturn(appUser);
        Mockito.when(advertPhotoRepository.save(Mockito.any(AdvertPhoto.class))).thenReturn(null);
        Mockito.when(photoRepository.save(Mockito.any(Photo.class))).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        Mockito.verify(appUserRepository, Mockito.times(0)).findByLogin(USER);
        Mockito.verify(advertPhotoRepository, Mockito.times(0)).save(Mockito.any(AdvertPhoto.class));

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
        assertEquals(defaultMessageOfNullDuration, response.getContentAsString());
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void checkAddAdvertWithoutImage() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/newadvert")
//                        .file(FIRST_PHOTO)
                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION))
                        .param("imagesDescriptions", IMAGE_DESCRIPTION);

        AppUser appUser = new AppUser();
        appUser.setLogin(USER);
        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(null);
        Mockito.when(appUserRepository.findByLogin(USER)).thenReturn(appUser);
        Mockito.when(advertPhotoRepository.save(Mockito.any(AdvertPhoto.class))).thenReturn(null);
        Mockito.when(photoRepository.save(Mockito.any(Photo.class))).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        Mockito.verify(appUserRepository, Mockito.times(0)).findByLogin(USER);
        Mockito.verify(advertPhotoRepository, Mockito.times(0)).save(Mockito.any(AdvertPhoto.class));

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
        assertEquals(defaultMessageOfNullImages, response.getContentAsString());
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void checkAddAdvertWithoutImageDescription() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/newadvert")
                        .file(FIRST_PHOTO)
                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION));
//                        .param("imagesDescriptions", IMAGE_DESCRIPTION);

        AppUser appUser = new AppUser();
        appUser.setLogin(USER);
        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(null);
        Mockito.when(appUserRepository.findByLogin(USER)).thenReturn(appUser);
        Mockito.when(advertPhotoRepository.save(Mockito.any(AdvertPhoto.class))).thenReturn(null);
        Mockito.when(photoRepository.save(Mockito.any(Photo.class))).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        Mockito.verify(appUserRepository, Mockito.times(0)).findByLogin(USER);
        Mockito.verify(advertPhotoRepository, Mockito.times(0)).save(Mockito.any(AdvertPhoto.class));

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
        assertEquals(defaultMessageOfNullImageDescription, response.getContentAsString());
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void checkIfNumberOfDescriptionOfPhotoIsEqualsToPhotos() {
        //nie dodaje wiecej jak 1 zdj wiec ciezko narazie to sprawdzic.
    }

    //SPRAWDZENIE BINDINGRESULT
}
