package com.example.surveyapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyapp.R;
import com.example.surveyapp.listeners.RadioClickListener;

import java.util.List;

public class RadioBtnAdapter extends RecyclerView.Adapter<RadioBtnAdapter.ViewHolder> {
     int selectedPosition;
    List<String> radioBtnList;
    RadioClickListener radioClickListener;
     public RadioBtnAdapter(List<String> radioBtnList, RadioClickListener radioClickListener){
        this.radioBtnList=radioBtnList;
        this.radioClickListener=radioClickListener;
    }
    public void updateCheckedItem(int selectedPosition){
        this.selectedPosition=selectedPosition;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RadioBtnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.radio_btn, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RadioBtnAdapter.ViewHolder holder, int position) {
        holder.radioButton.setText(radioBtnList.get(position));
        holder.radioButton.setChecked(position==selectedPosition);
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