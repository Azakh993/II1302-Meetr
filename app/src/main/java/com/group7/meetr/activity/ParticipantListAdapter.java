
package com.group7.meetr.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.meetr.R;

import java.util.List;

public class ParticipantListAdapter extends RecyclerView.Adapter<ParticipantListAdapter.myViewHolder> {

    Context context;
    List<String> names;

    public ParticipantListAdapter(Context context, List<String> names) {
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
        holder.nameView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
    public void setNames(List<String> names){
        this.names = names;
        Log.d("Participants LIST", "setNames: " + names);
    }
    public  List<String>  getParticipants(){
        return names;
    }


    public class myViewHolder extends RecyclerView.ViewHolder {


        TextView nameView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.participant_name);
        }
    }

}