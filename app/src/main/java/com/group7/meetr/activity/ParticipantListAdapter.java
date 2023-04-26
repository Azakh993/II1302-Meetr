
package com.group7.meetr.activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.group7.meetr.R;

import java.util.ArrayList;
import java.util.List;

public class ParticipantListAdapter extends RecyclerView.Adapter<myViewHolder> {

    Context context;
    String[] names;

    public ParticipantListAdapter(Context context, String[] names) {
        this.context = context;
        this.names = names;
    }
    public ParticipantListAdapter() {

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.participant_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nameView.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }
    public void setNames(String[] names){
        this.names = names;
    }
    public  String[] getParticipants(){
        return names;
    }
}