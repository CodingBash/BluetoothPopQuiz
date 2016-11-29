package edu.ilstu.bluetoothpopquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TeacherQuizCreationActivity extends AppCompatActivity {

    private TextView countText;
    private Button sendButton;
    // TODO: List View implementation - http://www.mysamplecode.com/2012/07/android-listview-checkbox-example.html
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
        customQuestion = (EditText) findViewById(R.id.custom_question_field);
        addCustomQuestionCheck = (CheckBox) findViewById(R.id.custom_question_check);
    }
}
