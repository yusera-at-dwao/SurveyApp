package com.example.surveyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.surveyapp.R;
import com.example.surveylib.survey.ShowSurvey;

public class TestActivity extends AppCompatActivity {
    TextView next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        next=findViewById(R.id.next);
        String url= "https://assets.alium.co.in/cmmn/cstjn/cstjn_1038.json";
        new ShowSurvey(this, url, this.getTitle().toString());
    }
}