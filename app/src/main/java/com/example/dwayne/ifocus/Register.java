package com.example.dwayne.ifocus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void ok_button1(View view) {
        Intent itd = new Intent(Register.this,Register_ok.class);
        startActivity(itd);
    }

    public void cancel_register(View view) {
        Intent itd = new Intent(Register.this,Welcome.class);
        startActivity(itd);
    }
}
