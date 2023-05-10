/**
 * This class represents the in-meeting activity screen of the application. It is responsible for
 * handling participant interaction with the application during a meeting. It implements the
 * SensorEventListener interface to monitor sensor events. Participants requests are monitored and
 * registered based on values detected by the proximity and light sensor.
 */

package com.group7.meetr.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;

import com.group7.meetr.R;
import com.group7.meetr.viewmodel.InMeetingViewModel;

public class InMeetingActivity extends AppCompatActivity implements SensorEventListener {
    private final InMeetingViewModel inMeetingViewModel = new InMeetingViewModel();
    private LiveData<Integer> currentSpeakingUser;
    private static final String CHANNEL_ID = "my_channel_01";
    private static final int NOTIFICATION_ID = 1;
    private static final long[] vibrationPattern = {0, 400, 200, 400};
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Sensor lightSensor;
    private boolean proximityFlag = false;
    private boolean lightFlag = false;
    private float lastLux = -1;

    private long lastGestureTime = 0;
    private static final long GESTURE_COOLDOWN = 2000; // 2000 milliseconds = 2 second




    private boolean gesture1ProximityFlag = false;
    private boolean gesture1LightFlag = false;
    private boolean gesture2ProximityFlag = false;
    private boolean gesture2LightFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_view);
        createNotificationChannel();

        currentSpeakingUser = inMeetingViewModel.getQueuePositionLiveData();
        currentSpeakingUser.observe(this, positionInQueue -> {
            if (positionInQueue == 2) {
                showNotification(InMeetingActivity.this, "You are next to speak, get ready!");
            } else if (positionInQueue == 1) {
                Intent intent = new Intent(InMeetingActivity.this, TalkingActivity.class);
                startActivity(intent);
                showNotification(InMeetingActivity.this, "You are currently speaking!");
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Button vib = findViewById(R.id.buttonJoin);
        vib.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(400, 10));
            inMeetingViewModel.enqueue();
        });
    }

    /**
     * onSensorChanged is called when a sensor value changes. It sets flags for the proximity
     * and light sensors, if both are triggered, the request is confirmed with a message
     * then the request is sent as a timestamp to inputViewModel.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastGestureTime < GESTURE_COOLDOWN) {
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];
            gesture1ProximityFlag = (distance >= 0) && (distance <= 8);
            gesture2ProximityFlag = (distance <= 0);
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float luxValue = event.values[0];

            if (lastLux != -1) {
                float luxChange = Math.abs((lastLux - luxValue) / luxValue * 100);
                gesture1LightFlag = (luxChange >= 10) && (luxChange < 95);
                gesture2LightFlag = (luxChange >= 95);
            }
            lastLux = luxValue;
        }

        if (gesture2ProximityFlag && gesture2LightFlag) {
            Toast.makeText(this, "Reply Registered", Toast.LENGTH_SHORT).show();
            inMeetingViewModel.enqueue();
            gesture2ProximityFlag = false;
            gesture2LightFlag = false;
            lastGestureTime = currentTime;
        } else if (gesture1ProximityFlag && gesture1LightFlag) {
            Toast.makeText(this, "Queue Request Registered", Toast.LENGTH_SHORT).show();
            inMeetingViewModel.enqueue();
            gesture1ProximityFlag = false;
            gesture1LightFlag = false;
            lastGestureTime = currentTime;
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

    public void showNotification(Context context, String message) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, TalkingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.exclamation)
                .setContentTitle("Get Ready")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setVibrate(vibrationPattern)
                .setAutoCancel(true); // Automatically removes the notification when the user taps it

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // The id of the notification.
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = "Your Channel";
        String description = "Your Channel Description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}