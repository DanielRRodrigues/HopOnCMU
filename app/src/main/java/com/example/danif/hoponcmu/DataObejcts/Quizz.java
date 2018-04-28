package com.example.danif.hoponcmu.DataObejcts;

import java.util.ArrayList;

/**
 * Created by danif on 4/26/2018.
 */

public class Quizz {
    private ArrayList<Question> _questions;

    private Quizz(ArrayList<Question> questions) {
        _questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return _questions;
    }
}
