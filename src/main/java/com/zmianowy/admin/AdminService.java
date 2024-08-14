package com.zmianowy.admin;


import com.zmianowy.ShiftRaport.ShiftRepository;
import com.zmianowy.security.UserRepository;
import com.zmianowy.security.ERole;
import com.zmianowy.security.Role;
import com.zmianowy.security.RoleRepository;
import com.zmianowy.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    protected String passwordGenerator(int length){
        final String LCASE = "abcdefghijklmnopqrstuvwxyz";
        final String UCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIG = "0123456789";
        final String ALLOWED_CHARS = LCASE + UCASE + DIG;
        final SecureRandom RANDOM = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char randomChar = ALLOWED_CHARS.charAt(RANDOM.nextInt(ALLOWED_CHARS.length()));
            password.append(randomChar);
        }
        return password.toString();
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
    public  List<Object[]> listCreatedPosts() {
        return shiftRepository.getAuthorStatistics();
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public boolean isUserExist(Long id){
        return userRepository.findById(id).isPresent();
    }
    public Optional<User> getUserData(Long id){
        return userRepository.findById(id);
    }
    public String changePassword(Long id){
        if (isUserExist(id)) {
            User user = getUserData(id).get();
            String newPassword = passwordGenerator(10);
            String newPasswordEncoded = bCryptPasswordEncoder.encode(newPassword);
            user.setPassword(newPasswordEncoded);
            userRepository.save(user);
            return newPassword;
        }else throw new RuntimeException("Brak takiego użytkownika");
    }
    public String addUser(String login, String password, ERole role) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        boolean UserExist = userRepository.findByUsername(login).isPresent();
        if (UserExist) return "Użytkownik już istnieje";
        User user = new User();
        user.setUsername(login);
        user.setPassword(encodedPassword);
        Optional<Role> roleFromDB = roleRepository.findByName(role);
        if (roleFromDB.isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleFromDB.get());
            user.setRoles(roles);
        } else {
            return "Nie znaleźono takiej roli";
        }
        userRepository.save(user);
        return user.getUsername();
    }
}
