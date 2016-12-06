package edu.ilstu.bluetoothpopquiz;

import java.io.Serializable;

/**
 * Created by bbece on 12/3/2016.
 */

public abstract class Answer implements Serializable {
    protected Question question;

    public abstract void setQuestion(Question question);

    public abstract Question getQuestion();
}
