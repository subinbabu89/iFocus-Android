package com.example.dwayne.ifocus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    private EditText ed_user_name;
    private EditText ed_user_email;
    private EditText ed_user_password;
    private EditText ed_partner_email;
    private EditText ed_user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_user_name = (EditText)findViewById(R.id.ed_user_name);
        ed_user_email = (EditText)findViewById(R.id.ed_user_email);
        ed_user_password = (EditText)findViewById(R.id.ed_user_password);
        ed_partner_email = (EditText)findViewById(R.id.ed_partner_email);
        ed_user_type = (EditText)findViewById(R.id.ed_user_type);
    }



    public void ok_button1(View view) {
        Intent itd = new Intent(Register.this,Register_ok.class);
        itd.putExtra("ed_user_name",ed_user_name.getEditableText().toString());
        itd.putExtra("ed_user_email",ed_user_email.getEditableText().toString());
        itd.putExtra("ed_user_password",ed_user_password.getEditableText().toString());
        itd.putExtra("ed_partner_email",ed_partner_email.getEditableText().toString());
        itd.putExtra("ed_user_type",ed_user_type.getEditableText().toString());
        startActivity(itd);
    }

    public void cancel_register(View view) {
        Intent itd = new Intent(Register.this,Welcome.class);
        startActivity(itd);
    }
}
