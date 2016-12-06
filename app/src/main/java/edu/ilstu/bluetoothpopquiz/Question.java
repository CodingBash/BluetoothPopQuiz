package edu.ilstu.bluetoothpopquiz;

import java.io.Serializable;

/**
 * Created by bbece on 11/29/2016.
 */

public abstract class Question implements Serializable{
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
