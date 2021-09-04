package com.example.study.service;

import com.example.study.model.entity.User;
import com.example.study.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByIdentification(username);

        if(user == null) {
            return null;
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getIdentification())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
