package com.monitoringprojectapp.Task;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.monitoringprojectapp.Milestone.Milestone;
import com.monitoringprojectapp.Project.Project;
import com.monitoringprojectapp.Database.Queue;
import com.monitoringprojectapp.Requirement.Requirement;
import com.monitoringprojectapp.Tools.RequirementList;
import com.monitoringprojectapp.Tools.TaskList;
import com.monitoringprojectapp.Type.Type;
import com.monitoringprojectapp.User.User;

import static java.lang.Integer.parseInt;

public class TaskDAO {
    private static final String url = "https://10.0.2.2:5001";
    private final Context ctx;
    private TaskList<Task> taskList;
    private String token;

    public TaskDAO(Context ctx)
    {
        this.ctx = ctx;
        Queue.getInstance(ctx);
    }

    public TaskList<Task> getTaskByMilestoneList(String token, int id)
    {
        this.token = token;
        Log.i("TaskDAO", "getTaskList");
        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET, url+"/api/task/milestone/"+id, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray  response)
            {
                Log.i("TaskDAO", "onResponse");
                try
                {
                    for(int i=0;i<response.length();i++)
                    {
                        Log.i("TaskDAO", "new task ");
                        JSONObject obj = response.getJSONObject(i);
                        JSONObject obj2 = obj.getJSONObject("user");
                        JSONObject obj3 = obj.getJSONObject("milestone");

                        String dtStart = obj.getString("theoreticalStartDate");
                        String rdtStart = obj.getString("realStartDate");
                        String exdateDelivery = obj3.getString("expectedDeliveryDate");
                        String realDelivery = obj3.getString("realDeliveryDate");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date tdate = null;
                        Date rdate = null;
                        Date exdate = null;
                        Date realdate = null;
                        try {
                            tdate = format.parse(dtStart);
                            rdate = format.parse(rdtStart);
                            exdate = format.parse(exdateDelivery);
                            realdate = format.parse(realDelivery);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        taskList.add(new Task(Integer.parseInt(obj.getString("id")),
                                obj.getString("label"), obj.getString("description"),
                                obj.getString("identifier"),
                                new User(obj2.getInt("id"), obj2.getString("login")),
                                tdate, Integer.parseInt(obj.getString("load")),
                                rdate, new Task(0),
                                new Milestone(obj3.getInt("id"), obj3.getString("label"),
                                        new User(0, ""), exdate, realdate)));
                    }
                }
                catch (JSONException e)
                {
                    Log.e("TaskDAO", "JSONException " + e);
                    //TODO: Handle error
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("TaskDAO", "onErrorResponse " + error);

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
        return taskList;
    }

    public TaskList<Task> getTaskList(String token, int id)
    {
        this.token = token;
        Log.i("TaskDAO", "getTaskList");
        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET, url+"/api/task/all/"+id, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray  response)
            {
                Log.i("TaskDAO", "onResponse");
                try
                {
                    for(int i=0;i<response.length();i++)
                    {
                        Log.i("TaskDAO", "new task ");
                        JSONObject obj = response.getJSONObject(i);
                        JSONObject obj2 = obj.getJSONObject("user");
                        JSONObject obj3 = obj.getJSONObject("milestone");

                        String dtStart = obj.getString("theoreticalStartDate");
                        String rdtStart = obj.getString("realStartDate");
                        String exdateDelivery = obj3.getString("expectedDeliveryDate");
                        String realDelivery = obj3.getString("realDeliveryDate");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date tdate = null;
                        Date rdate = null;
                        Date exdate = null;
                        Date realdate = null;
                        try {
                            tdate = format.parse(dtStart);
                            rdate = format.parse(rdtStart);
                            exdate = format.parse(exdateDelivery);
                            realdate = format.parse(realDelivery);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        taskList.add(new Task(Integer.parseInt(obj.getString("id")),
                                obj.getString("label"), obj.getString("description"),
                                obj.getString("identifier"),
                                new User(obj2.getInt("id"), obj2.getString("login")),
                                tdate, Integer.parseInt(obj.getString("load")),
                                rdate, new Task(0),
                                new Milestone(obj3.getInt("id"), obj3.getString("label"),
                                new User(0, ""), exdate, realdate)));
                    }
                }
                catch (JSONException e)
                {
                    Log.e("TaskDAO", "JSONException " + e);
                    //TODO: Handle error
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("TaskDAO", "onErrorResponse " + error);

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
        return taskList;
    }

    public void insertTask(final Task advert)
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url+"/advert/insert",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject mainObject = new JSONObject(response);
//                            taskList.add(new Task(Integer.parseInt(mainObject.getString("id")),
//                                    mainObject.optString ("title"),
//                                    mainObject.optString ("content"),
//                                    mainObject.optString ("author"),
//                                    mainObject.optString ("email"),
//                                    Float.parseFloat(mainObject.optString ("price")),
//                                    mainObject.optString ("state"),
//                                    Calendar.getInstance().getTime(),
//                                    ));
                        }
                        catch (JSONException ignored)
                        {
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
//                params.put("title", advert.getTitle());
//                params.put("content", advert.getContent());
//                params.put("author", advert.getAuthor());
//                params.put("email", advert.getEmail());
//                params.put("price", Float.toString(advert.getPrice()));
//                params.put("state", "draft");
//                params.put("createdAt", Calendar.getInstance().getTime().toString());
//                params.put("category", Integer.toString(advert.getCategory().getId()));

                return params;
            }
        };
        Queue.getInstance(ctx).addToRequestQueue(postRequest);
    }

    public void putTask(final Task advert)
    {
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url+"advert/edit/"+advert.getId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // response
                        Log.d("Response", response);
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
//                params.put("title", advert.getTitle());
//                params.put("content", advert.getContent());
//                params.put("author", advert.getAuthor());
//                params.put("email", advert.getEmail());
//                params.put("price", Float.toString(advert.getPrice()));
//                params.put("createdAt", Calendar.getInstance().getTime().toString());
//                params.put("category", Integer.toString(advert.getCategory().getId()));

                return params;
            }

        };

        Queue.getInstance(ctx).addToRequestQueue(putRequest);
    }

    public void setTaskList(TaskList<Task> taskList) {
        this.taskList = taskList;
    }
}
