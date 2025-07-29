package com.example.quiz_service.controller;
import com.example.quiz_service.entity.QuestionWrapper;
import com.example.quiz_service.entity.Response;
import com.example.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(
         @RequestParam String category,
         @RequestParam Integer numQ,
         @RequestParam String title
    ) {
      return quizService.createQuiz(category, numQ , title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion (@PathVariable Integer id){
        return quizService.getQuizQuestion(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz (
            @PathVariable Integer id ,
            @RequestBody List<Response> responses
    ){
    return quizService.submitQuiz(id, responses);
    }

}
