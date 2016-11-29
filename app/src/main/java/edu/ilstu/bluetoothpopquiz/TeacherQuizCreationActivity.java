package edu.ilstu.bluetoothpopquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeacherQuizCreationActivity extends AppCompatActivity {

    private TextView countText;
    private Button sendButton;
    // TODO: List View w/ Checkbox implementation - http://www.mysamplecode.com/2012/07/android-listview-checkbox-example.html
    private ListView listView;
    private EditText customQuestion;
    private CheckBox addCustomQuestionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_quiz_creation);
        init();
    }

    private void init(){
        countText = (TextView) findViewById(R.id.count_text);
        sendButton = (Button) findViewById(R.id.send_button);
        listView = (ListView) findViewById(R.id.question_list_view);
        displayListView();
        customQuestion = (EditText) findViewById(R.id.custom_question_field);
        addCustomQuestionCheck = (CheckBox) findViewById(R.id.custom_question_check);
    }

    private void displayListView(){
        List<Question> questionList = new ArrayList<Question>();
        Question question;
        for(int i = 0; i < 10; i++){
            question = new MultipleChoiceQuestion("Question" + i, "Answer1", "Answer2", "Answer3", "Answer4");
            questionList.add(question);
        }
        

    }
}
