package com.group7.meetr.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.group7.meetr.R;

import java.util.List;

public class QueuingListAdapter extends RecyclerView.Adapter<QueuingListAdapter.ViewHolder> {
    private List<String> queueList;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     */
    public QueuingListAdapter(@NonNull List<String> queue) {
        queueList = queue;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.participant_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * This method is responsible for binding the data (list from firebase)
     * to the views that represent each item in the list.
     * @param holder a reference of the viewHolder class that holds the views for each item in the list.
     * @param position The position parameter indicates the position of the current item in the list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(queueList.get(position));
    }

    @Override
    public int getItemCount() {
        return queueList.size();
    }

    /**
     * The viewHolder class that holds the views for each item in the list.
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.participant_name);
        }
    }
}