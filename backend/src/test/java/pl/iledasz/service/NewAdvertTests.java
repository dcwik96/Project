package pl.iledasz.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import pl.iledasz.Application;
import pl.iledasz.DTO.NewAdvertDTO;
import pl.iledasz.entities.AdvertPhoto;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Photo;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.PhotoRepository;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

    private static final String user = "user";
    private static final String password = "SpringBootKing";
    private static final String role_user = "USER";

    static String title = "Tytul";
    static String description = "Description";
    static Long duration = 1l;
    static List<String> imagesDescriptions = null;
    static List<MultipartFile> images = null;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();

        //Dont save new advert to database
        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(null);
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void checkAddingAdvertByAuthorizedUserWithoutImageDescriptions() throws Exception {
        String imageDesc = "asdf";
        File file = new File("./src/test/java/pl/iledasz/service/images/img.jpeg");

        BufferedImage img = ImageIO.read(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write( img, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        MockMultipartFile firstFile = new MockMultipartFile("images", "filename.txt", "image/jpeg", imageInByte);
//        MockMultipartFile secFile = new MockMultipartFile("images", "filename.txt", "image/jpeg", "MyLovelyFuckingFile".getBytes());
                 MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                         MockMvcRequestBuilders
                            .fileUpload("/api/newadvert")
                            .file(firstFile)
                                 // .file(secFile)
                            .param("title", title)
                            .param("description", description)
                            .param("duration", String.valueOf(duration))
                            .param("imagesDescriptions", imageDesc);

        AppUser appUser = new AppUser();
        appUser.setLogin(user);
        Mockito.when(appUserRepository.findByLogin(user)).thenReturn(appUser);
        Mockito.when(advertPhotoRepository.save(Mockito.any(AdvertPhoto.class))).thenReturn(null);
        Mockito.when(photoRepository.save(Mockito.any(Photo.class))).thenReturn(null);

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println(response.getStatus());
        System.out.println(response.getContentAsString());

    }

}
