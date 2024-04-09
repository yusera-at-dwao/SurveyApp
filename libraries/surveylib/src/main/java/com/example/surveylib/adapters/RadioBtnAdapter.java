package com.example.surveylib.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.surveylib.R;
import com.example.surveylib.listeners.RadioClickListener;
import com.example.surveylib.models.QuestionResponse;

import java.util.List;

public class RadioBtnAdapter extends RecyclerView.Adapter<RadioBtnAdapter.ViewHolder> {
     int selectedPosition;
    List<String> radioBtnList;
    RadioClickListener radioClickListener;
    QuestionResponse currentQuestionResponse;
     public RadioBtnAdapter(List<String> radioBtnList, RadioClickListener radioClickListener, QuestionResponse currentQuestionResponse){
         this.currentQuestionResponse=currentQuestionResponse;
        this.radioBtnList=radioBtnList;
        this.radioClickListener=radioClickListener;
        this.selectedPosition=-1;
    }
    public void updateCheckedItem(int selectedPosition){
        this.selectedPosition=selectedPosition;
        currentQuestionResponse.setQuestionResponse(radioBtnList.get(selectedPosition));
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_btn, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.radioButton.setText(radioBtnList.get(position));
        holder.radioButton.setChecked(position==selectedPosition);
        holder.radioButton.setButtonTintList(new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        }, new int[]{
                Color.GRAY,
                Color.BLUE
        }));
        Log.d("pos"+position, "pos: "+radioBtnList.get(position));
        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

               if(b){
                   radioClickListener.onClick(holder.getAdapterPosition());
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return radioBtnList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton=itemView.findViewById(R.id.radio_btn);
        }
    }
}
