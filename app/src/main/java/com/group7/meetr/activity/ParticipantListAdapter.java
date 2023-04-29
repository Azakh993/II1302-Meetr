
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

import java.util.Arrays;

public class ParticipantListAdapter extends RecyclerView.Adapter<ParticipantListAdapter.myViewHolder> {

    Context context;
   String[] names;
   // List<Participant> names;

    public ParticipantListAdapter(Context context, String[] names) {
        this.context = context;
        this.names = names;
    }

    public ParticipantListAdapter(){
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.participant_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        //Participant participant = names[position];
        holder.nameView.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public String[] setUsers(String[] names){
        this.names = names;
        Log.d("Participants LIST", "setNames: " + Arrays.toString(names));
        notifyDataSetChanged();
        return this.names;
    }

    public String[] getNames(){
    Log.d("Participants LIST", "setNames: " + Arrays.toString(this.names));
      return this.names;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {


        TextView nameView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.participant_name);
        }
    }

}