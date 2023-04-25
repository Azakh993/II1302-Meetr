package com.group7.meetr.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.meetr.R;

public class myAdapter extends RecyclerView.Adapter<myViewHolder> {

    Context context;
    private static String[] particantsNames = new String[0]; //= {"Ahmed", "Dinho", "Baskim", "Pop", "Alice"};
    //String[] emails = {"Ahmed", "Dinho", "Baskim", "Pop", "Alice"};

    public myAdapter() {
        this.context = context;
    }

    public myAdapter(Context applicationContext, String[] participants) {
        this.context = context;
        this.particantsNames = participants;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.participant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        //holder.nameView.setText(particantsNames.get(position));
        String participantEmail = particantsNames[position];
        holder.bind(participantEmail);
    }

    @Override
    public int getItemCount() {
        return particantsNames.length;
    }

    public static void setParticipants(String[] participants) {
        particantsNames = participants;
    }
    public static String[] getParticipants(){
        return particantsNames;
    }
}