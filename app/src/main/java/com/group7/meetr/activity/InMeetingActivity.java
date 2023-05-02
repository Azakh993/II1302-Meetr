package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.group7.meetr.R;
import com.group7.meetr.data.model.Participant;
import com.group7.meetr.data.remote.FirebaseFunctionsManager;
import com.group7.meetr.viewmodel.InputViewModel;
import com.group7.meetr.viewmodel.LoginPageViewModel;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMeetingActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor proximitySensor;

    private InputViewModel inputViewModel = new InputViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        Button vib = findViewById(R.id.buttonJoin);
        Vibrator vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibr.vibrate(400);
                Intent intent;
                FirebaseFunctionsManager.callEnqueue("7", LoginPageViewModel.getCurrentUser().getEmail(), System.currentTimeMillis());
                ArrayList<Object> obj = FirebaseFunctionsManager.callGetSpeakingQueue("7");
                intent = new Intent(InMeetingActivity.this, TalkingActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        long timestamp = System.currentTimeMillis();

        if (distance <= 7) {
            Toast.makeText(this, "Request Registered: " + timestamp, Toast.LENGTH_SHORT).show();
            inputViewModel.receiveProximityInput(timestamp);
        }
        else {
            Toast.makeText(this, "Request Not Registered: " + timestamp, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}