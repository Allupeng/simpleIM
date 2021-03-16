package com.orange.simpleimserver;

import com.orange.simpleimserver.server.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class SimpleimServerApplication implements CommandLineRunner {

    @Autowired
    private Server server;

    public static void main(String[] args) {
        SpringApplication.run(SimpleimServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        server.run();
    }
}
