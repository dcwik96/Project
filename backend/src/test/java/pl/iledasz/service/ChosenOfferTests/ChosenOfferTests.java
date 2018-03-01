package pl.iledasz.service.ChosenOfferTests;

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
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Offer;
import pl.iledasz.entities.ChosenOffer;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.OfferRepository;
import pl.iledasz.repository.ChosenOfferRepository;

import javax.servlet.Filter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ChosenOfferTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    OfferRepository offerRepository;

    @MockBean
    AppUserRepository appUserRepository;

    @MockBean
    AdvertisementRepository advertisementRepository;

    @MockBean
    ChosenOfferRepository chosenOfferRepository;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String user = "user";
    private static final String userTwo = "userex";
    private static final String password = "SpringBootKing";
    private static final String role_user = "USER";

    private static final Long idOne = Long.valueOf(1);
    private static final Long idTwo = Long.valueOf(2);
    private static final Long idThree = Long.valueOf(3);

    private RequestBuilder requestBuilder;
    private Advertisement advertisement;
    private Offer offer;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();

        requestBuilder =
                get("/api/advert/select/" + idTwo)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        advertisement = new Advertisement();
        advertisement.setId(idOne);
        advertisement.setAvailable(true);

        offer = new Offer();
        AppUser selectedUser = new AppUser();
        selectedUser.setLogin(userTwo);

        offer.setAppUser(selectedUser);
        offer.setAdvertisement(advertisement);

        Mockito.when(offerRepository.findOfferByIdAndAdvertisement_AppUser_LoginAndAdvertisement_AvailableTrue(idTwo, user)).thenReturn(offer);
        Mockito.when(chosenOfferRepository.save(Mockito.any(ChosenOffer.class))).thenReturn(null);
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferPositive() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        ArgumentCaptor<ChosenOffer> argumentCaptor = ArgumentCaptor.forClass(ChosenOffer.class);
        Mockito.verify(chosenOfferRepository).save(argumentCaptor.capture());
        ChosenOffer createdChosenOffer = argumentCaptor.getValue();

        assertEquals(advertisement.getId(), createdChosenOffer.getAdvertisement().getId());
        assertEquals(offer.getId(), createdChosenOffer.getOffer().getId());
        assertEquals(OffsetDateTime.now().plusDays(1).getDayOfYear(), createdChosenOffer.getExpiredDate().getDayOfYear());
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenOfferDidNotExistOrIsNotAvailable() throws Exception {

        //Assume that call to repository don't return anything.
        Mockito.when(offerRepository.findOfferByIdAndAdvertisement_AppUser_LoginAndAdvertisement_AvailableTrue(idTwo, user)).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(chosenOfferRepository, Mockito.never()).save(Mockito.any(ChosenOffer.class));
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());

    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenOtherOfferIsNotApproved() throws Exception {

        ChosenOffer offerInBase = new ChosenOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        //There is no way to have to selectOffers as approved (true) or without reaction(null)
        offerInBase.setApproved(false);


        offerInBase.setOffer(new Offer());
        offerInBase.getOffer().setId(idOne);

        List<ChosenOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        advertisement.setChosenOffers(inBaseList);


        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        ArgumentCaptor<ChosenOffer> argumentCaptor = ArgumentCaptor.forClass(ChosenOffer.class);
        Mockito.verify(chosenOfferRepository).save(argumentCaptor.capture());
        ChosenOffer createdChosenOffer = argumentCaptor.getValue();

        assertEquals(advertisement.getId(), createdChosenOffer.getAdvertisement().getId());
        assertEquals(offer.getId(), createdChosenOffer.getOffer().getId());
        assertEquals(OffsetDateTime.now().plusDays(1).getDayOfYear(), createdChosenOffer.getExpiredDate().getDayOfYear());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenOtherOfferIsApproved() throws Exception {

        ChosenOffer offerInBase = new ChosenOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        offerInBase.setApproved(true);
        offerInBase.setOffer(new Offer());
        offerInBase.getOffer().setId(idOne);


        List<ChosenOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        advertisement.setChosenOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(chosenOfferRepository, Mockito.never()).save(Mockito.any(ChosenOffer.class));
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenOtherOfferIsWaiting() throws Exception {

        ChosenOffer offerInBase = new ChosenOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        offerInBase.setOffer(new Offer());
        offerInBase.getOffer().setId(idOne);

        List<ChosenOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        advertisement.setChosenOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(chosenOfferRepository, Mockito.never()).save(Mockito.any(ChosenOffer.class));
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenThereAreOfferApprovedAndOfferRejected() throws Exception {

        ChosenOffer offerInBase = new ChosenOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        offerInBase.setApproved(false);
        offerInBase.setOffer(new Offer());
        offerInBase.getOffer().setId(idOne);

        ChosenOffer offerInBaseTwo = new ChosenOffer();
        offerInBaseTwo.setId(idThree);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        offerInBaseTwo.setApproved(true);
        offerInBaseTwo.setOffer(new Offer());
        offerInBaseTwo.getOffer().setId(idThree);



        List<ChosenOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        inBaseList.add(offerInBaseTwo);
        advertisement.setChosenOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(chosenOfferRepository, Mockito.never()).save(Mockito.any(ChosenOffer.class));

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenThereAreTwoRejectedOffers() throws Exception {

        ChosenOffer offerInBase = new ChosenOffer();
        offerInBase.setId(idOne);
        offerInBase.setApproved(false);
        offerInBase.setOffer(new Offer());
        offerInBase.getOffer().setId(idOne);

        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        ChosenOffer offerInBaseTwo = new ChosenOffer();
        offerInBaseTwo.setId(idThree);
        offerInBaseTwo.setApproved(false);
        offerInBase.setExpiredDate(OffsetDateTime.now().minusHours(6));
        offerInBaseTwo.setOffer(new Offer());
        offerInBaseTwo.getOffer().setId(idThree);


        List<ChosenOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        inBaseList.add(offerInBaseTwo);
        advertisement.setChosenOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(chosenOfferRepository, Mockito.only()).save(Mockito.any(ChosenOffer.class));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenThereIsWaitingOfferButDateExpired() throws Exception {

        ChosenOffer offerInBase = new ChosenOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().minusDays(1));
        offerInBase.setOffer(new Offer());
        offerInBase.getOffer().setId(idOne);


        List<ChosenOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        advertisement.setChosenOffers(inBaseList);

        ArgumentCaptor<ChosenOffer> argumentCaptor = ArgumentCaptor.forClass(ChosenOffer.class);
        List <ChosenOffer> createdChosenOffers = argumentCaptor.getAllValues();

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        Mockito.verify(chosenOfferRepository, Mockito.times(2) ).save(argumentCaptor.capture());

        assertEquals(false, createdChosenOffers.get(0).getApproved());
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        assertEquals(advertisement.getId(), createdChosenOffers.get(1).getAdvertisement().getId());
        assertEquals(offer.getId(), createdChosenOffers.get(1).getOffer().getId());
        assertEquals(OffsetDateTime.now().plusDays(1).getDayOfYear(), createdChosenOffers.get(1).getExpiredDate().getDayOfYear());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectTheSameOfferSecondTimes() throws Exception {

        ChosenOffer offerInBase = new ChosenOffer();
        offerInBase.setId(idOne);
        offerInBase.setOffer(new Offer());
        offerInBase.getOffer().setId(idTwo);

        offerInBase.setExpiredDate(OffsetDateTime.now().minusDays(1));

        List<ChosenOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);

        advertisement.setChosenOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        Mockito.verify(chosenOfferRepository, Mockito.never() ).save(Mockito.any(ChosenOffer.class));
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectTheSameOfferSecondTimeWithSomeRejectedOfferInBase() throws Exception {

        ChosenOffer offerInBase = new ChosenOffer();
        offerInBase.setId(idOne);
        offerInBase.setOffer(new Offer());
        offerInBase.getOffer().setId(idOne);
        offerInBase.setApproved(false);

        ChosenOffer offerInBaseTwo = new ChosenOffer();
        offerInBaseTwo.setId(idTwo);
        offerInBaseTwo.setOffer(new Offer());
        offerInBaseTwo.getOffer().setId(idTwo);
        offerInBaseTwo.setApproved(false);

        offerInBase.setExpiredDate(OffsetDateTime.now().minusDays(1));

        List<ChosenOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        inBaseList.add(offerInBaseTwo);

        advertisement.setChosenOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        Mockito.verify(chosenOfferRepository, Mockito.never() ).save(Mockito.any(ChosenOffer.class));
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }
}


