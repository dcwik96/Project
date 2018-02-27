package pl.iledasz.service;

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
import pl.iledasz.DTO.AppUserDTO;
import pl.iledasz.entities.Advertisement;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Offer;
import pl.iledasz.entities.SelectOffer;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.OfferRepository;
import pl.iledasz.repository.SelectOfferRepository;

import javax.servlet.Filter;
import java.math.BigDecimal;
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
public class SelectOfferTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    OfferRepository offerRepository;

    @MockBean
    AppUserRepository appUserRepository;

    @MockBean
    AdvertisementRepository advertisementRepository;

    @MockBean
    SelectOfferRepository selectOfferRepository;

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
    private static final String name = "Alex";
    private static final String surname = " Hunter";
    private static final String email = "mario@xs.ps";
    private static final String phone_number = "234332423";

    private static final BigDecimal offerOnePrice = BigDecimal.valueOf(123.44);
    private static final BigDecimal offerTwoPrice = BigDecimal.valueOf(453.44);
    private static final BigDecimal badOfferPrice = BigDecimal.valueOf(-123.55);

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
        Mockito.when(selectOfferRepository.save(Mockito.any(SelectOffer.class))).thenReturn(null);
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferPositive() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        ArgumentCaptor<SelectOffer> argumentCaptor = ArgumentCaptor.forClass(SelectOffer.class);
        Mockito.verify(selectOfferRepository).save(argumentCaptor.capture());
        SelectOffer createdSelectOffer = argumentCaptor.getValue();

        assertEquals(advertisement.getId(), createdSelectOffer.getAdvertisement().getId());
        assertEquals(offer.getId(), createdSelectOffer.getOffer().getId());
        assertEquals(OffsetDateTime.now().plusDays(1).getDayOfYear(), createdSelectOffer.getExpiredDate().getDayOfYear());
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenOfferDidNotExistOrIsNotAvailable() throws Exception {

        //Assume that call to repository don't return anything.
        Mockito.when(offerRepository.findOfferByIdAndAdvertisement_AppUser_LoginAndAdvertisement_AvailableTrue(idTwo, user)).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(selectOfferRepository, Mockito.never()).save(Mockito.any(SelectOffer.class));
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());

    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenOtherOfferIsNotApproved() throws Exception {

        SelectOffer offerInBase = new SelectOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        //There is no way to have to selectOffers as approved (true) or without reaction(null)
        offerInBase.setApproved(false);

        List<SelectOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        advertisement.setSelectOffers(inBaseList);


        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        ArgumentCaptor<SelectOffer> argumentCaptor = ArgumentCaptor.forClass(SelectOffer.class);
        Mockito.verify(selectOfferRepository).save(argumentCaptor.capture());
        SelectOffer createdSelectOffer = argumentCaptor.getValue();

        assertEquals(advertisement.getId(), createdSelectOffer.getAdvertisement().getId());
        assertEquals(offer.getId(), createdSelectOffer.getOffer().getId());
        assertEquals(OffsetDateTime.now().plusDays(1).getDayOfYear(), createdSelectOffer.getExpiredDate().getDayOfYear());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenOtherOfferIsApproved() throws Exception {

        SelectOffer offerInBase = new SelectOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        //There is no way to have two selectOffers as approved (true) or without reaction(null)
        offerInBase.setApproved(true);

        List<SelectOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        advertisement.setSelectOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(selectOfferRepository, Mockito.never()).save(Mockito.any(SelectOffer.class));
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenOtherOfferIsWaiting() throws Exception {

        SelectOffer offerInBase = new SelectOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));

        List<SelectOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        advertisement.setSelectOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(selectOfferRepository, Mockito.never()).save(Mockito.any(SelectOffer.class));
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenThereAreOfferApprovedAndOfferRejected() throws Exception {

        SelectOffer offerInBase = new SelectOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        offerInBase.setApproved(false);
        SelectOffer offerInBaseTwo = new SelectOffer();
        offerInBaseTwo.setId(idThree);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        offerInBaseTwo.setApproved(true);


        List<SelectOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        inBaseList.add(offerInBaseTwo);
        advertisement.setSelectOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(selectOfferRepository, Mockito.never()).save(Mockito.any(SelectOffer.class));

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenThereAreTwoRejectedOffers() throws Exception {

        SelectOffer offerInBase = new SelectOffer();
        offerInBase.setId(idOne);
        offerInBase.setApproved(false);
        offerInBase.setExpiredDate(OffsetDateTime.now().plusHours(6));
        SelectOffer offerInBaseTwo = new SelectOffer();
        offerInBaseTwo.setId(idThree);
        offerInBaseTwo.setApproved(false);
        offerInBase.setExpiredDate(OffsetDateTime.now().minusHours(6));

        List<SelectOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        inBaseList.add(offerInBaseTwo);
        advertisement.setSelectOffers(inBaseList);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(selectOfferRepository, Mockito.only()).save(Mockito.any(SelectOffer.class));

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void selectOfferWhenThereIsWaitingOfferButDateExpired() throws Exception {

        SelectOffer offerInBase = new SelectOffer();
        offerInBase.setId(idOne);
        offerInBase.setExpiredDate(OffsetDateTime.now().minusDays(1));

        List<SelectOffer> inBaseList = new ArrayList<>();
        inBaseList.add(offerInBase);
        advertisement.setSelectOffers(inBaseList);

        ArgumentCaptor<SelectOffer> argumentCaptor = ArgumentCaptor.forClass(SelectOffer.class);
        List <SelectOffer> createdSelectOffers = argumentCaptor.getAllValues();

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        Mockito.verify(selectOfferRepository, Mockito.times(2) ).save(argumentCaptor.capture());

        assertEquals(false,createdSelectOffers.get(0).getApproved());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}


