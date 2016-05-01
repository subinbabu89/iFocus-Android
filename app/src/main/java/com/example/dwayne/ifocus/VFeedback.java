package com.example.dwayne.ifocus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by kalpesh bhatia on 4/21/2016.
 */
public class VFeedback extends AppCompatActivity
{
    EditText eid;
    EditText fid;
    EditText sid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.viewfeedback);

        Button k= (Button) findViewById(R.id.ok);
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.app_main);

            }

        });

    }
}
