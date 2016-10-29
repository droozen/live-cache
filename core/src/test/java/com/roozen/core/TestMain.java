package com.roozen.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.roozen.core"})
public class TestMain {

    public static void main(final String[] args) throws Exception {
        final SpringApplication app = new SpringApplication(TestMain.class);
        app.run(args);
    }
}
