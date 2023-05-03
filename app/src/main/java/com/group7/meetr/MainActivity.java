package com.group7.meetr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;

import com.group7.meetr.activity.LoginPageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
        startActivity(intent);
    }
    public void onStart() {
        super.onStart();
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