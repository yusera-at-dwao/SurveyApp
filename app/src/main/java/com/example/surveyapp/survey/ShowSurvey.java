package com.example.surveyapp.survey;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.surveyapp.R;
import com.example.surveyapp.adapters.CheckBoxRecyViewAdapter;
import com.example.surveyapp.adapters.NpsGridViewAdapter;
import com.example.surveyapp.adapters.NpsOptionsAdapter;
import com.example.surveyapp.adapters.RadioBtnAdapter;
import com.example.surveyapp.listeners.CheckBoxClickListener;
import com.example.surveyapp.listeners.NpsOptionClickListener;
import com.example.surveyapp.listeners.RadioClickListener;
import com.example.surveyapp.listeners.VolleyResponseListener;
import com.example.surveyapp.network.VolleyService;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShowSurvey implements VolleyResponseListener {
    private View layoutView;
    private RelativeLayout layout;
    private RadioBtnAdapter adapter;
    private NpsOptionsAdapter npsOptionsAdapter;
    private NpsGridViewAdapter npsGridViewAdapter;
    private Dialog dialog;
    private CheckBoxRecyViewAdapter checkBoxRecyViewAdapter;
//    private static ShowSurvey instance;
    private MaterialButton  nextQuestion;
    private  AppCompatImageView closeDialogBtn;
    private int currentIndx=0;
    private  Context context;
    private  JSONArray surveyQuestions;
    private  JSONObject surveyUi;

    public ShowSurvey(Context ctx, String url){
        this.context=ctx;
        VolleyService volleyService=new VolleyService();
        volleyService.callVolley(ctx,url ,this::onResponseReceived );

    }
    public void show(){
        dialog=new Dialog(context);
//        GradientDrawable gradientDrawable=(GradientDrawable) layoutView.getBackground();
//        gradientDrawable.setStroke((int)(2* Resources.getSystem().getDisplayMetrics().density), Color.BLUE);
//        gradientDrawable.setCornerRadius(10);
        nextQuestion=layoutView.findViewById(R.id.btn_next);
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndx++;
                handleNextQuestion();
            }
        });
        closeDialogBtn=layoutView.findViewById(R.id.close_dialog_btn);
        closeDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitSurvey();
            }
        });
        if(surveyQuestions.length()>0 && currentIndx==0) showCurrentQuestion();
        dialog.setContentView(this.layoutView);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.horizontalMargin= Gravity.apply();
