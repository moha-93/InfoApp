package com.mycompany.testtask.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycompany.testtask.R;

public class SplashActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView=findViewById(R.id.txt_splash);
        imageView=findViewById(R.id.img_splash);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.my_transition);
        textView.setAnimation(animation);
        imageView.setAnimation(animation);
        LogoLauncher launcher = new LogoLauncher();
        launcher.start();
    }

    private class LogoLauncher extends Thread{
        @Override
        public void run() {
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
        }
    }
}
