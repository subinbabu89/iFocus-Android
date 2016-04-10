package com.example.dwayne.ifocus.health.history;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.health.pojo.Health;

import java.util.List;

/**
 * Created by Subin on 3/30/2016.
 */
public class HealthHistoryAdapter extends BaseAdapter {

    private List<Health>  healthHistoryObjects;
    private Activity context;

    public HealthHistoryAdapter(List<Health> healthHistoryObjects, Activity context) {
        this.healthHistoryObjects = healthHistoryObjects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return healthHistoryObjects.size();
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

            rowView = inflater.inflate(R.layout.list_health_history_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtv_health_name = (TextView) rowView.findViewById(R.id.txtv_health_name);
            viewHolder.txtv_health_desc = (TextView) rowView.findViewById(R.id.txtv_health_desc);
            viewHolder.txtv_health_date = (TextView) rowView.findViewById(R.id.txtv_health_date);
            viewHolder.txtv_health_completion = (TextView) rowView.findViewById(R.id.txtv_health_completion);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtv_health_name.setText(healthHistoryObjects.get(position).getType());
        holder.txtv_health_desc.setText(healthHistoryObjects.get(position).getDescription());
        holder.txtv_health_date.setText(healthHistoryObjects.get(position).getDate());
        holder.txtv_health_completion.setText(String.valueOf(healthHistoryObjects.get(position).isCompletion()));

        return rowView;
    }


    static class ViewHolder{
        public TextView txtv_health_name;
        public TextView txtv_health_desc;
        public TextView txtv_health_date;
        public TextView txtv_health_completion;
    }
}
