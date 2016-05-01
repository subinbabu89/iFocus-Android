package com.example.dwayne.ifocus.budget;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentHostCallback;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dwayne.ifocus.LogIn;
import com.example.dwayne.ifocus.MainActivity;
import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.Welcome;
import com.example.dwayne.ifocus.db.iFocusDatabase;
import com.example.dwayne.ifocus.user.User;
import com.example.dwayne.ifocus.user.UserDatabase;

/**
 * Created by Dwayne on 4/25/2016.
 */
public class CreateBudget extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_budget);

        UserDatabase userDatabase = new UserDatabase(this);
        if(userDatabase.getBudget(User.getInstance()) != 0){
            Intent itd = new Intent(CreateBudget.this,MainActivity.class);
            startActivity(itd);
        }
    }



    public void save_button(View view) {
        EditText bg = (EditText)findViewById(R.id.capbudget);
        EditText bl = (EditText)findViewById(R.id.capbalance);
        float budget = 0;//Float.valueOf(bg.getEditableText().toString());
        float balance = 0;//Float.valueOf(bl.getEditableText().toString());

        User user = User.getInstance();
        UserDatabase userDatabase = new UserDatabase(this);
        userDatabase.setBudget(user,Integer.parseInt(bl.getEditableText().toString()));
        user.setUserBudget(Integer.parseInt(bl.getEditableText().toString()));
        Intent itd = new Intent(CreateBudget.this,MainActivity.class);
        startActivity(itd);
    }

    public void cancel_button1(View view) {
        Intent itd = new Intent(CreateBudget.this,Welcome.class);
        startActivity(itd);
    }
}