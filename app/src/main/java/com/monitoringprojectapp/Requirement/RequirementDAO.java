package com.monitoringprojectapp.Requirement;

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
import com.monitoringprojectapp.Database.Queue;
import com.monitoringprojectapp.Project.Project;
import com.monitoringprojectapp.Tools.RequirementList;
import com.monitoringprojectapp.Type.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequirementDAO
{
    private static final String url = "https://10.0.2.2:5001";
    private final Context ctx;
    private RequirementList<Requirement> requirements;
    private final ArrayList<Requirement> requirementList;
    private String token;

    public RequirementDAO(Context ctx)
    {
        this.ctx = ctx;
        Queue.getInstance(ctx);
        requirementList = new ArrayList<>();
    }

    public RequirementList<Requirement> getRequirementList(String token, int id)
    {
        this.token = token;
        Log.i("RequirementDAO", "getRequirementList");
        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET, url+"/api/requirement/all/"+id, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray  response)
            {
                Log.i("RequirementDAO", "onResponse");
                try
                {
                    for(int i=0;i<response.length();i++)
                    {
                        Log.i("RequirementDAO", "new requirement ");
                        JSONObject obj = response.getJSONObject(i);
                        JSONObject obj3 = obj.getJSONObject("project");
                        try
                        {
                            JSONObject obj2 = obj.getJSONObject("type");
                            requirements.add(new Requirement(Integer.parseInt(obj.getString("id")),
                                    obj.getString("identifier"), obj.getString("description"),
                                    obj.getInt("identifier"), new Project(obj3.getInt("id"),
                                    obj3.getString("name")) ,new Type(obj2.getInt("id"),
                                    obj2.getString("name"))));
                        }
                        catch (JSONException e)
                        {
                            Log.e("RequirementDAO", "JSONException " + e);
                            requirements.add(new Requirement(Integer.parseInt(obj.getString("id")),
                                    obj.getString("identifier"), obj.getString("description"),
                                    obj.getInt("identifier"), new Project(obj3.getInt("id"),
                                    obj3.getString("name")) ,new Type(0,
                                   "")));
                        }

                    }
                }
                catch (JSONException e)
                {
                    Log.e("RequirementDAO", "JSONException " + e);
                    //TODO: Handle error
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.e("RequirementDAO", "onErrorResponse " + error);

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
        return requirements;
    }

    public void insertRequirement(final Requirement requirement)
    {
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("identifier", requirement.getIdentifier());
            jsonBody.put("description", requirement.getDescription());
            jsonBody.put("is_functional", requirement.getIsFunctional());
            jsonBody.put("type", requirement.getType());
            final String mRequestBody = jsonBody.toString();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url+"/api/requirement/insert",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.i("Response", response);
                        try {
                            JSONObject mainObject = new JSONObject(response);
//                            requirements.add(new Project(Integer.parseInt(mainObject.getString("id")),mainObject.getString("name")));
                        }
                        catch (JSONException e)
                        {
                            Log.e("RequirementDAO", "JSONException " + e);
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

    public void putRequirement(final Requirement requirement)
    {
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("identifier", requirement.getIdentifier());
            jsonBody.put("description", requirement.getDescription());
            jsonBody.put("is_functional", requirement.getIsFunctional());
            jsonBody.put("type", requirement.getType());
            final String mRequestBody = jsonBody.toString();

            StringRequest putRequest = new StringRequest(Request.Method.PUT, url+"/api/requirement/edit/"+requirement.getId(),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            try {
                                JSONObject mainObject = new JSONObject(response);
//                            requirements.get(Integer.parseInt(mainObject.getString("id"))).setName(mainObject.getString("name"));
                            }
                            catch (JSONException e)
                            {
                                Log.e("RequirementDAO", "JSONException " + e);
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

    public void deleteRequirement(Requirement requirement)
    {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url+"/api/requirement/delete/"+requirement.getId(),
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

    public void setRequirements(RequirementList<Requirement> requirements) {
        this.requirements = requirements;
    }
}
