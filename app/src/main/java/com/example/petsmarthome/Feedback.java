package com.example.petsmarthome;

public class Feedback {
   public float question_1;
    public float question_2;
    public String suggestion;

    public Feedback() {
    }

    public Feedback(float question_1, float question_2, String suggestion) {
        this.question_1 = question_1;
        this.question_2 = question_2;
        this.suggestion = suggestion;
    }

    public float getQuestion_1() {
        return question_1;
    }

    public void setQuestion_1(float question_1) {
        this.question_1 = question_1;
    }

    public float getQuestion_2() {
        return question_2;
    }

    public void setQuestion_2(float question_2) {
        this.question_2 = question_2;
    }



    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
