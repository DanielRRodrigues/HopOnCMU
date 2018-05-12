package com.example.danif.hoponcmu.DataObejcts;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by danif on 4/26/2018.
 */

public class Quizz implements Serializable{
    private ArrayList<Question> _questions;
    private String _title;

    public static ArrayList<Quizz> quizzes = new ArrayList<Quizz>();

    public Quizz(String title, ArrayList<Question> questions) {
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
