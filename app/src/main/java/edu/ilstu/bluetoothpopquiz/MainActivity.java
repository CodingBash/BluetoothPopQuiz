package edu.ilstu.bluetoothpopquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button beginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        beginButton = (Button) findViewById(R.id.appstart_button);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfileSelectActivity();
            }
        });
    }

    private void goToProfileSelectActivity(){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
}
