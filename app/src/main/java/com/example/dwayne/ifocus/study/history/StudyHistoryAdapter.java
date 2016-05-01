package com.example.dwayne.ifocus.study.history;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.study.pojo.Study;

import java.util.List;

/**
 * Created by Subin on 3/30/2016.
 */
public class StudyHistoryAdapter extends BaseAdapter {

    private List<Study>  studyHistoryObjects;
    private Activity context;

    public StudyHistoryAdapter(List<Study> studyHistoryObjects, Activity context) {
        this.studyHistoryObjects = studyHistoryObjects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return studyHistoryObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.list_study_history_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtv_study_name = (TextView) rowView.findViewById(R.id.txtv_study_name);
            viewHolder.txtv_study_desc = (TextView) rowView.findViewById(R.id.txtv_study_desc);
            viewHolder.txtv_study_date = (TextView) rowView.findViewById(R.id.txtv_study_date);
            viewHolder.txtv_study_completion = (TextView) rowView.findViewById(R.id.txtv_study_completion);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtv_study_name.setText(studyHistoryObjects.get(position).getType());
        holder.txtv_study_desc.setText(studyHistoryObjects.get(position).getDescription());
        holder.txtv_study_date.setText(studyHistoryObjects.get(position).getDate());
        holder.txtv_study_completion.setText(String.valueOf(studyHistoryObjects.get(position).isCompletion()));

        return rowView;
    }


    static class ViewHolder{
        public TextView txtv_study_name;
        public TextView txtv_study_desc;
        public TextView txtv_study_date;
        public TextView txtv_study_completion;
    }
}
