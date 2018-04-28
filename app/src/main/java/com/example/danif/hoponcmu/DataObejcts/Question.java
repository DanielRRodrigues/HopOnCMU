package com.example.danif.hoponcmu.DataObejcts;

import java.util.ArrayList;

/**
 * Created by danif on 4/26/2018.
 */

public class Question {
    private String _question;
    private ArrayList<String> _options;

    public Question(String question, ArrayList<String> options) {
        _question = question;
        _options = options;
    }

    public String getQuestion() {
        return _question;
    }
    public ArrayList<String> getOptions() {
        return _options;
    }
}
