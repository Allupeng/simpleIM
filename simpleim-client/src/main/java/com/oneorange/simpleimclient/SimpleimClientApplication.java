package com.oneorange.simpleimclient;

import com.oneorange.simpleimclient.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleimClientApplication implements CommandLineRunner {

    @Autowired
    private Client client;

    public static void main(String[] args) {
        SpringApplication.run(SimpleimClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        client.run();
    }
}
