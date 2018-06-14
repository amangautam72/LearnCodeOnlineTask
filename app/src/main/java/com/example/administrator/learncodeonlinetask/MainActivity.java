package com.example.administrator.learncodeonlinetask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final private static String url = "https://learncodeonline.in/api/android/datastructure.json";
    final private static String WEBSITE = "https://courses.learncodeonline.in";
    private static String myPref = "myPref";
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    TextView question1;
    TextView answer1;

    TextView question2;
    TextView answer2;

    ProgressBar progressBar;

    ImageView logo;
    ImageView nextPage;

    private String volleyResponse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorgrey));
        }
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(myPref,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        initialViews();

        if (sharedPreferences.getString("response","").equals("")){
            if (!isNetworkConnected()){
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    volleyResponse = response;

                    System.out.println("RESPONSE :"+volleyResponse);

                    editor.putString("response",volleyResponse);
                    editor.commit();

                    updateUI();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(stringRequest);
        }
        else {
            updateUI();
        }


        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Page2.class);
                startActivity(intent);
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEBSITE));
                startActivity(browserIntent);
            }
        });


    }

    private void initialViews() {

        question1 = findViewById(R.id.question1);
        answer1 = findViewById(R.id.answer1);

        question2 = findViewById(R.id.question2);
        answer2 = findViewById(R.id.answer2);

        logo = findViewById(R.id.logo);
        nextPage = findViewById(R.id.next);

        progressBar = findViewById(R.id.progress_dialog);
    }

    public void updateUI(){

        if (!sharedPreferences.getString("response","").equals("")) {
            volleyResponse = sharedPreferences.getString("response", "");
        }

        System.out.println("RFESPONSE --"+volleyResponse);
        try {
            JSONObject jsonObject = new JSONObject(volleyResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("questions");

            for (int i = 0; i < 2; i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                String question = object.getString("question");
                String answer = object.getString("Answer");

                if (i == 0) {
                    question1.setText(question);
                    answer1.setText(answer);
                }
                if (i == 1) {
                    question2.setText(question);
                    answer2.setText(answer);
                }


            }

            if (progressBar.getVisibility()==View.VISIBLE){
                progressBar.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
