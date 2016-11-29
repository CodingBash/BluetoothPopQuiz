package edu.ilstu.bluetoothpopquiz;

/**
 * Created by bbece on 11/29/2016.
 */

public class WrittenQuestion implements Question {
    private String question;

    public WrittenQuestion(String question){
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
