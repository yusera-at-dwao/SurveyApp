package com.example.surveyapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyapp.R;
import com.example.surveyapp.listeners.CheckBoxClickListener;
import com.example.surveyapp.models.QuestionResponse;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxRecyViewAdapter extends RecyclerView.Adapter<CheckBoxRecyViewAdapter.ViewHolder> {

    List<String> checkBoxList;
    CheckBoxClickListener listener;
    List<Integer> selectedItems;
    QuestionResponse currentQuestionResponse;
    public CheckBoxRecyViewAdapter( List<String> checkBoxList,
    CheckBoxClickListener listener, QuestionResponse currentQuestionResponse){
        this.checkBoxList=checkBoxList;
        this.listener=listener;
        selectedItems=new ArrayList<>();
        this.currentQuestionResponse=currentQuestionResponse;
    }
    private void updateResponseString(){
        String resp="";
        for(int i=0; i< selectedItems.size(); i++){
            if(i==selectedItems.size()-1){
                resp+= checkBoxList.get(selectedItems.get(i));
                break;
            }
           resp+= checkBoxList.get(selectedItems.get(i))+",";
        }
        currentQuestionResponse.setQuestionResponse(resp);
        Log.d("response changed", currentQuestionResponse.getQuestionResponse());
    }
    public void updateCheckedItem(int pos, boolean selected){
      if(selected){
          this.selectedItems.add(pos);

      }else {
          this.selectedItems.remove(Integer.valueOf(pos));
      }
        updateResponseString();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CheckBoxRecyViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_btn, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckBoxRecyViewAdapter.ViewHolder holder, int position) {
        holder.checkBox.setText(checkBoxList.get(position));
        holder.checkBox.setChecked(selectedItems.contains(position));
        Log.d("pos"+position, "pos: "+checkBoxList.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//           if(selectedItems.contains(Integer.valueOf(holder.getAdapterPosition())))
               listener.onClick(holder.getAdapterPosition(), b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.checkBoxList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatCheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox_btn);
        }
    }
}
