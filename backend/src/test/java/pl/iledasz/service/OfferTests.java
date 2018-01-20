package pl.iledasz.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import pl.iledasz.Application;
import pl.iledasz.DTO.OfferDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Offer;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.OfferRepository;

import javax.servlet.Filter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class OfferTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    OfferRepository offerRepository;

    @MockBean
    AppUserRepository appUserRepository;

    @MockBean
    AdvertisementRepository advertisementRepository;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String user = "user";
    private static final String userTwo = "userex";
    private static final String password = "SpringBootKing";
    private static final String role_user = "USER";

    private static final long idOne = 1;
    private static final long idTwo = 2;
    private static final String title =  "AdvertTitle";
    private static final String description = "AdvertDescription";
  //  private static final OffsetDateTime startDate = OffsetDateTime.now().minusDays((long) 3);
   // private static final OffsetDateTime ednDate = OffsetDateTime.now();
  //  private static final long duration = 3;
 //   private static final boolean available = true;
    private static final BigDecimal offerOnePrice = BigDecimal.valueOf(123.44);
    private static final BigDecimal offerTwoPrice = BigDecimal.valueOf(453.44);

    ObjectMapper objectMapper = new ObjectMapper();



    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void getOffersForAdvertWhenLoggedUserIsAdvertOwner() throws Exception {
        //Prepare offers
        List<Offer> advertOffersList = new ArrayList<>();
        Offer offerOne = new Offer();
        offerOne.setId(idOne);
        offerOne.setOffer(offerOnePrice);

        Offer offerTwo = new Offer();
        offerTwo.setId(idTwo);
        offerTwo.setOffer(offerTwoPrice);

        advertOffersList.add(offerOne);
        advertOffersList.add(offerTwo);

        //Prepare Advert owner
        AppUser owner = new AppUser();
        owner.setLogin(user);


        //Prepare advertisement response
        Advertisement advertisement = new Advertisement();
        advertisement.setId(idOne);
        advertisement.setOffers(advertOffersList);
        advertisement.setAppUser(owner);


        Mockito.when(advertisementRepository.findAdvertisementsByAppUser_LoginAndId(idOne, user)).thenReturn(advertisement);

        RequestBuilder requestBuilder =
               get("/api/advert/"+idOne+"/offers")
                        .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        List<OfferDTO> receivedOffers = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<OfferDTO>>(){});

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(advertOffersList.size(), receivedOffers.size());

        for(Offer advertOffer : advertOffersList)
        {
            for(OfferDTO receivedOffer : receivedOffers)
                if(advertOffer.getId().equals(receivedOffer.getId()) && advertOffer.getOffer().equals(receivedOffer.getOffer()))
                {
                    receivedOffers.remove(receivedOffer);
                    break;
                }
        }
        assertEquals(0 , receivedOffers.size());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void getOffersForAdvertWhenLoggedUserIsNotAdvertOwner() throws Exception {

        AppUser owner = new AppUser();
        owner.setLogin(userTwo);

        Advertisement advertisement = new Advertisement();
        advertisement.setId(idOne);
        advertisement.setAppUser(owner);

        Mockito.when(advertisementRepository.findAdvertisementsByAppUser_LoginAndId(idOne, userTwo)).thenReturn(null);

        RequestBuilder requestBuilder =
                get("/api/advert/"+idOne+"/offers")
                        .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void getEmptyOffersForAdvertWhenLoggedUserIsAdvertOwner() throws Exception {

        AppUser owner = new AppUser();
        owner.setLogin(user);

        Advertisement advertisement = new Advertisement();
        advertisement.setId(idOne);
        advertisement.setAppUser(owner);
        advertisement.setOffers(new ArrayList<>());

        Mockito.when(advertisementRepository.findAdvertisementsByAppUser_LoginAndId(idOne, user)).thenReturn(advertisement);

        RequestBuilder requestBuilder =
                get("/api/advert/"+idOne+"/offers")
                        .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        System.out.println(response.getContentAsString());
        assertEquals(objectMapper.writeValueAsString(new ArrayList<>()),response.getContentAsString());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void getUserOfferForAdvertWhenAdvertHadUserOffer() throws Exception {
        Offer offer = new Offer();
        offer.setId(idOne);
        offer.setOffer(offerOnePrice);

        Mockito.when(offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(idOne,user)).thenReturn(offer);

        RequestBuilder requestBuilder =
                get("/api/advert/"+idOne+"/UserOffer")
                        .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        OfferDTO receivedOffer = objectMapper.readValue(response.getContentAsString(), OfferDTO.class);

        assertEquals(offer.getId(), receivedOffer.getId());
        assertEquals(offer.getOffer(), receivedOffer.getOffer());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void getUserOfferForAdvertWhenAdvertDidNotHaveUserOffer() throws Exception {

        Mockito.when(offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(idOne,user)).thenReturn(null);

        RequestBuilder requestBuilder =
                get("/api/advert/"+idOne+"/UserOffer")
                        .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());

    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void putNewOfferWhenUserDidNotHaveOfferForAdvert () throws Exception {

        RequestBuilder requestBuilder =
                post("/api/advert/"+idOne+"/newOffer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("offer", offerOnePrice.toString());

        Advertisement advertisement = new Advertisement();
        advertisement.setId(idOne);

        AppUser loggedUser = new AppUser();
        loggedUser.setLogin(user);

        AppUser advertOwner = new AppUser();
        advertOwner.setLogin(userTwo);
        advertisement.setAppUser(advertOwner);

        Mockito.when(advertisementRepository.findAdvertisementByAppUser_LoginNotLikeAndId(user,idOne)).thenReturn(advertisement);
        Mockito.when(offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(idOne,user)).thenReturn(null);
        Mockito.when(appUserRepository.findByLogin(user)).thenReturn(loggedUser);
        Mockito.when(offerRepository.save(Mockito.any(Offer.class))).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        ArgumentCaptor<Offer> argumentCaptor = ArgumentCaptor.forClass(Offer.class);
        Mockito.verify(offerRepository).save(argumentCaptor.capture());
        Offer createdOffer = argumentCaptor.getValue();

        Mockito.verify(advertisementRepository, Mockito.only()).findAdvertisementByAppUser_LoginNotLikeAndId(user,idOne);
        Mockito.verify(offerRepository, Mockito.times(1)).findOfferByAdvertisement_IdAndAppUser_Login(idOne,user);
        Mockito.verify(appUserRepository, Mockito.times(1)).findByLogin(user);

        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals((Long) idOne, createdOffer.getAdvertisement().getId());
        assertEquals(user, createdOffer.getAppUser().getLogin());
        assertEquals(offerOnePrice, createdOffer.getOffer());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void putNewOfferWhenUserHadOfferForAdvert () throws Exception {

        RequestBuilder requestBuilder =
                post("/api/advert/"+idOne+"/newOffer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("offer", offerTwoPrice.toString());

        Advertisement advertisement = new Advertisement();
        advertisement.setId(idOne);

        AppUser loggedUser = new AppUser();
        loggedUser.setLogin(user);

        AppUser advertOwner = new AppUser();
        advertOwner.setLogin(userTwo);
        advertisement.setAppUser(advertOwner);

        Offer offerBefore = new Offer();
        offerBefore.setOffer(offerOnePrice);
        offerBefore.setId(idOne);
        offerBefore.setAppUser(loggedUser);
        offerBefore.setAdvertisement(advertisement);


        Mockito.when(advertisementRepository.findAdvertisementByAppUser_LoginNotLikeAndId(user,idOne)).thenReturn(advertisement);
        Mockito.when(offerRepository.findOfferByAdvertisement_IdAndAppUser_Login(idOne,user)).thenReturn(offerBefore);
        Mockito.when(offerRepository.save(Mockito.any(Offer.class))).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        ArgumentCaptor<Offer> argumentCaptor = ArgumentCaptor.forClass(Offer.class);
        Mockito.verify(offerRepository).save(argumentCaptor.capture());
        Offer createdOffer = argumentCaptor.getValue();

        Mockito.verify(advertisementRepository, Mockito.only()).findAdvertisementByAppUser_LoginNotLikeAndId(user,idOne);
        Mockito.verify(offerRepository, Mockito.times(1)).findOfferByAdvertisement_IdAndAppUser_Login(idOne,user);

        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals((Long) idOne, createdOffer.getAdvertisement().getId());
        assertEquals((Long) idOne, createdOffer.getId());
        assertEquals(user, createdOffer.getAppUser().getLogin());
        assertEquals(offerTwoPrice, createdOffer.getOffer());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void putNewOfferWhenUserIsAdvertOwner () throws Exception {

        RequestBuilder requestBuilder =
                post("/api/advert/"+idOne+"/newOffer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("offer", offerOnePrice.toString());


        Mockito.when(advertisementRepository.findAdvertisementByAppUser_LoginNotLikeAndId(user,idOne)).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();


        Mockito.verify(advertisementRepository, Mockito.only()).findAdvertisementByAppUser_LoginNotLikeAndId(user,idOne);

        assertEquals(HttpStatus.FORBIDDEN.value(),response.getStatus());
    }

}
