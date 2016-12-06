package edu.ilstu.bluetoothpopquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherPollingActivity extends AppCompatActivity {

    Button readyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_polling);
        init();
    }

    private void init(){
        readyButton = (Button) findViewById(R.id.teacher_polling_button);
        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTeacherViewResults();
            }
        });
    }

    private void toTeacherViewResults(){
        Intent i = new Intent(this, TeacherQuizViewResultsActivity.class);
        startActivity(i);
    }
}
