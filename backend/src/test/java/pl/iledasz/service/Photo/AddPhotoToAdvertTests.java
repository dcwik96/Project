package pl.iledasz.service.Photo;

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
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.PhotoRepository;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AddPhotoToAdvertTests {
    private static final String USER = "user";
    private static final String PASSWORD = "SpringBootKing";
    private static final String ROLE_USER = "USER";
    private static final Long ID_ONE = 1L;

    private static final MockMultipartFile FIRST_PHOTO = new MockMultipartFile("image", "filename.txt", "image/jpeg", "Lets see how W.Korol is great".getBytes());

    @MockBean
    private PhotoRepository photoRepository;

    @MockBean
    private AdvertPhotoRepository advertPhotoRepository;

    @MockBean
    private AdvertisementRepository advertisementRepository;


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    private Advertisement advertisement = new Advertisement();

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();

        advertisement.setId(1L);

        Mockito.when(advertPhotoRepository.save(Mockito.any(AdvertPhoto.class))).thenReturn(null);
        Mockito.when(photoRepository.save(any(Photo.class))).thenReturn(null);

    }

    @Test
    public void IsEndpointSecure() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/"+ID_ONE+"/sendNudes")
                        .file(FIRST_PHOTO);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.FOUND.value(), response.getStatus());



        advertisement.setId(ID_ONE);

    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void addPhotoPositive() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
        MockMvcRequestBuilders
                .fileUpload("/api/"+ID_ONE+"/sendNudes")
                .file( FIRST_PHOTO);

        Mockito.when(advertisementRepository.findAdvertisementsByAppUser_LoginAndId(USER, ID_ONE)).thenReturn(advertisement);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        ArgumentCaptor<Photo> photoCaptor = ArgumentCaptor.forClass(Photo.class);
        ArgumentCaptor<AdvertPhoto> advertPhotoCaptor = ArgumentCaptor.forClass(AdvertPhoto.class);

        Mockito.verify(advertPhotoRepository, only()).save(advertPhotoCaptor.capture());

        Mockito.verify(photoRepository, only()).save(photoCaptor.capture());
        AdvertPhoto savedAdvertPhoto = advertPhotoCaptor.getValue();
        Photo savedPhoto = photoCaptor.getValue();

        assertEquals(savedAdvertPhoto.getAdvertisement(), advertisement);
        assertEquals(savedPhoto.getAdvertphoto(), savedAdvertPhoto);
        assertEquals(savedPhoto.getImage(), FIRST_PHOTO.getBytes());

    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void TryAddNullPhoto() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/"+ID_ONE+"/sendNudes");

        Mockito.when(advertisementRepository.findAdvertisementsByAppUser_LoginAndId(USER, ID_ONE)).thenReturn(advertisement);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());

        Mockito.verify(advertPhotoRepository, never()).save(any(AdvertPhoto.class));
        Mockito.verify(photoRepository,never()).save(any(Photo.class));

    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void TryAddPhotoToAdvertWhenUserIsNotAdvertOwner() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .fileUpload("/api/"+ID_ONE+"/sendNudes")
                        .file( FIRST_PHOTO);
        //Advert shouldn't match
        Mockito.when(advertisementRepository.findAdvertisementsByAppUser_LoginAndId(USER, ID_ONE)).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());

        Mockito.verify(advertPhotoRepository, never()).save(any(AdvertPhoto.class));
        Mockito.verify(photoRepository,never()).save(any(Photo.class));

    }


}
