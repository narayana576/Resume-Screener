package com.resumescreener.backend.repository;

import com.resumescreener.backend.model.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends MongoRepository<Resume, String> {
    // We can add custom query methods later if needed
}