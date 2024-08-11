package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class UserSetup implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public void run(String... args) throws Exception {
        createRoleIfDontExist(ERole.USER,1L);
        createRoleIfDontExist(ERole.ADMIN,2L);
        createFirstUserIfDontExist();


    }

    private void createRoleIfDontExist(ERole eRole,Long roleId) {
        if(!roleRepository.existsById(roleId)) {
            Role role = new Role();
            role.setId(roleId);
            role.setName(eRole);
            roleRepository.save(role);
            System.out.println("Role Created with id " + roleId + " and name " + eRole);
        }
    }

    private void createFirstUserIfDontExist() {
        if(userRepository.count() == 0) {
            Role role = roleRepository.findByName(ERole.ADMIN).orElseThrow(() -> new RuntimeException("Role Not Found"));
            User admin = new User();
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setRoles(roles);
            userRepository.save(admin);
            System.out.println("First user created! Login: admin Password: admin");

        }


    }
}
