package com.monitoringprojectapp.Milestone;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.monitoringprojectapp.Project.Project;
import com.monitoringprojectapp.Project.ProjectActivity;
import com.monitoringprojectapp.Project.ProjectDAO;
import com.monitoringprojectapp.R;
import com.monitoringprojectapp.Requirement.Requirement;
import com.monitoringprojectapp.Requirement.RequirementActivity;
import com.monitoringprojectapp.Task.TaskActivity;
import com.monitoringprojectapp.Task.TaskMilestoneActivity;
import com.monitoringprojectapp.Tools.MilestoneList;
import com.monitoringprojectapp.Tools.ProjectList;

import java.util.Objects;

public class MilestoneActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private MilestoneDAO milestoneDAO;
    private String currentUsername;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        currentUsername = extras.getString("username");
        token = extras.getString("token");

        Log.i("MilestoneActivity", token);

        milestoneDAO = new MilestoneDAO(MilestoneActivity.this);
        try
        {
            listView = findViewById(R.id.listview);
            milestoneDAO.setMilestoneList(new MilestoneList<Milestone>(listView, MilestoneActivity.this));
            display();
        }
        catch (NullPointerException e)
        {
            Log.e("MilestoneActivity", "NullPointerException");
        }

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final Dialog dlg = new Dialog(MilestoneActivity.this);
                dlg.setContentView(R.layout.add_milestone);
                Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(220,255,220,160)));

                (dlg.findViewById(R.id.button_milestone_cancel))
                        .setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                (dlg.findViewById(R.id.button_milestone_add))
                        .setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {

//                                milestoneDAO.insertMilestone(new Milestone(editTextName.getText().toString()));
                                dlg.dismiss();
                                finish();
                                startActivity(getIntent());
                                Snackbar.make(findViewById(R.id.activity_project), "Milestone added",
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
                                Intent intentProjects = new Intent(MilestoneActivity.this, ProjectActivity.class);
                                intentProjects.putExtra("username", currentUsername);
                                intentProjects.putExtra("token", token);
                                startActivity(intentProjects);
                                return true;
                        }
                        return true;
                    }
                });
        mDrawerLayout = findViewById(R.id.activity_milestone);
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

            final Milestone currentMilestone = (Milestone) listView.getAdapter().getItem(position);

            final Dialog dlg = new Dialog(MilestoneActivity.this);
            dlg.setContentView(R.layout.edit_milestone);
            Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(220, 255, 220, 160)));
            (dlg.findViewById(R.id.button_milestone_cancel))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dlg.dismiss();
                        }
                    });
            (dlg.findViewById(R.id.button_milestone_edit))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            milestoneDAO.putMilestone(currentMilestone);
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
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            final Milestone currentMilestone = (Milestone) listView.getAdapter().getItem(position);

            Intent intentTask = new Intent(MilestoneActivity.this, TaskMilestoneActivity.class);
            intentTask.putExtra("username", currentUsername);
            intentTask.putExtra("token", token);
            intentTask.putExtra("id", currentMilestone.getId());
            startActivity(intentTask);
        }
    };


    private void display()
    {
        milestoneDAO.getMilestoneList(token);
        listView.setOnItemLongClickListener(onListDelete);
        listView.setOnItemClickListener(onListUpdate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}