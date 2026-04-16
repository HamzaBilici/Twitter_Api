package com.hbilici.twitter_api.service;

import com.hbilici.twitter_api.config.SecurityConfig;
import com.hbilici.twitter_api.dto.response.UserResponse;
import com.hbilici.twitter_api.entity.Role;
import com.hbilici.twitter_api.entity.User;
import com.hbilici.twitter_api.exception.BadRequestException;
import com.hbilici.twitter_api.exception.NotFoundException;
import com.hbilici.twitter_api.repository.RoleRepository;
import com.hbilici.twitter_api.repository.UserRepository;
import com.hbilici.twitter_api.util.UserMapper;
import lombok.AllArgsConstructor;
import org.apache.catalina.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import javax.management.remote.JMXAuthenticator;
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

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Override
    public UserResponse register(String firstName, String lastName, String email, String password) {
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
        User savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);


    }


    @Override
    public UserResponse login(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("Kullanıcı bulunamadı: " + email));

            return UserMapper.toUserResponse(user);

        } catch (BadCredentialsException e) {
            throw new BadRequestException("E-posta veya şifre hatalı!");
        }
    }
}
