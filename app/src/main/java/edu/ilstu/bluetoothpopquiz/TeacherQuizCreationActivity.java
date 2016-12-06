package edu.ilstu.bluetoothpopquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherQuizCreationActivity extends AppCompatActivity {

    private TextView countText;
    private Button sendButton;
    private ListView listView;
    private EditText customQuestion;
    private CheckBox addCustomQuestionCheck;
    private MyCustomAdapter dataAdapter;
    private Map<Integer, Question> sentQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_quiz_creation);
        init();
    }

    private void init() {
        sentQuestions = new HashMap<Integer, Question>();
        countText = (TextView) findViewById(R.id.count_text_number);
        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send logic. Must send the 'sentQuestions' to the connected device
                // TODO: Temporary
                startTeacherQuizCiewResultsActivity();
            }
        });
        listView = (ListView) findViewById(R.id.question_list_view);
        displayListView();
        addCustomQuestionCheck = (CheckBox) findViewById(R.id.custom_question_check);
        addCustomQuestionCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sentQuestions.put(0, new WrittenQuestion(countText.getText().toString()));
                    countText.setText(String.valueOf(Integer.parseInt(countText.getText().toString()) + 1));
                } else {
                    sentQuestions.remove(0);
                    countText.setText(String.valueOf(Integer.parseInt(countText.getText().toString()) - 1));
                }
            }
        });
        customQuestion = (EditText) findViewById(R.id.custom_question_field);
        customQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")) {
                    addCustomQuestionCheck.setChecked(true);
                } else {
                    addCustomQuestionCheck.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void displayListView() {
        List<Question> questionList = new ArrayList<Question>();
        Question question;
        for (int i = 0; i < 10; i++) {
            question = new MultipleChoiceQuestion("Question" + i, "Answer1", "Answer2", "Answer3", "Answer4");
            questionList.add(question);
        }
        dataAdapter = new MyCustomAdapter(this,
                R.layout.quiz_list_layout, questionList);
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO
            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<Question> {

        private List<Question> questionList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               List<Question> questionList) {
            super(context, textViewResourceId, questionList);
            this.questionList = new ArrayList<Question>();
            this.questionList.addAll(questionList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            final Integer posObject = position;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.quiz_list_layout, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.answer_list_text);
                holder.name = (CheckBox) convertView.findViewById(R.id.question_list_check);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        MultipleChoiceQuestion question = (MultipleChoiceQuestion) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + (cb.getText().toString().length() > 7 ? cb.getText().toString().substring(0, 7) + "..." : cb.getText().toString()) +
                                        " is " + (cb.isChecked() ? "added" : "removed"),
                                Toast.LENGTH_LONG).show();
                        question.setSelected(cb.isChecked());


                        if (cb.isChecked()) {
                            // Offset by one to allow the custom question index in the map.
                            sentQuestions.put(posObject.intValue() + 1, question);
                            countText.setText(String.valueOf(Integer.parseInt(countText.getText().toString()) + 1));
                        } else {
                            sentQuestions.remove(posObject.intValue() + 1);
                            countText.setText(String.valueOf(Integer.parseInt(countText.getText().toString()) - 1));
                        }
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            Question question = questionList.get(position);

            /*
            if(question instanceof MultipleChoiceQuestion){
                List<String> answerList = ((MultipleChoiceQuestion) question).getAnswers();
                StringBuilder builder = new StringBuilder();
                int counter = 0;
                char letter = 'A';
                while(counter < answerList.size()){
                    builder.append(" " + letter + ": " + answerList.get(counter));
                    letter++;
                    counter++;
                }
                int codeTextLength = 100 - question.getQuestion().length();
                codeTextLength = (codeTextLength < 0) ? 0 : codeTextLength;
                String codeText = builder.toString().substring(0, codeTextLength) + "...";
                holder.code.setText(codeText);
            } else {
                holder.code.setText("No answer available");
            }
            */
            holder.name.setText(question.getQuestion());
            holder.name.setChecked(question.isSelected());
            holder.name.setTag(question);

            return convertView;

        }

    }

    private void checkButtonClick() {
        Button myButton = (Button) findViewById(R.id.send_button);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                List<Question> questionList = dataAdapter.questionList;
                for (int i = 0; i < questionList.size(); i++) {
                    Question country = questionList.get(i);
                    if (country.isSelected()) {
                        responseText.append("\n" + country.getQuestion());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

    private void startTeacherQuizCiewResultsActivity(){
        Intent i = new Intent(this, TeacherQuizViewResultsActivity.class);
        startActivity(i);
    }
}
