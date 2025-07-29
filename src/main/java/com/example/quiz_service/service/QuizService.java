package com.example.quiz_service.service;


import com.example.quiz_service.entity.QuestionWrapper;
import com.example.quiz_service.entity.Quiz;
import com.example.quiz_service.entity.Response;
import com.example.quiz_service.feign.QuizInterface;
import com.example.quiz_service.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository; // repository for quiz

    @Autowired
    private QuizInterface quizInterface;
    /**
     * Generates a new quiz with random questions from a specified category.
     *
     * @param category The category from which to select questions.
     * @param numQ     The desired number of questions in the quiz.
     * @return A List of Question objects for the generated quiz.
     * Returns an empty list if not enough questions are available or if the category is invalid.
     */

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    public ResponseEntity<String> createQuiz(
            String category,
            int numQ,
            String title
    ) {
        try {
            // Use Feign Client directly to get random question IDs
            List<Integer> QuestionIds = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

            Quiz quiz = new Quiz();
            quiz.setId(generateUniqueQuizId());
            quiz.setTitle(title);
            quiz.setQuestionIds(QuestionIds);

            quizRepository.save(quiz);
            logger.info("Quiz created successfully with ID: {} for category: {}", quiz.getId(), category);
            return new ResponseEntity<>("success", HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("An error occurred while creating quiz for category {} using Feign: {}", category, e.getMessage(), e);
            return new ResponseEntity<>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Integer generateUniqueQuizId() {
        // Placeholder: In a production app, use a proper ID generation strategy
        // (e.g., a counter, UUIDs, or let Spring Data MongoDB handle ObjectId if you change Quiz ID type)
        return (int) (System.currentTimeMillis() % 1_000_000_000);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        try {
            Optional<Quiz> quizOptional = quizRepository.findById(id);

            if (quizOptional.isPresent()) {
                Quiz quiz = quizOptional.get();
                List<Integer> questionIds = quiz.getQuestionIds();

                // Use Feign Client to get question details
                ResponseEntity<List<QuestionWrapper>> responseEntity = quizInterface.getQuestionsFromId(questionIds);

                if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                    logger.info("Fetched {} questions for quiz ID: {} using Feign.", responseEntity.getBody().size(), id);
                    return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
                } else {
                    logger.warn("Failed to retrieve question details from question-service for quiz ID {} using Feign. Status: {}", id, responseEntity.getStatusCode());
                    return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
                }
            } else {
                logger.warn("Quiz with id {} not found.", id);
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("An error occurred while fetching quiz questions for ID {} using Feign: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> submitQuiz(Integer id, List<Response> responses) {
        try {
            // Use Feign Client to calculate score
            ResponseEntity<Integer> score = quizInterface.getScore(responses);
            return score;
        } catch (Exception e) {
            logger.error("An error occurred while submitting quiz ID {} using Feign: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}