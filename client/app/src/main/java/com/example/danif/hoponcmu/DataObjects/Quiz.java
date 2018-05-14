package com.example.danif.hoponcmu.DataObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by danif on 4/26/2018.
 */

public class Quiz implements Serializable{
    private ArrayList<Question> _questions;
    private String _title;

    public static ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

    public Quiz(String title, ArrayList<Question> questions) {
        _questions = questions;
        _title = title;

        quizzes.add(this);
    }

    public ArrayList<Question> getQuestions() {
        return _questions;
    }

    public String getTitle() {
        return _title;
    }
}
