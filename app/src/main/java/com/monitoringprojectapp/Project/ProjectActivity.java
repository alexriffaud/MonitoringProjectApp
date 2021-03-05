package com.monitoringprojectapp.Project;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.monitoringprojectapp.Milestone.MilestoneActivity;
import com.monitoringprojectapp.Requirement.RequirementActivity;
import com.monitoringprojectapp.Task.TaskActivity;
import com.monitoringprojectapp.R;
import com.monitoringprojectapp.Tools.ProjectList;
import com.monitoringprojectapp.User.UserDAO;

public class ProjectActivity extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private ProjectDAO projectDAO;
    private UserDAO userDAO;
    private String currentUsername;
    private String token;
    private List<String> tri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        currentUsername = extras.getString("username");
        token = extras.getString("token");

        Log.i("ProjectActivity", token);


        projectDAO = new ProjectDAO(ProjectActivity.this);
        userDAO = new UserDAO(ProjectActivity.this);
        tri = new ArrayList<>();
        tri = userDAO.getTrigramme(token);

        try
        {
            listView = findViewById(R.id.listview);
            projectDAO.setProjects(new ProjectList<Project>(listView, ProjectActivity.this));
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
                final Dialog dlg = new Dialog(ProjectActivity.this);
                dlg.setContentView(R.layout.add_project);
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
                                Spinner spinner = (Spinner) findViewById(R.id.spinnerProject);
//                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ProjectActivity.this, android.R.layout.simple_spinner_item, tri);
//                                spinner.setAdapter(spinnerArrayAdapter);


                                EditText editTextName =  dlg.findViewById(R.id.project_name);

                                projectDAO.insertProject(new Project(editTextName.getText().toString()));
                                dlg.dismiss();
                                finish();
                                startActivity(getIntent());
                                Snackbar.make(findViewById(R.id.activity_project), "Project added",
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
                                Intent intentProjects = new Intent(ProjectActivity.this, ProjectActivity.class);
                                intentProjects.putExtra("username", currentUsername);
                                intentProjects.putExtra("token", token);
                                startActivity(intentProjects);
                                return true;
                            case R.id.Milestones:
                                Intent intentMilestones = new Intent(ProjectActivity.this, MilestoneActivity.class);
                                intentMilestones.putExtra("token", token);
                                startActivity(intentMilestones);
                                return true;
                        }
                        return true;
                    }
                });
        mDrawerLayout = findViewById(R.id.activity_project);
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
            final Project currentProject = (Project) listView.getAdapter().getItem(position);

            final Dialog dlg = new Dialog(ProjectActivity.this);
            dlg.setContentView(R.layout.edit_project);

            EditText editProjectName = dlg.findViewById(R.id.project_name);
            editProjectName.setText(currentProject.getName());

            Spinner spinner = dlg.findViewById(R.id.spinnerProject);
//            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ProjectActivity.this, android.R.layout.simple_spinner_item, tri);
//            spinner.setAdapter(spinnerArrayAdapter)

            Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(220, 255, 220, 160)));
            (dlg.findViewById(R.id.button_project_cancel))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dlg.dismiss();
                        }
                    });
            (dlg.findViewById(R.id.button_project_add))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            EditText editTextName = dlg.findViewById(R.id.project_name);
                            currentProject.setName(editTextName.getText().toString());
                            projectDAO.putProject(currentProject);
                            dlg.setContentView(R.layout.add_project);
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
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Project currentProject = (Project) listView.getAdapter().getItem(position);
            Intent intentRequirement = new Intent(ProjectActivity.this, RequirementActivity.class);
            intentRequirement.putExtra("username", currentUsername);
            intentRequirement.putExtra("token", token);
            intentRequirement.putExtra("id", currentProject.getId());
            startActivity(intentRequirement);
        }
    };

    private void display()
    {
        projectDAO.getProjectsList(token);
        listView.setOnItemLongClickListener(onListDelete);
        listView.setOnItemClickListener(onListUpdate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
