package com.example.dwayne.ifocus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Register_ok extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ok);
    }

    public void ok_button(View view) {
        Intent itd = new Intent(Register_ok.this,LogIn.class);
        startActivity(itd);
    }

    public void cancel_button(View view) {
        Intent itd = new Intent(Register_ok.this,Welcome.class);
        startActivity(itd);
    }
}
