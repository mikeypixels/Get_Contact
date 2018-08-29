package com.example.michael.getcontact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Main2Activity extends AppCompatActivity {

    RadioButton radiobtn1, radiobtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        radiobtn1 = findViewById(R.id.radiobtn1);
        radiobtn2 = findViewById(R.id.radiobtn2);

        radiobtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radiobtn2.setChecked(false);
            }
        });

        radiobtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radiobtn1.setChecked(false);
            }
        });
    }
}
