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

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserProximityInput extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    Sensor proximitySensor;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpagev2);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        String ts = getCurrentTimeStamp();
        if (distance > 5) {
            Toast.makeText(this, "Object is far: " + ts, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Object is close: " + ts, Toast.LENGTH_SHORT).show();

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
