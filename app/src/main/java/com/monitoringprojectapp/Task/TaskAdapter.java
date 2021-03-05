package com.monitoringprojectapp.Task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.monitoringprojectapp.R;

public class TaskAdapter extends ArrayAdapter<Task>
{
    private final LayoutInflater layoutInflater;

    public TaskAdapter(Context context, List<Task> list)
    {
        super(context, R.layout.list_row, list);
        layoutInflater = (LayoutInflater)
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.i("TaskAdapter", "TaskAdapter");
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.list_row_task, null);
        }
        TextView label = convertView.findViewById(R.id.label);
        TextView description = convertView.findViewById(R.id.description);
        TextView identifier = convertView.findViewById(R.id.identifier);
        TextView user = convertView.findViewById(R.id.user);
        TextView theoreticalStartDate = convertView.findViewById(R.id.theoreticalStartDate);
        TextView realStartDate = convertView.findViewById(R.id.realStartDate);
        TextView load = convertView.findViewById(R.id.load);
        TextView previousTask = convertView.findViewById(R.id.previousTask);
        TextView milestone = convertView.findViewById(R.id.milestone);

        Task task = getItem(position);
        try
        {
            assert task != null;
            label.setText(task.getLabel());
            description.setText(task.getDescription());
            identifier.setText(task.getIdentifier());
            user.setText(task.getUser().getUsername());
            theoreticalStartDate.setText(task.getTheoreticalStartDate().toString());
            realStartDate.setText(task.getRealStartDate().toString());
            load.setText(task.getLoad());
            previousTask.setText(task.getPreviousTask().getLabel());
            milestone.setText(task.getMilestone().getLabel());

            Log.i("TaskAdapter", "adapter");
        }
        catch (NullPointerException nullException)
        {
            System.err.print(nullException.toString());
            Log.i("TaskAdapter", "NullPointerException");
        }
        return convertView;
    }
}
