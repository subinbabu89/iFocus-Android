package com.example.dwayne.ifocus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void register_button(View view) {
        Intent itd = new Intent(Welcome.this,Register.class);
        startActivity(itd);

    }

    public void signin_button(View view) {
        Intent itd = new Intent(Welcome.this,LogIn.class);
        startActivity(itd);

    }
}
