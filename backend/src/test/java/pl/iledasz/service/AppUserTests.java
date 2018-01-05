package pl.iledasz.service;


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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.iledasz.Application;
import pl.iledasz.entities.AppUser;
import pl.iledasz.entities.Role;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.RoleRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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


    private MockMvc mockMvc;

    static String name = "Wiktor";
    static String surname = "Korol";
    static String login = "appleIsBullshit";
    static String email = "wkorol@ssh.com";
    static String phone = "345234234";
    static String password = "ILoveMacDonald";


    @Test
    public void testRegistrationProcess() throws Exception {

        mockMvc = webAppContextSetup(webApplicationContext).build();

        AppUser appUser = new AppUser();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/registration")
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr("userForm", new AppUser())
                .param("name",name)
                .param("surname",surname)
                .param("phone_number",phone)
                .param("login",login)
                .param("password",password)
                .param("email", email);

        //Don't add new user to database
        Mockito.when(appUserRepository.save(Mockito.any(AppUser.class))).thenReturn(null);

        //Don't use role from database
        Mockito.when(roleRepository.findOne(Mockito.anyLong())).thenReturn(new Role( (long) 1 , "appuser"));

        //Assume as this login doesn't exist in database.
        Mockito.when(appUserRepository.findByLogin(Mockito.any(String.class))).thenReturn(null);

        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);

        MvcResult mvcResult =  this.mockMvc.perform(requestBuilder).andReturn();

        Mockito.verify(appUserRepository).save(argumentCaptor.capture());

        AppUser createdUser = argumentCaptor.getValue();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(response.getContentAsString(),"Nowy użytkownik został poprawnie dodany, możesz się teraz zalogować");
        assertEquals(response.getStatus(), HttpStatus.OK.value());

        //verify received user detail

        assertEquals(createdUser.getLogin(), login);
        assertEquals(createdUser.getName(), name);
        assertEquals(createdUser.getSurname(), surname);
        assertEquals(createdUser.getEmail(),email);
        assertEquals(createdUser.getPhone_number(), phone);
        assertTrue(bCryptPasswordEncoder.matches(password,createdUser.getPassword()));
        assertEquals(createdUser.isEnable(), true);

    }
}
