package com.example.dwayne.ifocus.health;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.example.dwayne.ifocus.health.db.HealthContract;
import com.example.dwayne.ifocus.health.history.HealthHistoryDao;
import com.example.dwayne.ifocus.health.history.HealthHistoryDatabase;
import com.example.dwayne.ifocus.health.listener.HealthEditClickListener;
import com.example.dwayne.ifocus.health.pojo.Health;
import com.example.dwayne.ifocus.R;

import java.util.List;

/**
 * Created by Subin on 3/25/2016.
 */
public class HealthTaskAdapter extends ArrayAdapter<Health> {

    private HealthFragment context;
    private  HealthHistoryDatabase healthHistoryDatabase;

    public HealthTaskAdapter(HealthFragment context) {
        super(context.getActivity(),android.R.layout.simple_list_item_2);
        this.context = context;
        healthHistoryDatabase = ((MainActivity)context.getActivity()).getHealthHistoryDatabase();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        LayoutInflater inflater = context.getActivity().getLayoutInflater();


        if (rowView == null) {

            rowView = inflater.inflate(R.layout.list_health_item_layout, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtv_health_task_name = (TextView) rowView.findViewById(R.id.txtv_health_task_name);
            viewHolder.txtv_health_task_value = (TextView) rowView.findViewById(R.id.txtv_health_task_value);
            viewHolder.imgbtn_task_complete = (ImageButton)rowView.findViewById(R.id.imgbtn_task_complete);
            viewHolder.imgbtn_task_incomplete = (ImageButton)rowView.findViewById(R.id.imgbtn_task_incomplete);
            viewHolder.imgbtn_task_delete = (ImageButton)rowView.findViewById(R.id.imgbtn_task_delete);
            viewHolder.imgbtn_task_edit = (ImageButton)rowView.findViewById(R.id.imgbtn_task_edit);
            rowView.setTag(viewHolder);
        }

        final Health health = getItem(position);


        ViewHolder holder = (ViewHolder) rowView.getTag();
        if(health!=null)
        holder.txtv_health_task_name.setText(health.getType());
        if(health!=null)
        holder.txtv_health_task_value.setText(health.getDescription());
        holder.imgbtn_task_complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity()).setMessage("are you sure you want to mark this task as complete?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context.getActivity(),"Message marked as complete", Toast.LENGTH_SHORT).show();

                        DateFormat df = new android.text.format.DateFormat();
                        String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();

                        healthHistoryDatabase.insertHistoryRecord(new HealthHistoryDao(health.getId(),currentDate,Boolean.TRUE));
                        ((HealthEditClickListener)context).markChanged();
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

                        DateFormat df = new android.text.format.DateFormat();
                        String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();

                        HealthHistoryDatabase healthHistoryDatabase = ((MainActivity)context.getActivity()).getHealthHistoryDatabase();
                        healthHistoryDatabase.insertHistoryRecord(new HealthHistoryDao(health.getId(),currentDate,Boolean.FALSE));
                        ((HealthEditClickListener)context).markChanged();
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
                        ((HealthEditClickListener)context).onDeleteClicked(health);
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

                ((HealthEditClickListener)context).onEditClicked(health);

            }
        });

        if(healthHistoryDatabase.isTaskMarked(health.getId())){
            holder.imgbtn_task_complete.setVisibility(View.GONE);
            holder.imgbtn_task_incomplete.setVisibility(View.GONE);
        }else{
            holder.imgbtn_task_complete.setVisibility(View.VISIBLE);
            holder.imgbtn_task_incomplete.setVisibility(View.VISIBLE);
        }

        return rowView;
    }

    public void setData(List<Health> healthTasks){
        clear();
        if(healthTasks!=null){
            for (Health health : healthTasks){
                add(health);
            }
        }
    }

    static class ViewHolder {
        public TextView txtv_health_task_name;
        public TextView txtv_health_task_value;

        public ImageButton imgbtn_task_incomplete;
        public ImageButton imgbtn_task_complete;
        public ImageButton imgbtn_task_delete;
        public ImageButton imgbtn_task_edit;
    }
}
