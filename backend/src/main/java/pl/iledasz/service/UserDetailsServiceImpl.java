package pl.iledasz.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.AppUser;
import pl.iledasz.repository.AppUserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {


        AppUser appUser = appUserRepository.findOneByLogin(login);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        grantedAuthorities.add(new SimpleGrantedAuthority(appUser.getRole().getRole()));

        return new org.springframework.security.core.userdetails.User(appUser.getLogin(), appUser.getPassword(), grantedAuthorities);
    }
}
