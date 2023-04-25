package com.group7.meetr.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group7.meetr.R;

public class myViewHolder extends RecyclerView.ViewHolder {


    TextView nameView;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.participant_name);
    }

    public void bind(String participantEmail) {
        nameView.setText(participantEmail);
    }
}
