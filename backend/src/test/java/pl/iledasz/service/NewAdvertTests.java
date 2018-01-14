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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import pl.iledasz.entities.AppUser;
import pl.iledasz.repository.AdvertPhotoRepository;
import pl.iledasz.repository.AdvertisementRepository;

import javax.servlet.Filter;
import java.security.Principal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

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
    @WithMockUser(username = "user", password = "SpringBootKing", roles = "USER")
    public void testCheckIfRegisteredUserAddingAdvert() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());

        RequestBuilder requestBuilder = post("/api/newadvert")
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr("advertForm", new NewAdvertDTO())
                .param("title", title)
                .param("description", description)
                .param("duration", String.valueOf(duration))
//                .param("imagesDescriptions", String.valueOf(imagesDescriptions))
                .param("images", String.valueOf(file));

        Mockito.when(principal.getName()).thenReturn(null);

        this.mockMvc.perform(requestBuilder).andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));

    }

}
