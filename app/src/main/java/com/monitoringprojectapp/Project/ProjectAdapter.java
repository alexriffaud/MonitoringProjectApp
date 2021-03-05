package com.monitoringprojectapp.Project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import com.monitoringprojectapp.R;

public class ProjectAdapter extends ArrayAdapter<Project>
{
    private final LayoutInflater layoutInflater;

    public ProjectAdapter(Context context, List<Project> list)
    {
        super(context, R.layout.list_row, list);
        layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.i("ProjectAdapter", "ProjectAdapter");
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.list_row_project, null);
        }
        TextView name = convertView.findViewById(R.id.name);
        TextView manager = convertView.findViewById(R.id.manager);

        Project project = getItem(position);
        try
        {
            assert project != null;
            name.setText(project.getName());
            manager.setText(project.getUser().getUsername());

            Log.i("ProjectAdapter", "adapter");
        }
        catch (NullPointerException nullException)
        {
            System.err.print(nullException.toString());
            Log.i("ProjectAdapter", "NullPointerException");
        }
        return convertView;
    }
}
