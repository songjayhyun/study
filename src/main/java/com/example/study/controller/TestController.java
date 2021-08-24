package com.example.study.controller;

import com.example.study.component.ServiceManager;
import com.example.study.model.res.Res;
import com.example.study.service.TestService;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ServiceManager serverManager;
    private final TestService testService;

//    @GetMapping("/test")
//    public Res getTest() {
//        final Res res = new Res();
//        final String message = res.getMessage();
//        return res;
//    }

    @GetMapping("/test")
    public Map getTest() {
        return Map.of("serverPort", serverManager.getPort());
    }

    @GetMapping("/test/{id}")
    public Res getTestById(
            @PathVariable Long id
    ) {
        return new Res(
                testService.getTest(id)
        );
    }
}
