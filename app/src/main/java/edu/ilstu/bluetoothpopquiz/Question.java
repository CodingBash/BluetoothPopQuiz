package edu.ilstu.bluetoothpopquiz;

/**
 * Created by bbece on 11/29/2016.
 */

public class Question {
    private boolean selected;
    private String question;

    public Question(){

    }

    public Question(String question){
        this.question = question;
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
