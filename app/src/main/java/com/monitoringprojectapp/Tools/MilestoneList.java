package com.monitoringprojectapp.Tools;

import android.content.Context;
import android.widget.ListView;

import com.monitoringprojectapp.Milestone.MilestoneAdapter;

import java.util.ArrayList;
import java.util.List;

public class MilestoneList<Milestone>
{
    private final ArrayList<Milestone> list;
    private final ListView listView;
    private final Context ctx;

    public MilestoneList(ListView listView, Context ctx)
    {
        list = new ArrayList<>();
        this.listView = listView;
        this.ctx = ctx;
    }

    public void add(Milestone t)
    {
        list.add(t);
        listView.setAdapter(new MilestoneAdapter(ctx, (List<com.monitoringprojectapp.Milestone.Milestone>) list));
    }

    public void remove(Milestone t)
    {
        list.remove(t);
        listView.setAdapter(new MilestoneAdapter(ctx, (List<com.monitoringprojectapp.Milestone.Milestone>) list));
    }

    public Milestone get(int i)
    {
        return list.get(i);
    }

    public ArrayList<Milestone> getList() {
        return list;
    }
}
