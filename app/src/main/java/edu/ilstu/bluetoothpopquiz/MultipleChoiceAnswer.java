package edu.ilstu.bluetoothpopquiz;

/**
 * Created by bbece on 12/3/2016.
 */

public class MultipleChoiceAnswer extends Answer {
    private Integer answer;

    @Override
    public void setQuestion(Question question){
        this.question = (MultipleChoiceQuestion) question;
    }

    @Override
    public MultipleChoiceQuestion getQuestion(){
        return (MultipleChoiceQuestion) this.question;
    }

    public void setAnswer(Integer answer){
        this.answer = answer;
    }
    public Integer getAnswer(){
        return this.answer;
    }
}
