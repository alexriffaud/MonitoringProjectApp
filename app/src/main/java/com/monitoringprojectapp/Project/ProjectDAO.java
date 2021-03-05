package com.monitoringprojectapp.Project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.monitoringprojectapp.Database.Queue;
import com.monitoringprojectapp.Tools.ProjectList;
import com.monitoringprojectapp.User.User;


public class ProjectDAO
{
    private static final String url = "https://10.0.2.2:5001";
    private final Context ctx;
    private ProjectList<Project> projects;
    private final ArrayList<Project> projectsList;
    private String token;

    public ProjectDAO(Context ctx)
    {
        this.ctx = ctx;
        Queue.getInstance(ctx);
        projectsList = new ArrayList<>();
    }

    public ProjectList<Project> getProjectsList(String token)
    {
        this.token = token;
        Log.i("ProjectDAO", "getProjectsList");
        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET, url+"/api/project/all", null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray  response)
            {
                Log.i("ProjectDAO", "onResponse");
                try
                {
                    for(int i=0;i<response.length();i++)
                    {
                        Log.i("ProjectDAO", "new project ");
                        JSONObject obj = response.getJSONObject(i);
                        JSONObject obj2 = obj.getJSONObject("manager");
                        projects.add(new Project(Integer.parseInt(obj.getString("id")), obj.getString("name"), new User(obj2.getInt("id"),obj2.getString("login"))));
                    }
                }
                catch (JSONException e)
                {
                    Log.e("ProjectDAO", "JSONException " + e);
                    //TODO: Handle error
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("ProjectDAO", "onErrorResponse " + error);

            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }
        };

        Queue.getInstance(ctx).addToRequestQueue(getRequest);
        return projects;
    }

    public void insertProject(final Project project)
    {
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", project.getName());
            final String mRequestBody = jsonBody.toString();

            Log.i("ProjectDAO", "insertProject");
            StringRequest postRequest = new StringRequest(Request.Method.POST, url+"/api/project/insert",
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.i("Response", response);
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                projects.add(new Project(Integer.parseInt(mainObject.getString("id")),mainObject.getString("name")));
                            }
                            catch (JSONException e)
                            {
                                Log.e("ProjectDAO", "JSONException " + e);
                                //TODO: Handle error
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer "+token);
                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }
            };
            Queue.getInstance(ctx).addToRequestQueue(postRequest);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void putProject(final Project project)
    {
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", project.getId());
            jsonBody.put("name", project.getName());
            final String mRequestBody = jsonBody.toString();

            StringRequest putRequest = new StringRequest(Request.Method.PUT, url+"/api/project/edit/"+project.getId(),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                projects.get(Integer.parseInt(mainObject.getString("id"))).setName(mainObject.getString("name"));
                            }
                            catch (JSONException e)
                            {
                                Log.e("ProjectDAO", "JSONException " + e);
                                //TODO: Handle error
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer "+token);
                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

            };

            Queue.getInstance(ctx).addToRequestQueue(putRequest);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteProject(Project project)
    {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url+"/api/project/delete/"+project.getId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );
        Queue.getInstance(ctx).addToRequestQueue(deleteRequest);
    }

    public void setProjects(ProjectList<Project> projects) {
        this.projects = projects;
    }
}
