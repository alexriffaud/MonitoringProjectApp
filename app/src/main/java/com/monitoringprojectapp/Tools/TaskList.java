package com.monitoringprojectapp.Tools;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.monitoringprojectapp.Task.TaskAdapter;
import com.monitoringprojectapp.Task.Task;

public class TaskList<Task>
{
    private final ArrayList<Task> list;
    private final ListView listView;
    private final Context ctx;

    public TaskList(ListView listView, Context ctx)
    {
        list = new ArrayList<>();
        this.listView = listView;
        this.ctx = ctx;
    }

    public void add(Task t)
    {
        list.add(t);
        listView.setAdapter(new TaskAdapter(ctx, (List<com.monitoringprojectapp.Task.Task>) list));
    }

    public void remove(Task t)
    {
        list.remove(t);
        listView.setAdapter(new TaskAdapter(ctx, (List<com.monitoringprojectapp.Task.Task>) list));
    }

    public Task get(int i)
    {
        return list.get(i);
    }
}
