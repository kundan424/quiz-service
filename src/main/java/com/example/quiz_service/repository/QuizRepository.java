package com.example.quiz_service.repository;

import com.example.quiz_service.entity.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz,Integer> {
}
