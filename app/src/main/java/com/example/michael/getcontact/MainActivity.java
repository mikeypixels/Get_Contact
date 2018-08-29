package com.example.michael.getcontact;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView sign_up,logo,login_txt;
    CardView login_btn;
    ConstraintLayout login_layout;
    LinearLayout txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_up = findViewById(R.id.sign_up);
        login_btn=findViewById(R.id.login_btn);
        logo=findViewById(R.id.logo);
        login_txt=findViewById(R.id.login_txt);
        login_layout=findViewById(R.id.login_layout);
        txt=findViewById(R.id.txt);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main4Activity.class);
                startActivity(intent);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.up_to_down);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.down_to_up);
        Animation animation3 = AnimationUtils.loadAnimation(this,R.anim.real_up_to_down);
        logo.setAnimation(animation3);
        login_btn.setAnimation(animation2);
        login_txt.setAnimation(animation);
        login_layout.setAnimation(animation1);
        txt.setAnimation(animation2);
    }
}

