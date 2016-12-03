package edu.ilstu.bluetoothpopquiz;

/**
 * Created by bbece on 12/3/2016.
 */

public abstract class Answer {
    protected Question question;

    public abstract void setQuestion(Question question);

    public abstract Question getQuestion();
}
