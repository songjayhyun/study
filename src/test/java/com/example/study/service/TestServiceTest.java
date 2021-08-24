package com.example.study.service;

import com.example.study.respository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {


    @Autowired
    private TestService testService;


    @Test
    void getTest() {
        Assertions.assertEquals(testService.getTest(1L).getId(), 1L);
    }



}