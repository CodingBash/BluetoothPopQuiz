package edu.ilstu.bluetoothpopquiz;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bbece on 11/29/2016.
 */

public class MultipleChoiceQuestion extends Question{
    private List<String> answers;

    public MultipleChoiceQuestion(String... inputs){
        if(inputs.length > 0){
            super.setQuestion(inputs[0]);
            if(inputs.length > 1){
                answers = Arrays.asList(Arrays.copyOfRange(inputs, 1, inputs.length));
            }
        }
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
