package com.example.dwayne.ifocus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dwayne.ifocus.user.User;
import com.example.dwayne.ifocus.user.UserDatabase;

public class Register_ok extends AppCompatActivity {

    private String ed_user_name;
    private String ed_user_email;
    private String ed_user_password;
    private String ed_partner_email;
    private String ed_user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ok);

        ed_user_name = getIntent().getExtras().getString("ed_user_name");
        ed_user_email = getIntent().getExtras().getString("ed_user_email");
        ed_user_password = getIntent().getExtras().getString("ed_user_password");
        ed_partner_email = getIntent().getExtras().getString("ed_partner_email");
        ed_user_type = getIntent().getExtras().getString("ed_user_type");

    }

    public void ok_button(View view) {
        User user = User.getInstance();
        user.setUserName(ed_user_name);
        user.setUserEmailID(ed_user_email);
        user.setUserPartnerEmail(ed_partner_email);
        user.setUserPassword(ed_user_password);
        user.setUserType(ed_user_type);
        user.setUserScore(0);

        Intent itd = new Intent(Register_ok.this,LogIn.class);
        UserDatabase userDatabase = new UserDatabase(this);
        userDatabase.insertUser(user);
        startActivity(itd);
    }

    public void cancel_button(View view) {
        Intent itd = new Intent(Register_ok.this,Welcome.class);
        startActivity(itd);
    }
}
