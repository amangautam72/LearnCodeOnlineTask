package com.example.administrator.learncodeonlinetask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorgrey));
        }
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);


            }

        },2000);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        //GoogleAnalytics.getInstance(SplashScreen.this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //GoogleAnalytics.getInstance(SplashScreen.this).reportActivityStop(this);
    }

}
