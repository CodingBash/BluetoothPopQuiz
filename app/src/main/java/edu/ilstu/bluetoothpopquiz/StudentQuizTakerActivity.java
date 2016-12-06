package edu.ilstu.bluetoothpopquiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentQuizTakerActivity extends AppCompatActivity {
    private MultipleChoiceAdapter multipleChoiceAdapter;
    private WrittenAdapter writtenAdapter;
    private List<Question> questionList;
    private ListView multipleChoiceListView;
    private ListView writtenListView;
    private Button submitButton;

    private List<MultipleChoiceQuestion> multipleChoiceQuestions;
    private List<WrittenQuestion> writtenQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiz_taker);
        createMockQuestionList();
        separateQuestionList();
        init();
    }

    private void createMockQuestionList(){
        questionList = new ArrayList<Question>();
        Question question;
        for (int i = 0; i < 10; i++) {
            question = new MultipleChoiceQuestion("MultipleChoiceQuestion" + i, "Answer1", "Answer2", "Answer3", "Answer4");
            questionList.add(question);
        }

        for (int i = 0; i < 10; i++) {
            question = new WrittenQuestion("WrittenQuestion" + i);
            questionList.add(question);
        }
    }

    private void separateQuestionList(){
        multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestion>();
        writtenQuestions = new ArrayList<WrittenQuestion>();
        for(Question question : questionList){
            if(question instanceof MultipleChoiceQuestion){
                multipleChoiceQuestions.add((MultipleChoiceQuestion) question);
            } else {
                writtenQuestions.add((WrittenQuestion) question);
            }
        }
    }

    private void init(){
        multipleChoiceListView = (ListView) findViewById(R.id.student_quiz_multiplechoice_list_view);
        writtenListView = (ListView) findViewById(R.id.student_quiz_written_list_view);
        displayListView();
        ListUtils.setDynamicHeight(multipleChoiceListView);
        ListUtils.setDynamicHeight(writtenListView);

        Button button = (Button) findViewById(R.id.quiz_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitQuiz();
            }
        });
    }

    private void displayListView(){
        multipleChoiceAdapter = new MultipleChoiceAdapter(this,
                R.layout.quiztaker_multiplechoice_list_layout, multipleChoiceQuestions);
        multipleChoiceListView.setAdapter(multipleChoiceAdapter);
        multipleChoiceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO
            }
        });

        writtenAdapter = new WrittenAdapter(this,
                R.layout.quiztaker_written_list_layout, writtenQuestions);
        writtenListView.setAdapter(writtenAdapter);
        writtenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO
            }
        });
    }

    private class MultipleChoiceAdapter extends ArrayAdapter<MultipleChoiceQuestion> {
        private List<MultipleChoiceQuestion> multipleChoiceQuestions;

        private class ViewHolder {
            TextView questionValue;
            RadioButton answerOne;
            RadioButton answerTwo;
            RadioButton answerThree;
            RadioButton answerFour;
        }

        public MultipleChoiceAdapter(Context context, int textViewResourceId,
                               List<MultipleChoiceQuestion> multipleChoiceQuestions) {
            super(context, textViewResourceId, multipleChoiceQuestions);
            this.multipleChoiceQuestions = new ArrayList<MultipleChoiceQuestion>();
            this.multipleChoiceQuestions.addAll(multipleChoiceQuestions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MultipleChoiceAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            final Integer posObject = position;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.quiztaker_multiplechoice_list_layout, null);

                holder = new MultipleChoiceAdapter.ViewHolder();
                holder.questionValue = (TextView) convertView.findViewById(R.id.answer_question_value);
                holder.answerOne= (RadioButton) convertView.findViewById(R.id.radio_answer_1);
                holder.answerTwo= (RadioButton) convertView.findViewById(R.id.radio_answer_2);
                holder.answerThree= (RadioButton) convertView.findViewById(R.id.radio_answer_3);
                holder.answerFour= (RadioButton) convertView.findViewById(R.id.radio_answer_4);
                convertView.setTag(holder);


            } else {
                holder = (MultipleChoiceAdapter.ViewHolder) convertView.getTag();
            }


            MultipleChoiceQuestion question = multipleChoiceQuestions.get(position);

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


            holder.questionValue.setText(question.getQuestion());
            holder.answerOne.setText(question.getAnswers().get(0));
            holder.answerTwo.setText(question.getAnswers().get(1));
            holder.answerThree.setText(question.getAnswers().get(2));
            holder.answerFour.setText(question.getAnswers().get(3));

            return convertView;

        }
    }

    private class WrittenAdapter extends ArrayAdapter<WrittenQuestion> {
        private List<WrittenQuestion> writtenQuestions;

        public class ViewHolder {
            TextView questionValue;
            EditText editText;
        }
        public WrittenAdapter(Context context, int textViewResourceId,
                                     List<WrittenQuestion> writtenQuestions) {
            super(context, textViewResourceId, writtenQuestions);
            this.writtenQuestions = new ArrayList<WrittenQuestion>();
            this.writtenQuestions.addAll(writtenQuestions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            WrittenAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            final Integer posObject = position;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.quiztaker_written_list_layout, null);

                holder = new WrittenAdapter.ViewHolder();
                holder.questionValue = (TextView) convertView.findViewById(R.id.answer_question_value);
                holder.editText = (EditText) convertView.findViewById(R.id.written_textfield);
                convertView.setTag(holder);


            } else {
                holder = (WrittenAdapter.ViewHolder) convertView.getTag();
            }


            WrittenQuestion question = writtenQuestions.get(position);

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


            holder.questionValue.setText(question.getQuestion());
            return convertView;

        }
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void submitQuiz(){
        // TODO: Do Quiz Validation Check (LOW PRIORITY)
        // TODO: Retrieve Quiz
        List<Answer> answers = new ArrayList<Answer>();
        for(int i = 0; i < multipleChoiceQuestions.size(); i++){
            View view = getViewByPosition(i, multipleChoiceListView);
            MultipleChoiceAdapter.ViewHolder holder = (MultipleChoiceAdapter.ViewHolder) view.getTag();
            MultipleChoiceAnswer answer = new MultipleChoiceAnswer();
            answer.setQuestion(multipleChoiceQuestions.get(i));
            Integer selectedAnswer = null;
            if(holder.answerOne.isChecked())
                selectedAnswer = 0;
            else if(holder.answerTwo.isChecked())
                selectedAnswer = 1;
            else if(holder.answerThree.isChecked())
                selectedAnswer = 2;
            else if(holder.answerFour.isChecked())
                selectedAnswer = 3;
            answer.setAnswer(selectedAnswer);
            answers.add(answer);
        }
        for(int i = 0; i < writtenQuestions.size(); i++){
            View view = getViewByPosition(i, writtenListView);
            WrittenAdapter.ViewHolder holder = (WrittenAdapter.ViewHolder) view.getTag();
            WrittenAnswer answer = new WrittenAnswer();
            answer.setQuestion(writtenQuestions.get(i));
            answer.setAnswer(holder.editText.getText().toString());
            answers.add(answer);
        }


        // TODO: Bluetooth Logic (HIGH PRIORITY)
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}
