package com.monitoringprojectapp.User;

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
import java.util.List;
import java.util.Map;

import com.monitoringprojectapp.Database.Queue;
import com.monitoringprojectapp.Tools.VolleyCallback;

public class UserDAO
{
    private static final String url = "https://10.0.2.2:5001";
    private final Context ctx;
    private final ArrayList<User> userList;
    private List<String> array;

    public UserDAO(Context ctx)
    {
        this.ctx = ctx;
        Queue.getInstance(ctx);
        userList = new ArrayList<>();
        array = new ArrayList<>();
    }

    public List<String> getTrigramme(String token)
    {
        Log.i("UserDAO", "getTrigramme");

        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET, url+"/api/user/tri", null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray  response)
            {
                Log.i("Response 2", response.toString());
                try
                {
                    for(int i=0;i<response.length();i++)
                    {
                        Log.i("UserDAO", "new trigramme");
                        JSONObject obj = response.getJSONObject(i);
                        array.add(obj.getString("trigramme"));
                    }
                }
                catch (JSONException e)
                {
                    Log.i("UserDAO", e.toString());
                    //TODO: Handle error
                }

            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                // TODO: Handle error
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
        return array;
    }

    public void login(final String username, final String password, final VolleyCallback callback)  {
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("login", username);
            jsonBody.put("password", password);
            final String mRequestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,  url+"/api/user/login", new Response.Listener<String>()
            {
                @Override
                public void onResponse(String   response)
                {
                    // response
                    Log.i("Response", response.toString());
                    try
                    {
                        callback.onSuccess(response.toString());
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
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
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

            };
            Queue.getInstance(ctx).addToRequestQueue(stringRequest);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
