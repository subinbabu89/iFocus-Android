package com.example.dwayne.ifocus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dwayne.ifocus.budget.CreateBudget;
import com.example.dwayne.ifocus.user.User;
import com.example.dwayne.ifocus.user.UserDatabase;

public class LogIn extends AppCompatActivity {

    private EditText ed_email_id;
    private EditText ed_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ed_email_id = (EditText)findViewById(R.id.ed_email_id);
        ed_password = (EditText)findViewById(R.id.ed_password);
    }

    public void login_button(View view) {
        User user = User.getInstance();
        user.setUserEmailID(ed_email_id.getEditableText().toString());
        user.setUserPassword(ed_password.getEditableText().toString());
        Intent itd;
        itd = new Intent(LogIn.this,CreateBudget.class);
        UserDatabase userDatabase = new UserDatabase(this);
        if(userDatabase.userPresent(user)) {
            userDatabase.getUser(user.getUserEmailID());
            startActivity(itd);
        }
        else{
            Toast.makeText(this,"User Not Found",Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel_button(View view) {
        Intent itd = new Intent(LogIn.this,Welcome.class);
        startActivity(itd);
    }
}
