package com.group7.meetr.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.group7.meetr.R;

public class ParticipantListAdapter  extends FirebaseRecyclerAdapter<String,ParticipantListAdapter.myViewHolder > {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ParticipantListAdapter(@NonNull FirebaseRecyclerOptions<String> options) {
        super(options);
    }

    /**
     * This method is responsible for binding the data (list from firebase)
     * to the views that represent each item in the list.
     * @param holder a reference of the viewHolder class that holds the views for each item in the list.
     * @param position The position parameter indicates the position of the current item in the list
     * @param emails the model object containing the data that should be used to populate the view.
     */
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull String emails) {
        holder.nameView.setText(emails);
    }

    /**
     * This method is responsible for inflating the layout
     * for each item in the list and creating a ViewHolder object to hold the views for the item.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return a new viewHolder object with the data
     */
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_item, parent, false);
        return new myViewHolder(view);
    }

    /**
     * The viewHolder class that holds the views for each item in the list.
     */
    class myViewHolder extends RecyclerView.ViewHolder{

        TextView nameView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.participant_name);
        }
    }
}