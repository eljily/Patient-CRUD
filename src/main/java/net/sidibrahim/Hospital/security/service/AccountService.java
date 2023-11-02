package net.sidibrahim.Hospital.security.service;

import net.sidibrahim.Hospital.security.entities.AppRole;
import net.sidibrahim.Hospital.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username,String password,String email,String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);
    AppUser loadUserByUserName(String username);

}
