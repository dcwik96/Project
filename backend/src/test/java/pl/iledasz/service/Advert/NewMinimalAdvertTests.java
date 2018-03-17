package pl.iledasz.service.Advert;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.iledasz.Application;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;

import javax.servlet.Filter;

import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class NewMinimalAdvertTests {

    @MockBean
    AdvertisementRepository advertisementRepository;

    @MockBean
    AppUserRepository appUserRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;
    private AppUser appUser = new AppUser();
    private Advertisement advertisement = new Advertisement();


    private static final String USER = "user";
    private static final String PASSWORD = "SpringBootKing";
    private static final String ROLE_USER = "USER";

    private static final String TITLE = "Tytul";
    private static final String DESCRIPTION = "Description";
    private static final Long DURATION = 3L;
    private static final Long FAIL_DURATION = -3L;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();

        appUser.setLogin(USER);
        advertisement.setId(1L);
        advertisement.setDescription(DESCRIPTION);
        advertisement.setAvailable(true);
        advertisement.setDuration(DURATION);

        Mockito.when(appUserRepository.findByLogin(USER)).thenReturn(appUser);
        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(advertisement);
    }

    @Test
    public void IsEndpointSecure() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .post("/api/newAdvert")
                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION));

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();


        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.FOUND.value(), response.getStatus());

    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void AddMinimalAdvertPositive() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .post("/api/newAdvert")
                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION));

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        ArgumentCaptor<Advertisement> advertisementArgumentCaptor = ArgumentCaptor.forClass(Advertisement.class);
        Mockito.verify(appUserRepository, Mockito.only()).findByLogin(USER);
        Mockito.verify(advertisementRepository, Mockito.only()).save(advertisementArgumentCaptor.capture());


        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(TITLE, advertisementArgumentCaptor.getValue().getTitle());
        assertEquals(DESCRIPTION, advertisementArgumentCaptor.getValue().getDescription());
        assertEquals(DURATION, advertisementArgumentCaptor.getValue().getDuration());
        assertEquals(OffsetDateTime.now().getDayOfYear(), advertisementArgumentCaptor.getValue().getStartDate().getDayOfYear());
        assertEquals(OffsetDateTime.now().getDayOfYear() + DURATION, advertisementArgumentCaptor.getValue().getEndDate().getDayOfYear());
        assertEquals(USER, advertisementArgumentCaptor.getValue().getAppUser().getLogin());
        assertEquals(Long.valueOf(1), Long.valueOf(response.getContentAsString()));
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void AddMinimalAdvertWithoutTitle() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .post("/api/newAdvert")
//                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION));

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertEquals(Long.valueOf(0), Long.valueOf(response.getContentAsString()));
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void AddMinimalAdvertWithoutDescription() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .post("/api/newAdvert")
                        .param("title", TITLE)
//                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(DURATION));

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertEquals(Long.valueOf(0), Long.valueOf(response.getContentAsString()));
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void AddMinimalAdvertWithoutDuration() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .post("/api/newAdvert")
                        .param("title", TITLE)
                        .param("description", DESCRIPTION);
//                        .param("duration", String.valueOf(DURATION));

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertEquals(Long.valueOf(0), Long.valueOf(response.getContentAsString()));
    }

    @Test
    @WithMockUser(username = USER, password = PASSWORD, roles = ROLE_USER)
    public void AddMinimalAdvertWithoutNonValid() throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .post("/api/newAdvert")
                        .param("title", TITLE)
                        .param("description", DESCRIPTION)
                        .param("duration", String.valueOf(FAIL_DURATION));

        MvcResult mvcResult = this.mockMvc.perform(mockHttpServletRequestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertEquals(Long.valueOf(0), Long.valueOf(response.getContentAsString()));
    }
}
