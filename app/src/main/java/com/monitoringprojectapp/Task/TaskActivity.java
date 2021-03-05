package com.monitoringprojectapp.Task;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Objects;

import com.monitoringprojectapp.Milestone.MilestoneActivity;
import com.monitoringprojectapp.Project.Project;
import com.monitoringprojectapp.Project.ProjectActivity;
import com.monitoringprojectapp.R;
import com.monitoringprojectapp.Requirement.Requirement;
import com.monitoringprojectapp.Requirement.RequirementActivity;
import com.monitoringprojectapp.Requirement.RequirementDAO;
import com.monitoringprojectapp.Tools.RequirementList;
import com.monitoringprojectapp.Tools.TaskList;

import static java.lang.Boolean.parseBoolean;

public class TaskActivity extends AppCompatActivity
{
    private DrawerLayout mDrawerLayout;
    private TaskDAO taskDAO;
    private String token;
    private int id;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        token = extras.getString("token");
        id = extras.getInt("id");

        Log.i("TaskActivity", token);

        taskDAO = new TaskDAO(TaskActivity.this);
        try
        {
            listView = findViewById(R.id.listviewT);
            taskDAO.setTaskList(new TaskList<Task>(listView, TaskActivity.this));
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
                final Dialog dlg = new Dialog(TaskActivity.this);
                dlg.setContentView(R.layout.add_task);
                Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(220,255,220,160)));

                (dlg.findViewById(R.id.button_task_cancel))
                        .setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                (dlg.findViewById(R.id.button_task_add))
                        .setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {

                                dlg.dismiss();
                                finish();
                                startActivity(getIntent());
                                Snackbar.make(findViewById(R.id.advert_activity), "Task added",
                                        Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        });
                dlg.show();
            }
        });
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
                                Intent intentProjects = new Intent(TaskActivity.this, ProjectActivity.class);
                                intentProjects.putExtra("token", token);
                                startActivity(intentProjects);
                                return true;
                            case R.id.Milestones:
                                Intent intentMilestones = new Intent(TaskActivity.this, MilestoneActivity.class);
                                intentMilestones.putExtra("token", token);
                                startActivity(intentMilestones);
                                return true;
                        }
                        return true;
                    }
                });
        mDrawerLayout = findViewById(R.id.task_activity);
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
            final Task currentTask = (Task) listView.getAdapter().getItem(position);

            final Dialog dlg = new Dialog(TaskActivity.this);
            dlg.setContentView(R.layout.edit_task);

            EditText identifier =  dlg.findViewById(R.id.task_identifier);
            EditText label =  dlg.findViewById(R.id.task_label);
            EditText description =  dlg.findViewById(R.id.task_description);
            EditText theoreticalDate =  dlg.findViewById(R.id.task_theoretical_date);
            EditText realDate =  dlg.findViewById(R.id.task_real_date);
            EditText load =  dlg.findViewById(R.id.load);

            identifier.setText(currentTask.getIdentifier());
            description.setText(currentTask.getDescription());
            label.setText(currentTask.getLabel());
            theoreticalDate.setText(currentTask.getTheoreticalStartDate().toString());
            realDate.setText(currentTask.getRealStartDate().toString());
            load.setText(currentTask.getLoad());

            Spinner spinnerUser = dlg.findViewById(R.id.spinnerUser);
            //          ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_spinner_item, tri);
//                              spinner.setAdapter(spinnerArrayAdapter)

            Spinner spinnerPreviousTask = dlg.findViewById(R.id.spinnerTask);
            //          ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_spinner_item, tri);
//                              spinner.setAdapter(spinnerArrayAdapter)

            Spinner spinnerMilestone = dlg.findViewById(R.id.spinnerMilestone);
            //          ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(TaskActivity.this, android.R.layout.simple_spinner_item, tri);
//                              spinner.setAdapter(spinnerArrayAdapter)

            Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(220, 255, 220, 160)));
            (dlg.findViewById(R.id.button_task_cancel))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dlg.dismiss();
                        }
                    });
            (dlg.findViewById(R.id.button_task_edit))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            taskDAO.putTask(currentTask);
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
            final Task currentTask = (Task) listView.getAdapter().getItem(position);
            Intent intentRequirement = new Intent(TaskActivity.this, RequirementActivity.class);
            intentRequirement.putExtra("token", token);
            intentRequirement.putExtra("id", currentTask.getId());
            startActivity(intentRequirement);
        }
    };

    private void display()
    {
        taskDAO.getTaskList(token, id);
        listView.setOnItemLongClickListener(onListDelete);
        listView.setOnItemClickListener(onListUpdate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
