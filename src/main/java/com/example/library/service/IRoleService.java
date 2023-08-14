package com.example.library.service;

import com.example.library.model.Role;

import java.util.List;

/**
 * The {@code IRoleService} interface defines methods for managing role-related operations
 * within the library management system.
 */
public interface IRoleService {

    /**
     * This method finds the role by name
     * @param name name of the role
     * @return List of Roles class type
     */
    List<Role> findRoleByName(String name);

    /**
     * Finds all roles with the specified name.
     *
     * @param name The name of the roles to find.
     * @return A list of Role objects with the specified name.
     */
    List<Role> findAllRoleByName(String name);
}
