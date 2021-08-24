package com.example.study.component;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Slf4j
@Component
public class ServiceManager {

    @Value("${server.port}")
    private Integer serverPort;

    @PostConstruct
    public void postConstruct() {
        log.info("ServerManager printed");
    }


    public Integer getPort() {
        return 8082;
    }
}
