package com.example.study.service;

import com.example.study.model.entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    public Test getTest(final Long id) {
        return Test.builder()
                .id(id)
                .name("sjh")
                .build();
    }
}
