package com.monitoringprojectapp.Requirement;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.monitoringprojectapp.Milestone.Milestone;
import com.monitoringprojectapp.Milestone.MilestoneActivity;
import com.monitoringprojectapp.Milestone.MilestoneDAO;
import com.monitoringprojectapp.Project.Project;
import com.monitoringprojectapp.Project.ProjectActivity;
import com.monitoringprojectapp.R;
import com.monitoringprojectapp.Task.Task;
import com.monitoringprojectapp.Task.TaskActivity;
import com.monitoringprojectapp.Tools.MilestoneList;
import com.monitoringprojectapp.Tools.RequirementList;

import java.util.Objects;

public class RequirementActivity extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private RequirementDAO requirementDAO;
    private String currentUsername;
    private String token;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        currentUsername = extras.getString("username");
        token = extras.getString("token");
        id = extras.getInt("id");

        Log.i("RequirementActivity", token);

        requirementDAO = new RequirementDAO(RequirementActivity.this);
        try
        {
            listView = findViewById(R.id.listviewR);
            requirementDAO.setRequirements(new RequirementList<Requirement>(listView, RequirementActivity.this));
            display();
        }
        catch (NullPointerException e)
        {
            Log.e("AdvertActivity", "NullPointerException");
        }

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final Dialog dlg = new Dialog(RequirementActivity.this);
                dlg.setContentView(R.layout.add_requirement);
                Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(220,255,220,160)));

                (dlg.findViewById(R.id.button_project_cancel))
                        .setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                (dlg.findViewById(R.id.button_project_add))
                        .setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                EditText identifier =  dlg.findViewById(R.id.requirement_identifier);
                                EditText description =  dlg.findViewById(R.id.requirement_description);
                                CheckBox isFunctional =  dlg.findViewById(R.id.requirement_functionnal);

                                Spinner spinner = dlg.findViewById(R.id.spinnerType);
                    //          ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(RequirementActivity.this, android.R.layout.simple_spinner_item, tri);
//                              spinner.setAdapter(spinnerArrayAdapter)

//                                requirementDAO.insertRequirement(new Milestone(identifier.getText(), description.getText(), isFunctional.isChecked(), id));
                                dlg.dismiss();
                                finish();
                                startActivity(getIntent());
                                Snackbar.make(findViewById(R.id.activity_project), "Category added",
                                        Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        });
                dlg.show();
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
                    {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        int id = menuItem.getItemId();
                        switch (id)
                        {
                            case R.id.Projects:
                                Intent intentProjects = new Intent(RequirementActivity.this, ProjectActivity.class);
                                intentProjects.putExtra("username", currentUsername);
                                intentProjects.putExtra("token", token);
                                startActivity(intentProjects);
                                return true;
                            case R.id.Milestones:
                                Intent intentMilestones = new Intent(RequirementActivity.this, MilestoneActivity.class);
                                intentMilestones.putExtra("token", token);
                                startActivity(intentMilestones);
                                return true;
                        }
                        return true;
                    }
                });
        mDrawerLayout = findViewById(R.id.activity_requirement);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener()
                {
                    @Override
                    public void onDrawerSlide(@NonNull View drawerView, float slideOffset)
                    {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(@NonNull View drawerView)
                    {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(@NonNull View drawerView)
                    {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState)
                    {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    AdapterView.OnItemLongClickListener onListDelete = new AdapterView.OnItemLongClickListener()
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            final Requirement currentRequirement = (Requirement) listView.getAdapter().getItem(position);

            final Dialog dlg = new Dialog(RequirementActivity.this);
            dlg.setContentView(R.layout.edit_requirement);

            EditText identifier =  dlg.findViewById(R.id.requirement_identifier);
            EditText description =  dlg.findViewById(R.id.requirement_description);
            CheckBox isFunctional =  dlg.findViewById(R.id.requirement_functionnal);

            Spinner spinner = dlg.findViewById(R.id.spinnerType);
            //          ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(RequirementActivity.this, android.R.layout.simple_spinner_item, tri);
//                              spinner.setAdapter(spinnerArrayAdapter)

            identifier.setText(currentRequirement.getIdentifier());
            description.setText(currentRequirement.getDescription());
            boolean b = (currentRequirement.getIsFunctional() != 0);
            isFunctional.setChecked(b);

            Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(220, 255, 220, 160)));
            (dlg.findViewById(R.id.button_requirement_cancel))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dlg.dismiss();
                        }
                    });
            (dlg.findViewById(R.id.button_requirement_edit))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText identifier =  dlg.findViewById(R.id.requirement_identifier);
                            EditText description =  dlg.findViewById(R.id.requirement_description);
                            CheckBox isFunctional =  dlg.findViewById(R.id.requirement_functionnal);

                            Spinner spinner = dlg.findViewById(R.id.spinnerType);
                            //          ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(RequirementActivity.this, android.R.layout.simple_spinner_item, tri);
//                              spinner.setAdapter(spinnerArrayAdapter)
                            currentRequirement.setIdentifier(identifier.getText().toString());
                            currentRequirement.setDescription(description.getText().toString());
                            currentRequirement.setIsFunctional(Boolean.compare(isFunctional.isChecked(), false));
//                            currentRequirement.setType());

                            requirementDAO.putRequirement(currentRequirement);
                            dlg.setContentView(R.layout.edit_requirement);
                            dlg.dismiss();
                            finish();
                            startActivity(getIntent());
                        }
                    });
            dlg.show();
            return true;
        }
    };

    AdapterView.OnItemClickListener onListUpdate = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            final Requirement currentRequirement = (Requirement) listView.getAdapter().getItem(position);

            Intent intentTask = new Intent(RequirementActivity.this, TaskActivity.class);
            intentTask.putExtra("username", currentUsername);
            intentTask.putExtra("token", token);
            intentTask.putExtra("id", currentRequirement.getId());
            startActivity(intentTask);
        }
    };

    private void display()
    {
        requirementDAO.getRequirementList(token, id);
        listView.setOnItemLongClickListener(onListDelete);
        listView.setOnItemClickListener(onListUpdate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}