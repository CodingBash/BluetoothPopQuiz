package edu.ilstu.bluetoothpopquiz;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bbece on 11/29/2016.
 */

public class MultipleChoiceQuestion implements Question{
    private String question;
    private List<String> answers;

    public MultipleChoiceQuestion(String... inputs){
        if(inputs.length > 0){
            question = inputs[0];
            if(inputs.length > 1){
                answers = Arrays.asList(Arrays.copyOfRange(inputs, 1, inputs.length));
            }
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
