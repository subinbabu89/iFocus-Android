package com.example.dwayne.ifocus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kalpesh bhatia on 4/10/2016.
 */
public class GFeedback extends AppCompatActivity
{
    EditText eid;
    EditText fid;
    EditText sid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.givefeedback);

        eid = (EditText) findViewById(R.id.email);
        fid = (EditText) findViewById(R.id.feed);
        sid = (EditText) findViewById(R.id.sub);

        Button s= (Button) findViewById(R.id.sen);
        s.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendEmail();

            }

        });
    }

    protected void sendEmail()
    {
        //Log.i("Send email", "");
        //String[] TO = {"kalpeshbhatia000@gmail.com"};
        String[] TO = {eid.getText().toString()};
        String sid1=sid.getText().toString();
        String fid1=fid.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,sid1);
        emailIntent.putExtra(Intent.EXTRA_TEXT,fid1);

        try
        {
            startActivity(Intent.createChooser(emailIntent, "Send mail1..."));
            finish();

        }
        catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(GFeedback.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }



}
