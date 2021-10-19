package com.hgs.caipiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class CaipiaoApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(CaipiaoApplication.class, args);
    }

}
