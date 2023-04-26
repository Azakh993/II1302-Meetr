package com.group7.meetr.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.group7.meetr.R;
import com.group7.meetr.data.remote.SessionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserProximityInput extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    Sensor proximitySensor;

    private SessionHandler sessionHandler;
    private String sessionId = "7";

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpagev2);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        sessionHandler = new SessionHandler();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        String timestamp = getCurrentTimeStamp();

        if (distance <= 7) {
            Toast.makeText(this, "Request Registered: " + timestamp, Toast.LENGTH_SHORT).show();
            // Send handRaise flag and timestamp to sessionHandler
            sessionHandler.sendProximityData(sessionId, true, timestamp);
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
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTimeStamp(){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
