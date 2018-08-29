package com.example.michael.getcontact;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    TextView splash_txt;
    ImageView phone_icon,background_img;
    ImageView people_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash_txt = findViewById(R.id.splash_txt);
        phone_icon = findViewById(R.id.phone_icon);
        people_icon = findViewById(R.id.people_icon);
        background_img = findViewById(R.id.background_img);



        Animation animation = AnimationUtils.loadAnimation(this,R.anim.up_to_down);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.down_to_up);
        Animation animation3 = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        splash_txt.setAnimation(animation2);
        phone_icon.setAnimation(animation);
        people_icon.setAnimation(animation1);
        background_img.setAnimation(animation3);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash_screen.this,MainActivity.class));
                finish();
            }
        },5000);
    }
}
