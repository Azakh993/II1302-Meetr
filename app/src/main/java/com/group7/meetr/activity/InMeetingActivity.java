/**
 * This class represents the in-meeting activity screen of the application. It is responsible for
 * handling participant interaction with the application during a meeting. It implements the
 * SensorEventListener interface to monitor sensor events. Participants requests are monitored and
 * registered based on values detected by the proximity and light sensor.
 */

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
import com.group7.meetr.data.remote.FirebaseFunctionsManager;
import com.group7.meetr.viewmodel.InputViewModel;
import com.group7.meetr.viewmodel.LoginPageViewModel;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class InMeetingActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor proximitySensor;
    Sensor lightSensor;

    private boolean proximityFlag = false;
    private boolean lightFlag = false;

    private InputViewModel inputViewModel = new InputViewModel();

    private float lastLux = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

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

    /**
     * onSensorChanged is called when a sensor value changes. It sets flags for the proximity
     * and light sensors, if both are triggered, the request is confirmed with a message
     * then the request is sent as a timestamp to inputViewModel.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        long timestamp = System.currentTimeMillis();

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            float distance = event.values[0];
            proximityFlag = (distance <= 7);
        } else if (event.sensor.getType()  == Sensor.TYPE_LIGHT){
            float luxValue = event.values[0];

            if(lastLux != -1){
                float luxChange = Math.abs((lastLux - luxValue) / luxValue * 100);
                if (luxChange >= 20){
                    lightFlag = true;
                }
            }
            lastLux = luxValue;
        }

        if (proximityFlag && lightFlag) {
            Toast.makeText(this, "Request Registered: " + timestamp, Toast.LENGTH_SHORT).show();
            inputViewModel.receiveSensorInput(timestamp);
            proximityFlag = false;
            lightFlag = false;
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
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}