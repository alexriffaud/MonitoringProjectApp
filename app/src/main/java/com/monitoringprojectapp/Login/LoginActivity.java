package com.monitoringprojectapp.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.monitoringprojectapp.Project.ProjectActivity;
import com.monitoringprojectapp.R;
import com.monitoringprojectapp.Tools.NukeSSLCerts;
import com.monitoringprojectapp.Tools.VolleyCallback;
import com.monitoringprojectapp.User.UserDAO;

public class LoginActivity extends AppCompatActivity
{
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new NukeSSLCerts().nuke();
        userDAO = new UserDAO(LoginActivity.this);

        Button button = findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText editTextUsername =  findViewById(R.id.editUsername);
                EditText editTextPassword =  findViewById(R.id.editPasword);
                TextView message =  findViewById(R.id.message);
                //message.setText("");
                userDAO.login(editTextUsername.getText().toString(), editTextPassword.getText().toString(), new VolleyCallback()
                {
                    @Override
                    public void onSuccess(String result) throws JSONException
                    {
                        JSONObject mainObject = new JSONObject(result);
                        String token = mainObject.getString("token");
                        testConnection(token);
                    }
                });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void testConnection(String token)
    {
        EditText editTextUsername =  findViewById(R.id.editUsername);
        EditText editTextPassword =  findViewById(R.id.editPasword);
        TextView message =  findViewById(R.id.message);

        if(!token.isEmpty())
        {
            Intent intentProject = new Intent(LoginActivity.this, ProjectActivity.class);
            intentProject.putExtra("state", "true");
            intentProject.putExtra("token", token);
            intentProject.putExtra("username", editTextUsername.getText().toString());
            startActivity(intentProject);

        }
        else
        {
            message.setText("Invalid password or username");
        }
    }
}
