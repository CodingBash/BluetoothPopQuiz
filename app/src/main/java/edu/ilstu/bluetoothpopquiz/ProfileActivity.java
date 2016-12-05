package edu.ilstu.bluetoothpopquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    private Button teacherButton;
    private Button studentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    private void init(){
        teacherButton = (Button) findViewById(R.id.teacher_button);
        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTeacherBluetoothActivity();
            }
        });


        studentButton = (Button) findViewById(R.id.student_button);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToStudentBluetoothActivity();
            }
        });
    }

    private void goToTeacherBluetoothActivity(){
        Intent i = new Intent(this, TeacherBluetoothActivity.class);
        startActivity(i);
    }

    private void goToStudentBluetoothActivity(){
        Intent i = new Intent(this, StudentBluetoothActivity.class);
        startActivity(i);
    }
}
