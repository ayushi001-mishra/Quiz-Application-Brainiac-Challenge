package com.quizapp.controller;

import com.quizapp.model.QuestionWrapper;
import com.quizapp.model.Response;
import com.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam Integer numQ, @RequestParam Integer id, @RequestParam String title){
        return quizService.createQuiz(category, numQ, id, title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer id){
        return quizService.getQuiz(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> getScore(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.getScore(id, responses);
    }
}
