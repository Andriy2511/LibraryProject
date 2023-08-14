package com.example.library.service;

import com.example.library.model.Role;
import com.example.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * This method finds the role by name
     * @param name name of the role
     * @return List of Roles class type
     */
    @Override
    public List<Role> findRoleByName(String name){
        return Collections.singletonList(roleRepository.findByName(name).get());
    }

    @Override
    public List<Role> findAllRoleByName(String name){
        return roleRepository.findAllByName(name);
    }
}
