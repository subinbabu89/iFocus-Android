package com.example.dwayne.ifocus.health;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.health.db.HealthContract;
import com.example.dwayne.ifocus.health.listener.HealthDialogClickListener;
import com.example.dwayne.ifocus.health.pojo.Health;

/**
 * Created by Subin on 3/29/2016.
 */
public class HealthDialogFragment extends DialogFragment {

    private EditText ed_task_name;
    private EditText ed_task_value;

    private HealthDialogClickListener callback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callback = (HealthDialogClickListener) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = (getArguments()!=null && getArguments().size()>0)? "Edit Health Task" : "Create Health Task";
        AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DateFormat df = new android.text.format.DateFormat();
                                String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();
                                if(getArguments()!=null && getArguments().size()>0){
                                    Health health = new Health(ed_task_name.getEditableText().toString(), ed_task_value.getEditableText().toString(), currentDate, Boolean.FALSE);
                                    int id = getArguments().getInt(HealthContract.HealthColumns.HEALTH_ID);
                                    health.setId(id);
                                    callback.onEditOKClick(health);
                                }else {
                                    callback.onCreateOKClick(new Health(ed_task_name.getEditableText().toString(), ed_task_value.getEditableText().toString(), currentDate, Boolean.FALSE));
                                }
                            }
                        }
                ).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                });
        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.health_task_form,null);
        ed_task_value = (EditText)v.findViewById(R.id.ed_task_value);
        ed_task_name = (EditText)v.findViewById(R.id.ed_task_name);
        if(getArguments()!=null && getArguments().size()>0){
            String type = getArguments().getString(HealthContract.HealthColumns.HEALTH_TYPE);
            String desc = getArguments().getString(HealthContract.HealthColumns.HEALTH_DESCRIPTION);
            ed_task_name.setText(type);
            ed_task_value.setText(desc);
        }
        b.setView(v);
        return b.create();
    }

}
