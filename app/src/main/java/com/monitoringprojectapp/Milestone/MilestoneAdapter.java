package com.monitoringprojectapp.Milestone;

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

import com.monitoringprojectapp.R;

import java.util.List;

public class MilestoneAdapter extends ArrayAdapter<Milestone>
{
    private final LayoutInflater layoutInflater;

    public MilestoneAdapter(Context context, List<Milestone> list)
    {
        super(context, R.layout.list_row, list);
        layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.i("MilestoneAdapter", "MilestoneAdapter");
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.list_row_milestone, null);
        }
        TextView labelMilestone = convertView.findViewById(R.id.label_milestone);
        TextView managerMilestone = convertView.findViewById(R.id.manager_milestone);
        TextView expectedDeliveryDateMilestone = convertView.findViewById(R.id.expected_delivery_date_milestone);
        TextView reamDeliveryDateMilestone = convertView.findViewById(R.id.ream_delivery_date_milestone);

        Milestone milestone = getItem(position);
        try
        {
            assert milestone != null;
            labelMilestone.setText(milestone.getLabel());
            managerMilestone.setText(milestone.getManager().getUsername());
            expectedDeliveryDateMilestone.setText(milestone.getExpectedDeliveryDate().toString());
            reamDeliveryDateMilestone.setText(milestone.getRealDeliveryDate().toString());

            Log.i("MilestoneAdapter", "adapter");
        }
        catch (NullPointerException nullException)
        {
            System.err.print(nullException.toString());
            Log.i("MilestoneAdapter", "NullPointerException");
        }
        return convertView;
    }
}
