package com.resumescreener.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    private TestDocumentRepository testRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        TestDocument doc = new TestDocument("Hello from Spring Boot to MongoDB!");
        testRepository.save(doc);
        System.out.println("✅ Saved test document with ID: " + doc.getId());
    }
}