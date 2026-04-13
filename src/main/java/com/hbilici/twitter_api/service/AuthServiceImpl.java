package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.entity.Role;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.repository.RoleRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    public User register(String firstName, String lastName, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with this email already exists!");
        }

        String encodedPassword = passwordEncoder.encode(password);

        Role userRole = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found!"));


        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setAuthorities(roles);

        return userRepository.save(user);


    }
}
