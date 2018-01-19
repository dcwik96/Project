package pl.iledasz.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        Mockito.when(offerRepository.save(Mockito.any(Offer.class))).thenReturn(null);
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


        Mockito.when(advertisementRepository.findOneById(idOne)).thenReturn(advertisement);

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



}
