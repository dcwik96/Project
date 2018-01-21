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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import pl.iledasz.Application;
import pl.iledasz.DTO.AppUserDTO;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Role;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.RoleRepository;

import javax.servlet.Filter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AppUserTests {

    @MockBean
    AppUserRepository appUserRepository;

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    static String name = "Wiktor";
    static String surname = "Korol";
    static String login = "appleIsBullshit";
    static String weakLogin ="sd";
    static String email = "wkorol@ssh.com";
    static String phone = "345234234";
    static String password = "ILoveMacDonald";
    static String weakPassword = "KfcKing";
    static String incorrectEmail = "asad.321#ypu.pl";

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
        //Don't add new user to database
        Mockito.when(appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(null);

        //Don't use role from database
        Mockito.when(roleRepository.findOne(Mockito.anyLong())).thenReturn(new Role((long) 1, "appuser"));

    }


    @Test
    public void testCorrectRegistrationProcess() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("login", login)
                        .param("password", password)
                        .param("email", email);

        //Assume as this login doesn't exist in database.
        Mockito.when(appUserRepository.findByLogin(Mockito.any(String.class))).thenReturn(null);

        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        Mockito.verify(appUserRepository).save(argumentCaptor.capture());

        AppUser createdUser = argumentCaptor.getValue();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(),response.getStatus());

        //verify received user detail
        assertEquals(createdUser.getLogin(), login);
        assertEquals(createdUser.getName(), name);
        assertEquals(createdUser.getSurname(), surname);
        assertEquals(createdUser.getEmail(), email);
        assertEquals(createdUser.getPhone_number(), phone);
        assertTrue(bCryptPasswordEncoder.matches(password, createdUser.getPassword()));
        assertEquals(createdUser.isEnable(), true);
    }

    @Test
    public void testRegistrationWithoutName() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("login", login)
                        .param("password", password)
                        .param("email", email);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWithoutSurname() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("phone_number", phone)
                        .param("login", login)
                        .param("password", password)
                        .param("email", email);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWithoutPhone() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("login", login)
                        .param("password", password)
                        .param("email", email);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWithoutLogin() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("password", password)
                        .param("email", email);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWithoutEmail() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("login", login)
                        .param("password", password);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWithoutPassword() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("login", login)
                        .param("email", email);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWithWeakPassword() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("login", login)
                        .param("password", weakPassword)
                        .param("email", email);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWithIncorrectEmail() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("login", login)
                        .param("password", password)
                        .param("email", incorrectEmail);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWithWeakLogin() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("login", weakLogin)
                        .param("password", password)
                        .param("email", email);

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testRegistrationWhenLoginIsEngaged() throws Exception {

        RequestBuilder requestBuilder =
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("surname", surname)
                        .param("phone_number", phone)
                        .param("login", login)
                        .param("password", password)
                        .param("email", email);

        //Simulate that login is engaged
        Mockito.when(appUserRepository.findByLogin(login)).thenReturn(new AppUser());

        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),response.getStatus());
    }

    @Test
    public void testLogin() throws Exception {
        RequestBuilder requestBuilder =
                post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", login)
                        .param("password", password);

        ObjectMapper objectMapper = new ObjectMapper();

        AppUser appUser = new AppUser();
        appUser.setLogin(login);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUser.setName(name);
        appUser.setEmail(email);
        appUser.setSurname(surname);
        appUser.setPhone_number(phone);
        appUser.setEnable(true);
        appUser.setRole(roleRepository.findOne((long) 0));

        //Return user for login test
        Mockito.when(appUserRepository.findByLogin(login)).thenReturn(appUser);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        AppUserDTO receivedAppUser = objectMapper.readValue(response.getContentAsString(), AppUserDTO.class);


        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(receivedAppUser.getLogin(), login);
        assertEquals(receivedAppUser.getSurname(), surname);
        assertEquals(receivedAppUser.getPhone_number(), phone);
        assertEquals(receivedAppUser.getEmail(), email);
        assertEquals(receivedAppUser.getName(), name);

    }

    @Test
    public void testBadLogin() throws Exception {
        RequestBuilder requestBuilder =
                post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", login)
                        .param("password", password);

        Mockito.when(appUserRepository.findByLogin(login)).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();


        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());

    }

    @Test
    public void testLoginWithWrongPassword() throws Exception {
        RequestBuilder requestBuilder =
                post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", login)
                        .param("password", password);

        AppUser appUser = new AppUser();
        appUser.setLogin(login);
        appUser.setPassword(bCryptPasswordEncoder.encode(password + weakPassword));
        appUser.setRole(roleRepository.findOne(2l));

        Mockito.when(appUserRepository.findByLogin(login)).thenReturn(appUser);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());

    }


}
