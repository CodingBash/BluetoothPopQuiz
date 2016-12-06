package edu.ilstu.bluetoothpopquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentPollingActivity extends AppCompatActivity {

    Button retrievedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_polling);
        init();
    }

    private void init() {
    retrievedButton = (Button) findViewById(R.id.student_polling_button);
        retrievedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToQuizTaker();
            }
        });
    }

    private void goToQuizTaker() {
        Intent i = new Intent(this, StudentQuizTakerActivity.class);
        startActivity(i);

    }
}
