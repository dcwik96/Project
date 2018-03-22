package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.iledasz.DTO.AppUserDTO;
import pl.iledasz.entities.AppUser;
import pl.iledasz.repository.AppUserRepository;
import pl.iledasz.repository.RoleRepository;

import java.security.Principal;
import java.util.List;


@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    public AppUserDTO getUser(Long id) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(appUserRepository.findOne(id), AppUserDTO.class);
    }

    public void save(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findOne(2L));
        user.setEnable(true);
        appUserRepository.save(user);
    }

    public AppUser findByLogin(String login) {
        return appUserRepository.findByLogin(login);
    }


    public void modify(AppUser userForm, Principal principal) {
        AppUser loggedUser = appUserRepository.findByLogin(principal.getName());

        loggedUser.setName(userForm.getName());
        loggedUser.setSurname(userForm.getSurname());
        loggedUser.setEmail(userForm.getEmail());
        loggedUser.setPhone_number(userForm.getPhone_number());
        loggedUser.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        loggedUser.setEnable(true);

        appUserRepository.save(loggedUser);
    }
}
