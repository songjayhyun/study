package com.example.study.respository;

import com.example.study.model.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("테스트")
    void insertTest() {

        final User user = User.builder()
                .name("sjh")
                .identification("test")
                .password(passwordEncoder.encode("test"))
                .build();

        User save = userRepository.save(user);

        User byIdentification = userRepository.findByIdentification(save.getIdentification());

        Assertions.assertThat(byIdentification.getId()).isEqualTo(save.getId());

    }
}