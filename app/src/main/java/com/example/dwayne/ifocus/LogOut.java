package com.example.dwayne.ifocus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LogOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout);
    }

    public void okbutton(View view) {
        //Toast.makeText(this, "Thank you for using iFocus!!", Toast.LENGTH_SHORT).show();
        Intent itd = new Intent(LogOut.this,ThankYou.class);
        startActivity(itd);
        //setContentView(R.layout.thankyou);
    }
}
