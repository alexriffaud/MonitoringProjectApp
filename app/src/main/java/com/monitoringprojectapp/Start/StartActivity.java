package com.monitoringprojectapp.Start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.monitoringprojectapp.Login.LoginActivity;
import com.monitoringprojectapp.R;

public class StartActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accueil);

        Thread welcomeThread = new Thread()
        {

            @Override
            public void run()
            {
                try
                {
                    super.run();
                    sleep(2000);  //Delay of 10 seconds
                }
                catch (Exception e)
                {
                    Log.e("StartActivity", "Error splashScreen");
                }
                finally
                {
                    Intent i = new Intent(StartActivity.this,
                            LoginActivity.class);
                    i.putExtra("state", "false");
                    i.putExtra("username", "");
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
