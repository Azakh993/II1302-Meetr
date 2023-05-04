/**
 * This class represents the in-meeting activity screen of the application. It is responsible for
 * handling participant interaction with the application during a meeting. It implements the
 * SensorEventListener interface to monitor sensor events. Participants requests are monitored and
 * registered based on values detected by the proximity and light sensor.
 */

package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import com.group7.meetr.R;
import com.group7.meetr.data.remote.FirebaseFunctionsManager;
import com.group7.meetr.viewmodel.InMeetingViewModel;
import com.group7.meetr.viewmodel.LoginPageViewModel;
import com.group7.meetr.viewmodel.QueueListViewModel;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.ArrayList;

public class InMeetingActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor proximitySensor;
    Sensor lightSensor;

    private boolean proximityFlag = false;
    private boolean lightFlag = false;

    private InMeetingViewModel inputViewModel = new InMeetingViewModel();

    private float lastLux = -1;
    private LiveData<Integer> currentSpeakingUser;

    private static final String CHANNEL_ID = "my_channel_01";
    private static final  int NOTIFICATION_ID = 1;
    private static final long[] vibrationPattern = {0, 400, 200, 400};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_view);
        createNotificationChannel();
        currentSpeakingUser = InMeetingViewModel.getLiveData();
        currentSpeakingUser.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 2){
                    showNotification(InMeetingActivity.this, "You are next to speak, get ready!");
                } else if (integer == 1) {
                    Intent intent;
                    intent = new Intent(InMeetingActivity.this, TalkingActivity.class);
                    startActivity(intent);
                    showNotification(InMeetingActivity.this, "You are currently speaking!");

                }
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        QueueListViewModel queueListViewModel = new QueueListViewModel();
        queueListViewModel.indexObserver();

        Button vib = findViewById(R.id.buttonJoin);
        vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFunctionsManager.callEnqueue("7",LoginPageViewModel.getCurrentUser().getEmail(), System.currentTimeMillis());
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
            inputViewModel.receiveProximityInput(timestamp);
            proximityFlag = false;
            lightFlag = false;
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
        PendingIntent pendingIntent;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
    public void setCurrentQueue(ArrayList<Object> queue){
        Vibrator vibr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        showNotification(InMeetingActivity.this, "It's your turn");

        vibr.vibrate(400);
        Intent intent;
        FirebaseFunctionsManager.callEnqueue("7", LoginPageViewModel.getCurrentUser().getEmail(), System.currentTimeMillis());
        intent = new Intent(InMeetingActivity.this, TalkingActivity.class);
        startActivity(intent);
    }
}