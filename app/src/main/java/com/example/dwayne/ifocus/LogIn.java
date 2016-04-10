package com.example.dwayne.ifocus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void login_button(View view) {
        Intent itd = new Intent(LogIn.this,MainActivity.class);
        startActivity(itd);
    }

    public void cancel_button(View view) {
        Intent itd = new Intent(LogIn.this,Welcome.class);
        startActivity(itd);
    }
}
