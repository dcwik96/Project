package pl.iledasz.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.DTO.AppUserDTO;
import pl.iledasz.repository.AppUserRepository;


@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUserDTO getUser(Long id)
    {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(appUserRepository.findOne(id),AppUserDTO.class);
    }



}
