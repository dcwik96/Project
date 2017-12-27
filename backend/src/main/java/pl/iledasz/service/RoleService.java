package pl.iledasz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.iledasz.entities.Role;
import pl.iledasz.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> list() {
        return roleRepository.findAll();
    }

    public Role getUserRole(long id) {
        return roleRepository.findOne(id);
    }
}
