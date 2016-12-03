package edu.ilstu.bluetoothpopquiz;

/**
 * Created by bbece on 12/3/2016.
 */

public class WrittenAnswer extends Answer {
    private String answer;

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getAnswer(){
        return this.answer;
    }

    @Override
    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public Question getQuestion() {
        return this.question;
    }
}
