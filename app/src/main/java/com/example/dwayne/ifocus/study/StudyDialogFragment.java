package com.example.dwayne.ifocus.study;

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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.study.db.StudyContract;
import com.example.dwayne.ifocus.study.listener.StudyDialogClickListener;
import com.example.dwayne.ifocus.study.pojo.Study;

import java.util.Calendar;

/**
 * Created by Subin on 3/29/2016.
 */
public class StudyDialogFragment extends DialogFragment {

    private EditText ed_task_name;
    private CalendarView cal_study_date;

    private StudyDialogClickListener callback;

    private String selectedDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callback = (StudyDialogClickListener) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = (getArguments()!=null && getArguments().size()>0)? "Edit Study Task" : "Create Study Task";
        AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DateFormat df = new DateFormat();
                                String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();
                                if(getArguments()!=null && getArguments().size()>0){
                                    Study study = new Study(ed_task_name.getEditableText().toString(), selectedDate, currentDate, Boolean.FALSE);
                                    int id = getArguments().getInt(StudyContract.StudyColumns.STUDY_ID);
                                    study.setId(id);
                                    callback.onEditOKClick(study);
                                }else {
                                    callback.onCreateOKClick(new Study(ed_task_name.getEditableText().toString(), selectedDate, currentDate, Boolean.FALSE));
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
        View v = i.inflate(R.layout.study_task_form,null);
        cal_study_date = (CalendarView) v.findViewById(R.id.cal_study_date);
        ed_task_name = (EditText)v.findViewById(R.id.ed_task_name);
        if(getArguments()!=null && getArguments().size()>0) {
            String type = getArguments().getString(StudyContract.StudyColumns.STUDY_TYPE);
            String desc = getArguments().getString(StudyContract.StudyColumns.STUDY_DESCRIPTION);
            ed_task_name.setText(type);

            String date = desc;
            String parts[] = date.split("/");

            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int year = Integer.parseInt(parts[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            long milliTime = calendar.getTimeInMillis();

            cal_study_date.setDate(milliTime, true, true);
        }
        cal_study_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                selectedDate = day + "/" + (month+1) + "/" + year;
            }
        });

        b.setView(v);
        return b.create();
    }

}
