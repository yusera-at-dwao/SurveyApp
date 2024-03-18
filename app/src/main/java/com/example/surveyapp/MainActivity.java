package com.example.surveyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.surveyapp.network.FetchJson;
import com.example.surveyapp.network.VolleyService;
import com.example.surveyapp.survey.ShowSurvey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
JSONObject json=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        VolleyService.callVolley(this);
//        try {
//            VolleyService volleyService=new VolleyService();
//            String url= "https://assets.alium.co.in/cmmn/cstjn/cstjn_1038.json";
//            volleyService.callVolley(this,url );
//           json= FetchJson.getFromLocal(getAssets().open("survey_details.json"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if(json!=null){
        String url= "https://assets.alium.co.in/cmmn/cstjn/cstjn_1038.json";
           new ShowSurvey(this, url);

//        }

    }
}