package com.group7.meetr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.group7.meetr.R;

public class OptionActivity extends AppCompatActivity {

    private ToggleButton toggleAlgorithm;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        toggleAlgorithm = findViewById(R.id.toggle_algorithm);
        toggleAlgorithm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //TODO: Enable inclusive queue to this
                }
                else {
                    //TODO: Enable FIFO queue to this
                }
            }
        });

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(OptionActivity.this, ModeratorActivity.class);
                startActivity(intent);
            }
        });
    }
}