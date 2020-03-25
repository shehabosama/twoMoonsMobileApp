package com.android.two_moons_project.Ui.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.two_moons_project.R;
import com.android.two_moons_project.Ui.Activities.main.MainActivity;
import com.android.two_moons_project.common.SqlHelper.myDbAdapter;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        helper = new myDbAdapter(this);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        Thread thread = new Thread()
        {

            @Override
            public void run() {

                try{
                    sleep(2000);
                }catch (Exception e){

                    e.printStackTrace();

                }

                finally
                {
                    if(helper.getData().length()>0){

                    }else{
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK));
                    }

                }

            }
        };thread.start();
    }
}
