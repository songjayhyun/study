package com.example.study.service;

import com.example.study.req.RGithub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        final ResponseEntity<List<RGithub.ListGetRepo>> res = restTemplate.exchange(
                "https://api.github.com/orgs/google/repos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RGithub.ListGetRepo>>(){});

        System.out.println("Status Code : " + res.getStatusCode());
        for(final RGithub.ListGetRepo repo : res.getBody()) {
            System.out.println(repo.getId() + " / " + repo.getName());
        }
    }

//    @Test
//    void contextLoads2() {
//        List<RGithub.ListGetRepo> list = githubWebClient.get()
//                .uri("/orgs/google/repos")
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<RGithub.ListGetRepo>>() {})
//                .block();
//        for(final RGithub.ListGetRepo repo : list) {
//            System.out.println(repo.getId() + " / " + repo.getName());
//        }
//    }

}