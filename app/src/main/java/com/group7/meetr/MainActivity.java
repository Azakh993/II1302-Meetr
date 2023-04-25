package com.group7.meetr;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.group7.meetr.activity.myAdapter;
import com.group7.meetr.data.remote.FirebaseRealtimeDatabase;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {



    private myAdapter mParticipantsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewModel updates the Model
        // after observing changes in the View

        // model will also update the view
        // via the ViewModel

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://meetr-android-default-rtdb.europe-west1.firebasedatabase.app/");

        //Loginpagev2Binding activityMainBinding = DataBindingUtil.setContentView(
          //      this, R.layout.loginpagev2);
        //LoginPageViewModel lpvm = new LoginPageViewModel(database);
        //activityMainBinding.setViewModel(lpvm);
        //activityMainBinding.executePendingBindings();


        String[] names = {"AHmed", "Bashir", "Ibrahim", "Bakal"};

            Log.d("Participant List", ": names:  " + Arrays.toString(names));


        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new myAdapter(getApplicationContext(), myAdapter.getParticipants()));
        //recyclerView.setAdapter(mParticipantsAdapter);

        //TODO: Move this code to a more appropriate place.
        FirebaseRealtimeDatabase realtimeDatabase = new FirebaseRealtimeDatabase(database, "7");
        realtimeDatabase.addParticipantsListener();


    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    @BindingAdapter({ "toastMessage" })
    public static void runMe(View view, String message)
    {
        if (message != null)
            Toast
                    .makeText(view.getContext(), message,
                            Toast.LENGTH_SHORT)
                    .show();
    }
}