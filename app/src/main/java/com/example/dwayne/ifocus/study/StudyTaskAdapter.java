package com.example.dwayne.ifocus.study;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwayne.ifocus.MainActivity;
import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.ScoringSheet;
import com.example.dwayne.ifocus.study.history.StudyHistoryDao;
import com.example.dwayne.ifocus.study.history.StudyHistoryDatabase;
import com.example.dwayne.ifocus.study.listener.StudyEditClickListener;
import com.example.dwayne.ifocus.study.pojo.Study;
import com.example.dwayne.ifocus.user.User;
import com.example.dwayne.ifocus.user.UserDatabase;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Subin on 3/25/2016.
 */
public class StudyTaskAdapter extends ArrayAdapter<Study> {

    private StudyFragment context;
    private  StudyHistoryDatabase studyHistoryDatabase;

    public StudyTaskAdapter(StudyFragment context) {
        super(context.getActivity(),android.R.layout.simple_list_item_2);
        this.context = context;
        studyHistoryDatabase = ((MainActivity)context.getActivity()).getStudyHistoryDatabase();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        LayoutInflater inflater = context.getActivity().getLayoutInflater();


        if (rowView == null) {

            rowView = inflater.inflate(R.layout.list_study_item_layout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtv_study_task_name = (TextView) rowView.findViewById(R.id.txtv_study_task_name);
            viewHolder.txtv_study_task_value = (TextView) rowView.findViewById(R.id.txtv_study_task_value);
            viewHolder.imgbtn_task_complete = (ImageButton)rowView.findViewById(R.id.imgbtn_task_complete);
            viewHolder.imgbtn_task_incomplete = (ImageButton)rowView.findViewById(R.id.imgbtn_task_incomplete);
            viewHolder.imgbtn_task_delete = (ImageButton)rowView.findViewById(R.id.imgbtn_task_delete);
            viewHolder.imgbtn_task_edit = (ImageButton)rowView.findViewById(R.id.imgbtn_task_edit);
            rowView.setTag(viewHolder);
        }

        final Study study = getItem(position);


        ViewHolder holder = (ViewHolder) rowView.getTag();
        if(study!=null)
        holder.txtv_study_task_name.setText(study.getType());
        if(study!=null) {
            holder.txtv_study_task_value.setText(study.getDescription());
        }
        holder.imgbtn_task_complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity()).setMessage("are you sure you want to mark this task as complete?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context.getActivity(),"Message marked as complete", Toast.LENGTH_SHORT).show();

                        DateFormat df = new DateFormat();
                        String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();

                        studyHistoryDatabase.insertHistoryRecord(new StudyHistoryDao(study.getId(),currentDate,Boolean.TRUE));
                        ((StudyEditClickListener)context).markChanged();
                        int userScore = User.getInstance().getUserScore();
                        userScore+= ScoringSheet.getScoreSheet().get(ScoringSheet.STUDY_TASK_DONE);
                        User.getInstance().setUserScore(userScore);
                        ((MainActivity)context.getActivity()).setNavigationDrawerData();

                        UserDatabase userDatabase = new UserDatabase((MainActivity)context.getActivity());
                        userDatabase.setScore(User.getInstance(),User.getInstance().getUserScore());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        holder.imgbtn_task_incomplete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity()).setMessage("are you sure you want to mark this task as incomplete?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context.getActivity(),"Message marked as incomplete", Toast.LENGTH_SHORT).show();

                        DateFormat df = new DateFormat();
                        String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();

                        StudyHistoryDatabase studyHistoryDatabase = ((MainActivity)context.getActivity()).getStudyHistoryDatabase();
                        studyHistoryDatabase.insertHistoryRecord(new StudyHistoryDao(study.getId(),currentDate,Boolean.FALSE));
                        ((StudyEditClickListener)context).markChanged();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        holder.imgbtn_task_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity()).setMessage("are you sure you want to delete this task?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((StudyEditClickListener)context).onDeleteClicked(study);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

            }
        });

        holder.imgbtn_task_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((StudyEditClickListener)context).onEditClicked(study);

            }
        });

        if(studyHistoryDatabase.isTaskMarked(study.getId())){
            holder.imgbtn_task_complete.setVisibility(View.GONE);
            holder.imgbtn_task_incomplete.setVisibility(View.GONE);
        }else{
            holder.imgbtn_task_complete.setVisibility(View.VISIBLE);
            holder.imgbtn_task_incomplete.setVisibility(View.VISIBLE);
        }

        return rowView;
    }

    public void setData(List<Study> studyTasks){
        clear();
        if(studyTasks!=null){
            for (Study study : studyTasks){
                add(study);
            }
        }
    }

    static class ViewHolder {
        public TextView txtv_study_task_name;
        public TextView txtv_study_task_value;

        public ImageButton imgbtn_task_incomplete;
        public ImageButton imgbtn_task_complete;
        public ImageButton imgbtn_task_delete;
        public ImageButton imgbtn_task_edit;
    }
}