//
//        dialog.getWindow().setAttributes();
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
//                AlertDialog.Builder builder=new AlertDialog.Builder(this);
//                builder.setView(layoutView);
//                builder.create().show();
    }

    @Override
    public void onResponseReceived(JSONObject json) {
        Log.d("response from", json.toString());
//        try {
//            Log.d("response-object", json.getJSONObject("1197").getString("orgId"));
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        String checkURL="https://alium.co.in/survey/Dashboard/index";
        surveyResponse(json, checkURL);
    }

    private void surveyResponse(JSONObject response, String checkURL) {
        Log.d("Target2", checkURL);
        Iterator<String> keys = response.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            try {
                JSONObject jsonObject = response.getJSONObject(key);
                JSONObject ppupsrvObject = jsonObject.getJSONObject("ppupsrv");
                String urlValue = ppupsrvObject.getString("url");
                Log.d("Target2", "Key: " + key + ", URL: " + urlValue);
                if (checkURL.equals(urlValue)){
                    Log.e("True","True");
                    loadSurvey(key);
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadSurvey(String key) {
        String surURL="https://assets.alium.co.in/cmmn/cqsjn/cqsjn_1038_" +key + ".json";
        VolleyResponseListener volleyResponseListener2=new VolleyResponseListener() {
            @Override
            public void onResponseReceived(JSONObject json) {
                Log.d("survey loaded", json.toString());
                try {
                    surveyQuestions=json.getJSONArray("surveyQuestions");
                    if(json.has("surveyUI")){
                        surveyUi=json.getJSONObject("surveyUI");
                    }

                    layoutView= LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
//                    Drawable dialogBg=context.getDrawable(R.drawable.dialog_bg);
//
//                    layoutView.setBackgroundResource(R.drawable.dialog_bg);
                    layout= layoutView.findViewById(R.id.dialog_layout_content);
                    if(surveyUi!=null)layoutView.setBackgroundColor(Color.argb(255,Integer.parseInt(surveyUi.getString("rBackground")),
                            Integer.parseInt(surveyUi.getString("gBackground")), Integer.parseInt(surveyUi.getString("bBackground"))));
                    show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        VolleyService volleyService=new VolleyService();
        volleyService.callVolley(context, surURL,volleyResponseListener2 );
    }
    private void handleNextQuestion(){
        if(surveyQuestions.length()>0 && currentIndx< surveyQuestions.length()){
            this.layout.removeAllViews();
            showCurrentQuestion();
        }else if(currentIndx==surveyQuestions.length()){
            //show thank you msg
            Toast.makeText(context, "Your response has been submitted!!", Toast.LENGTH_LONG).show();
            submitSurvey();
        }
    }
    private void submitSurvey(){
        dialog.dismiss();
    }
    private void showCurrentQuestion(){
        try {

                if(surveyQuestions.getJSONObject(currentIndx).getString("responseType").equals("1")){
                    View longtextQues= LayoutInflater.from(context).inflate(R.layout.long_text_ques, null);
                    AppCompatTextView quest=longtextQues.findViewById(R.id.question);
                    quest.setText(surveyQuestions.getJSONObject(currentIndx).getString("question"));
                    if(surveyUi!=null)quest.setTextColor(Color.argb(255,Integer.parseInt(surveyUi.getString("rText")),
                            Integer.parseInt(surveyUi.getString("gText")), Integer.parseInt(surveyUi.getString("bText"))));
                    this.layout.addView(longtextQues);
                }


            else if(surveyQuestions.getJSONObject(currentIndx).getString("responseType").equals("2")){

                JSONArray responseOptJSON=surveyQuestions.getJSONObject(currentIndx).getJSONArray("responseOptions");
                List<String> responseOptions=new ArrayList<>();
                for (int i=0; i<responseOptJSON.length(); i++){
                    responseOptions.add(responseOptJSON.getString(i));
                }
                View radioQues= LayoutInflater.from(context).inflate(R.layout.radio_ques, null);
                AppCompatTextView quest=radioQues.findViewById(R.id.question);
                quest.setText(surveyQuestions.getJSONObject(currentIndx).getString("question"));
                    if(surveyUi!=null)quest.setTextColor(Color.argb(255,Integer.parseInt(surveyUi.getString("rText")),
                            Integer.parseInt(surveyUi.getString("gText")), Integer.parseInt(surveyUi.getString("bText"))));

                    RecyclerView radioBtnRecyView=radioQues.findViewById(R.id.radio_btn_rec_view);
                radioBtnRecyView.setLayoutManager(new LinearLayoutManager(context));

                RadioClickListener radioClickListener=new RadioClickListener() {
                    @Override
                    public void onClick(int position) {
                        radioBtnRecyView.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.updateCheckedItem(position);
                            }
                        });
                    }
                };
                this.adapter=new RadioBtnAdapter(responseOptions,radioClickListener );
                radioBtnRecyView.setAdapter(adapter);

                this.layout.addView(radioQues);
            }
            else
                if(surveyQuestions.getJSONObject(currentIndx).getString("responseType").equals("3")){
                    JSONArray responseOptJSON=surveyQuestions.getJSONObject(currentIndx).getJSONArray("responseOptions");
                    List<String> responseOptions=new ArrayList<>();
                    for (int i=0; i<responseOptJSON.length(); i++){
                        responseOptions.add(responseOptJSON.getString(i));
                    }
                    View checkBoxQues= LayoutInflater.from(context).inflate(R.layout.checkbox_type_ques, null);
                    AppCompatTextView quest=checkBoxQues.findViewById(R.id.question);
                    quest.setText(surveyQuestions.getJSONObject(currentIndx).getString("question"));
                    if(surveyUi!=null)quest.setTextColor(Color.argb(255,Integer.parseInt(surveyUi.getString("rText")),
                            Integer.parseInt(surveyUi.getString("gText")), Integer.parseInt(surveyUi.getString("bText"))));

                    RecyclerView recyclerView=checkBoxQues.findViewById(R.id.checkbox_recy_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    CheckBoxClickListener checkBoxClickListener=new CheckBoxClickListener() {
                        @Override
                        public void onClick(int position, boolean selected) {
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    checkBoxRecyViewAdapter.updateCheckedItem(position, selected);
                                }
                            });
                        }
                    };
                    checkBoxRecyViewAdapter=new CheckBoxRecyViewAdapter(responseOptions, checkBoxClickListener);
                    recyclerView.setAdapter(checkBoxRecyViewAdapter);
                    this.layout.addView(checkBoxQues);
                }else  if(surveyQuestions.getJSONObject(currentIndx).getString("responseType").equals("4")){
                    View npsQues= LayoutInflater.from(context).inflate(R.layout.nps_ques, null);
                    AppCompatTextView quest=npsQues.findViewById(R.id.question);
                    quest.setText(surveyQuestions.getJSONObject(currentIndx).getString("question"));
                    if(surveyUi!=null)quest.setTextColor(Color.argb(255,Integer.parseInt(surveyUi.getString("rText")),
                            Integer.parseInt(surveyUi.getString("gText")), Integer.parseInt(surveyUi.getString("bText"))));

                    GridView npsRecView=npsQues.findViewById(R.id.nps_recy_view);
                    NpsOptionClickListener listener=new NpsOptionClickListener() {
                        @Override
                        public void onClick(int position) {
                            npsRecView.post(new Runnable() {
                                @Override
                                public void run() {
                                    npsGridViewAdapter.updatedSelectedOption(position);
//                                    npsOptionsAdapter.updatedSelectedOption(position);
                                }
                            });
                        }
                    };
                    npsOptionsAdapter=new NpsOptionsAdapter(listener);
                    npsGridViewAdapter=new NpsGridViewAdapter(context, listener);
                    npsRecView.setAdapter( npsGridViewAdapter);
                    this.layout.addView(npsQues);
                }



        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


//    public static void createSurvey(Context ctx, JSONObject json){
//        if(instance==null){
//            instance=new ShowSurvey();
//        }
//        try {
//            surveyQuestions=json.getJSONArray("surveyQuestions");
//            surveyUi=json.getJSONObject("surveyUI");
//            context=ctx;
//
//            instance.layoutView= LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
//            instance.layout= instance.layoutView.findViewById(R.id.dialog_layout_content);
//            instance.layoutView.setBackgroundColor(Color.argb(255,Integer.parseInt(surveyUi.getString("rBackground")),
//                    Integer.parseInt(surveyUi.getString("gBackground")), Integer.parseInt(surveyUi.getString("bBackground"))));
//            instance.show( );
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//    }


    private void updateSurveyUi(){

    }

}
