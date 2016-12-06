package edu.ilstu.bluetoothpopquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeacherQuizViewResultsActivity extends AppCompatActivity {

    private MyCustomAdapter dataAdapter;
    private List<Answer> answerList;
    private ListView answerListView;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_quiz_view_results);
        createMockAnswerList();
        init();
    }

    private void createMockAnswerList(){
        answerList = new ArrayList<Answer>();
        MultipleChoiceAnswer answer;

        for(int i = 0; i < 10; i++){
            answer  = new MultipleChoiceAnswer();
            answer.setQuestion(new MultipleChoiceQuestion("Question" + i, "Answer1", "Answer2", "Answer3", "Answer4"));
            answer.setAnswer(0);
            answerList.add(answer);
        }

        WrittenAnswer writtenAnswer = new WrittenAnswer();
        writtenAnswer.setQuestion(new WrittenQuestion("WrittenQuestion1"));
        writtenAnswer.setAnswer("AnswerQuestion");
        answerList.add(writtenAnswer);
    }

    private void init(){
        doneButton = (Button) findViewById(R.id.answer_view_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomePage();
            }
        });
        answerListView = (ListView) findViewById(R.id.answer_list_view);
        displayListView();
    }

    private void displayListView() {
        dataAdapter = new MyCustomAdapter(this,
                R.layout.answer_list_layout, answerList);
        answerListView.setAdapter(dataAdapter);
        answerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO
            }
        });
    }
    private class MyCustomAdapter extends ArrayAdapter<Answer> {

        private List<Answer> answerList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               List<Answer> answerList) {
            super(context, textViewResourceId, answerList);
            this.answerList = new ArrayList<Answer>();
            this.answerList.addAll(answerList);
        }

        private class ViewHolder {
            TextView questionValue;
            TextView answerValue;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            final Integer posObject = position;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.answer_list_layout, null);

                holder = new MyCustomAdapter.ViewHolder();
                holder.questionValue = (TextView) convertView.findViewById(R.id.answer_question_value);
                holder.answerValue= (TextView) convertView.findViewById(R.id.answer_answer_value);
                convertView.setTag(holder);


            } else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
            }


            Answer answer = answerList.get(position);

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


            holder.questionValue.setText(answer.getQuestion().getQuestion());
            if(answer instanceof MultipleChoiceAnswer){
                MultipleChoiceAnswer multipleChoiceAnswer = (MultipleChoiceAnswer) answer;
                holder.answerValue.setText(multipleChoiceAnswer.getQuestion().getAnswers().get(multipleChoiceAnswer.getAnswer()));
            } else if (answer instanceof WrittenAnswer) {
                WrittenAnswer writtenAnswer = (WrittenAnswer) answer;
                holder.answerValue.setText(writtenAnswer.getAnswer());
            } else {
                holder.answerValue.setText("");
            }
            return convertView;

        }

    }

    private void goToHomePage(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
