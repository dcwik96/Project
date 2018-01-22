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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import pl.iledasz.Application;
import pl.iledasz.DTO.NewAdvertDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;

import javax.servlet.Filter;
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
    Principal principal;

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
    public void testAuthentication() {

    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void checkAddingAdvertByAuthorizedUserWithoutImageDescriptions() throws Exception {
        MultipartFile photo = new MockMultipartFile("file", "orig", "multipart/form-data", "bar" .getBytes());
        String imageDesc = "asdf";

        RequestBuilder requestBuilder = post("/api/newadvert")
                .accept(MediaType.ALL)
                .contentType(MediaType.ALL)
                .sessionAttr("advertForm", new NewAdvertDTO())
                .param("title", title)
                .param("description", description)
                .param("duration", String.valueOf(duration))
                .param("imagesDescriptions", imageDesc)
                .param("images", photo.toString());

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@" +response.getStatus());

    }

}
