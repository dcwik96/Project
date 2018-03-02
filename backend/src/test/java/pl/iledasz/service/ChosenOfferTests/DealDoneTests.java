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
import pl.iledasz.entities.ChosenOffer;
import pl.iledasz.repository.AdvertisementRepository;
import pl.iledasz.repository.ChosenOfferRepository;

import javax.servlet.Filter;

import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.internal.verification.VerificationModeFactory.only;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DealDoneTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    ChosenOfferRepository chosenOfferRepository;

    @MockBean
    AdvertisementRepository advertisementRepository;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    private RequestBuilder requestBuilder;

    private static final Long idTwo = Long.valueOf(2);

    private static final String user = "user";
    private static final String password = "SpringBootKing";
    private static final String role_user = "USER";
    private static final String accept = "true";
    private static final String reject = "false";

    private Advertisement advertisement;
    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();

        requestBuilder =
                get("/api/requests//"+idTwo)
                        .param("reaction", accept)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        advertisement = new Advertisement();

        advertisement.setAvailable(true);

        Mockito.when(advertisementRepository.save(Mockito.any(Advertisement.class))).thenReturn(null);
    }

    @Test
    public void checkIsEndpointSecure() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertEquals(HttpStatus.FOUND.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void DealDone() throws Exception {

        ChosenOffer chosenOffer = new ChosenOffer();
        chosenOffer.setId(idTwo);
        chosenOffer.setExpiredDate(OffsetDateTime.now().plusHours(5));
        chosenOffer.setAdvertisement(advertisement);


        Mockito.when(chosenOfferRepository.findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter( eq(user),eq(idTwo), Mockito.any(OffsetDateTime.class))).thenReturn(chosenOffer);



        ArgumentCaptor<ChosenOffer> argumentCaptor = ArgumentCaptor.forClass(ChosenOffer.class);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        //I don't know why only() returns error when it finds one invocation.
        Mockito.verify(chosenOfferRepository,times(1)).findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter(Mockito.anyString(), Mockito.anyLong(), Mockito.any(OffsetDateTime.class));
        Mockito.verify(chosenOfferRepository, times(1)).save(argumentCaptor.capture());
        Mockito.verify(advertisementRepository, Mockito.only()).save(any(Advertisement.class));

        ChosenOffer receivedData = argumentCaptor.getValue();

        assertEquals(chosenOffer.getId(), receivedData.getId());
        assertEquals(true, receivedData.getApproved());
        assertFalse( receivedData.getAdvertisement().isAvailable());
        //ExpiredDate Should be updated. Now represents time when advert owner sees contact data.
        assertEquals(OffsetDateTime.now().plusDays(7).getDayOfYear(), receivedData.getExpiredDate().getDayOfYear());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void DealRejected() throws Exception {

        ChosenOffer chosenOffer = new ChosenOffer();
        chosenOffer.setId(idTwo);
        chosenOffer.setExpiredDate(OffsetDateTime.now().plusHours(5));

        chosenOffer.setAdvertisement(advertisement);

        Mockito.when(chosenOfferRepository.findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter( eq(user),eq(idTwo), Mockito.any(OffsetDateTime.class))).thenReturn(chosenOffer);

        requestBuilder =
                get("/api/requests//"+idTwo)
                        .param("reaction", reject)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED);


        ArgumentCaptor<ChosenOffer> argumentCaptor = ArgumentCaptor.forClass(ChosenOffer.class);
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        Mockito.verify(chosenOfferRepository, times(1)).findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter(Mockito.anyString(), Mockito.anyLong(), Mockito.any(OffsetDateTime.class));
        Mockito.verify(chosenOfferRepository, times(1)).save(argumentCaptor.capture());
        Mockito.verify(advertisementRepository, Mockito.never()).save(any(Advertisement.class));

        ChosenOffer receivedData = argumentCaptor.getValue();

        assertEquals(chosenOffer.getId(), receivedData.getId());
        assertEquals(false, receivedData.getApproved());
        assertEquals(OffsetDateTime.now().plusHours(5).getDayOfYear(), receivedData.getExpiredDate().getDayOfYear());

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void AcceptDealRejectedOrAcceptedBeforeOrWhenExpiredDatePassed() throws Exception {

        //assume that call to base doesn't return anything
        Mockito.when(chosenOfferRepository.findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter(eq(user),eq(idTwo), Mockito.any(OffsetDateTime.class))).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(chosenOfferRepository, Mockito.only()).findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter(Mockito.anyString(), Mockito.anyLong(), Mockito.any(OffsetDateTime.class));
        Mockito.verify(chosenOfferRepository, Mockito.never()).save(Mockito.any(ChosenOffer.class));

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    @WithMockUser(username = user, password = password, roles = role_user)
    public void SendNullReaction() throws Exception {

        requestBuilder =
                get("/api/requests//"+idTwo)
                        .param("reaction", "")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED);

        Mockito.when(chosenOfferRepository.findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter(eq(user),eq(idTwo), Mockito.any(OffsetDateTime.class))).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();
        Mockito.verify(chosenOfferRepository, Mockito.never()).findByOffer_AppUser_LoginAndIdAndApprovedIsNullAndExpiredDateAfter(Mockito.anyString(), Mockito.anyLong(), Mockito.any(OffsetDateTime.class));
        Mockito.verify(chosenOfferRepository, Mockito.never()).save(Mockito.any(ChosenOffer.class));

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }




}
