package edu.ilstu.bluetoothpopquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TeacherBluetoothActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_bluetooth);
        // TODO: Bluetooth Implementation
        Intent i = new Intent(this, TeacherQuizCreationActivity.class);
        startActivity(i);

    }
}
