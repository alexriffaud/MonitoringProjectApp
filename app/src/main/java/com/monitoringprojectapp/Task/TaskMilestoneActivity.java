package com.monitoringprojectapp.Task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.monitoringprojectapp.Milestone.MilestoneActivity;
import com.monitoringprojectapp.Project.ProjectActivity;
import com.monitoringprojectapp.R;
import com.monitoringprojectapp.Tools.TaskList;

public class TaskMilestoneActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private TaskDAO taskDAO;
    private String token;
    private int id;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_milestone);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        token = extras.getString("token");
        id = extras.getInt("id");

        Log.i("TaskActivity", token);

        taskDAO = new TaskDAO(TaskMilestoneActivity.this);
        try
        {
            listView = findViewById(R.id.listviewTM);
            taskDAO.setTaskList(new TaskList<Task>(listView, TaskMilestoneActivity.this));
            display();
        }
        catch (NullPointerException e)
        {
            Log.e("AdvertActivity", "NullPointerException");
        }

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_more);

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
                                Intent intentProjects = new Intent(TaskMilestoneActivity.this, ProjectActivity.class);
                                intentProjects.putExtra("token", token);
                                startActivity(intentProjects);
                                return true;
                            case R.id.Milestones:
                                Intent intentMilestones = new Intent(TaskMilestoneActivity.this, MilestoneActivity.class);
                                intentMilestones.putExtra("token", token);
                                startActivity(intentMilestones);
                                return true;
                        }
                        return true;
                    }
                });
        mDrawerLayout = findViewById(R.id.task_milestone_activity);
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

    private void display()
    {
        taskDAO.getTaskByMilestoneList(token, id);
    }
}