package com.example.surveyapp.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.surveyapp.listeners.VolleyResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyService {
   public  void callVolley(Context context, String url, VolleyResponseListener volleyResponseListener){
       // Instantiate the RequestQueue.
       RequestQueue queue = Volley.newRequestQueue(context);
//       String url = "https://assets.alium.co.in/cmmn/cstjn/cstjn_1038.json";

       // Request a string response from the provided URL.
       StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONObject jsonObject=new JSONObject(response);
                           volleyResponseListener.onResponseReceived(jsonObject);

                       } catch (JSONException e) {
                           throw new RuntimeException(e);
                       }
                       // Display the first 500 characters of the response string.
                       Log.d("response", response);
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.d("error", error.toString());
           }
       });

// Add the request to the RequestQueue.
       queue.add(stringRequest);
   }
}
