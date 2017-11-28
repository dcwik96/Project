package pl.iledasz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        logger.info(String.format("Username: %s", login));

        AppUser appUser = appUserRepository.findByLogin(login);

       if (appUser == null)
           throw new UsernameNotFoundException(login);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        logger.info(String.format("role: %s", appUser.getRole().getRole()));
        grantedAuthorities.add(new SimpleGrantedAuthority(appUser.getRole().getRole()));

        return new User(appUser.getLogin(), appUser.getPassword(), grantedAuthorities);
    }
}
