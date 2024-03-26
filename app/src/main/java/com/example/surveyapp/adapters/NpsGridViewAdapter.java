package com.example.surveyapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatButton;

import com.example.surveyapp.R;
import com.example.surveyapp.listeners.NpsOptionClickListener;
import com.example.surveyapp.models.QuestionResponse;

public class NpsGridViewAdapter extends BaseAdapter{
    Context ctx;
    int selectedOption=-1;
    NpsOptionClickListener npsOptionClickListener;
    QuestionResponse currentQuestionResponse;
public NpsGridViewAdapter(Context ctx, NpsOptionClickListener npsOptionClickListener,  QuestionResponse currentQuestionResponse){
    this.ctx=ctx;
    this.npsOptionClickListener=npsOptionClickListener;
    this.currentQuestionResponse=currentQuestionResponse;
}

    public void updatedSelectedOption(int position){
        if(selectedOption==position){
            this.selectedOption=-1;
        }else{
            this.selectedOption=position;
        }
       if(selectedOption!=-1) {
           currentQuestionResponse.setQuestionResponse(String.valueOf(selectedOption));
       }else{
           currentQuestionResponse.setQuestionResponse("");
       };
       Log.d("selected Opt", ""+selectedOption);
       notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return 11;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view=LayoutInflater.from(ctx).inflate(R.layout.nps_option, null);
        AppCompatButton  npsOption=view.findViewById(R.id.nps_option);
        npsOption.setText(String.valueOf(position));
        npsOption.setBackgroundColor(selectedOption!=position? Color.parseColor("#ffffff")
                :Color.parseColor("#00ff00"));
        npsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                npsOptionClickListener.onClick(position);
            }
        });
        return view;

    }
}