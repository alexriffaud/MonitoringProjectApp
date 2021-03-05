package com.monitoringprojectapp.Milestone;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.monitoringprojectapp.Database.Queue;
import com.monitoringprojectapp.Project.Project;
import com.monitoringprojectapp.Tools.MilestoneList;
import com.monitoringprojectapp.User.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MilestoneDAO
{
    private static final String url = "https://10.0.2.2:5001";
    private final Context ctx;
    private MilestoneList<Milestone> milestoneList;
    private final ArrayList<Milestone> milestones;
    private String token;

    public MilestoneDAO(Context ctx)
    {
        this.ctx = ctx;
        Queue.getInstance(ctx);
        milestones = new ArrayList<>();
    }

    public MilestoneList<Milestone> getMilestoneList(String token)
    {
        this.token = token;
        Log.i("MilestoneDAO", "getMilestoneList");
        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET, url+"/api/milestone/all", null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray  response)
            {
                Log.i("MilestoneDAO", "onResponse");
                try
                {
                    for(int i=0;i<response.length();i++)
                    {
                        Log.i("MilestoneDAO", "new milestone ");
                        JSONObject obj = response.getJSONObject(i);
                        JSONObject obj2 = obj.getJSONObject("manager");

                        String exdate = obj.getString("expectedDeliveryDate");
                        String rdtStart = obj.getString("realDeliveryDate");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date exddate = null;
                        Date rdate = null;
                        try {
                            exddate = format.parse(exdate);
                            rdate = format.parse(rdtStart);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        milestoneList.add(new Milestone(Integer.parseInt(obj.getString("id")), obj.getString("label"),
                                new User(obj2.getInt("id"), obj2.getString("login")),exddate,rdate));
                    }
                }
                catch (JSONException e)
                {
                    Log.e("MilestoneDAO", "JSONException " + e);
                    //TODO: Handle error
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("MilestoneDAO", "onErrorResponse " + error);

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
        return milestoneList;
    }

    public void insertMilestone(final Milestone milestone)
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url+"/api/milestone/insert",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("Response", response);
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            //milestoneList.add(new Project(Integer.parseInt(mainObject.getString("id")),mainObject.getString("name")));
                        }
                        catch (JSONException e)
                        {
                            Log.e("MilestoneDAO", "JSONException " + e);
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
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
//                params.put("name", project.getName());

                return params;
            }

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
        Queue.getInstance(ctx).addToRequestQueue(postRequest);
    }

    public void putMilestone(final Milestone milestone)
    {
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url+"/api/milestone/edit/"+milestone.getId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject mainObject = new JSONObject(response);
                           // milestoneList.get(Integer.parseInt(mainObject.getString("id"))).setName(mainObject.getString("name"));
                        }
                        catch (JSONException e)
                        {
                            Log.e("MilestoneDAO", "JSONException " + e);
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
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
//                params.put("id", Integer.toString(project.getId()));
//                params.put("name", project.getName());

                return params;
            }

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

        Queue.getInstance(ctx).addToRequestQueue(putRequest);
    }

    public void deleteMilestone(Milestone milestone)
    {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url+"/api/milestone/delete/"+milestone.getId(),
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

    public void setMilestoneList(MilestoneList<Milestone> milestoneList) {
        this.milestoneList = milestoneList;
    }
}
