package com.example.surveyapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyapp.R;
import com.example.surveyapp.listeners.NpsOptionClickListener;

public class NpsOptionsAdapter extends RecyclerView.Adapter<NpsOptionsAdapter.ViewHolder> {
    NpsOptionClickListener npsOptionClickListener;
    public NpsOptionsAdapter(NpsOptionClickListener npsOptionClickListener){
        this.npsOptionClickListener=npsOptionClickListener;
    }
    int selectedOption=-1;
    public void updatedSelectedOption(int position){
        if(selectedOption==position){
            this.selectedOption=-1;
        }else{
            this.selectedOption=position;
        }

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NpsOptionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.nps_option, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NpsOptionsAdapter.ViewHolder holder, int position) {
        holder.npsOption.setText(String.valueOf(position+1));
        holder.npsOption.setBackgroundColor(selectedOption!=position?Color.parseColor("#55ffffff")
                :Color.parseColor("#00ff00"));
        holder.npsOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              npsOptionClickListener.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton npsOption;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            npsOption=itemView.findViewById(R.id.nps_option);
        }
    }
}
