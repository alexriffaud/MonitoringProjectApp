package com.monitoringprojectapp.Requirement;

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

public class RequirementAdapter extends ArrayAdapter<Requirement>
{
    private final LayoutInflater layoutInflater;

    public RequirementAdapter(Context context, List<Requirement> list)
    {
        super(context, R.layout.list_row, list);
        layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.i("RequirementAdapter", "RequirementAdapter");
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.list_row_requirement, null);
        }
        TextView identifierRequirement = convertView.findViewById(R.id.identifier_requirement);
        TextView descriptionRequirement = convertView.findViewById(R.id.description_requirement);
        TextView isFunctionalRequirement = convertView.findViewById(R.id.is_functional_requirement);
        TextView typeRequirement = convertView.findViewById(R.id.type_requirement);

        Requirement requirement = getItem(position);
        try
        {
            assert requirement != null;
            identifierRequirement.setText(requirement.getIdentifier());
            descriptionRequirement.setText(requirement.getDescription());

            if(requirement.getIsFunctional() != 0)
            {
                isFunctionalRequirement.setText("L'exigence est fonctionnelle");
                typeRequirement.setText(requirement.getType().getName());
            }
            else
            {
                isFunctionalRequirement.setText("L'exigence n'est pas fonctionnelle");
            }

            Log.i("RequirementAdapter", "adapter");
        }
        catch (NullPointerException nullException)
        {
            System.err.print(nullException.toString());
            Log.i("RequirementAdapter", "NullPointerException");
        }
        return convertView;
    }
}
