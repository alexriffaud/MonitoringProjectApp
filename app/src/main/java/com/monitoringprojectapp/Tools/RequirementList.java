package com.monitoringprojectapp.Tools;

import android.content.Context;
import android.widget.ListView;

import com.monitoringprojectapp.Requirement.RequirementAdapter;

import java.util.ArrayList;
import java.util.List;

public class RequirementList<Requirement>
{
    private final ArrayList<Requirement> list;
    private final ListView listView;
    private final Context ctx;

    public RequirementList(ListView listView, Context ctx)
    {
        list = new ArrayList<>();
        this.listView = listView;
        this.ctx = ctx;
    }

    public void add(Requirement t)
    {
        list.add(t);
        listView.setAdapter(new RequirementAdapter(ctx, (List<com.monitoringprojectapp.Requirement.Requirement>) list));
    }

    public void remove(Requirement t)
    {
        list.remove(t);
        listView.setAdapter(new RequirementAdapter(ctx, (List<com.monitoringprojectapp.Requirement.Requirement>) list));
    }

    public Requirement get(int i)
    {
        return list.get(i);
    }

    public ArrayList<Requirement> getList() {
        return list;
    }
}
