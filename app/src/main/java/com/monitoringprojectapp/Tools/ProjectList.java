package com.monitoringprojectapp.Tools;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.monitoringprojectapp.Project.ProjectAdapter;
import com.monitoringprojectapp.Project.Project;

public class ProjectList<Project>
{
    private final ArrayList<Project> list;
    private final ListView listView;
    private final Context ctx;

    public ProjectList(ListView listView, Context ctx)
    {
        list = new ArrayList<>();
        this.listView = listView;
        this.ctx = ctx;
    }

    public void add(Project t)
    {
        list.add(t);
        listView.setAdapter(new ProjectAdapter(ctx, (List<com.monitoringprojectapp.Project.Project>) list));
    }

    public void remove(Project t)
    {
        list.remove(t);
        listView.setAdapter(new ProjectAdapter(ctx, (List<com.monitoringprojectapp.Project.Project>) list));
    }

    public Project get(int i)
    {
        return list.get(i);
    }

    public ArrayList<Project> getList() {
        return list;
    }


}

