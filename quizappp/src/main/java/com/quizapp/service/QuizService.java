package com.quizapp.service;

import com.quizapp.dao.QuestionRepository;
import com.quizapp.dao.QuizRepository;
import com.quizapp.model.Question;
import com.quizapp.model.QuestionWrapper;
import com.quizapp.model.Quiz;
import com.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuizRepository quizRepository;


    public ResponseEntity<String> createQuiz(String category, Integer numQ, Integer id, String title) {

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);

        List<Question> questionFromDB = quiz.get().getQuestions();

        List<QuestionWrapper>  questionForUser = new ArrayList<>();
        for(Question q: questionFromDB){
            QuestionWrapper quest = new QuestionWrapper(q.getId(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4(), q.getQuestionTitle());
            questionForUser.add(quest);
        }

        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(Integer id, List<Response> responses) {
        Optional<Quiz> quiz = quizRepository.findById(id);

        List<Question> quizQuestion = quiz.get().getQuestions();

        int count=0;
        int i=0;
        for(Response r: responses){
            if(r.getResponse().equals(quizQuestion.get(i).getRightAnswer()))
                count++;
            i++;
        }

        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
