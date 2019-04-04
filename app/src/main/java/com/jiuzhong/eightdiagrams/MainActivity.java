package com.jiuzhong.eightdiagrams;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jiuzhong.eightdiagrams.view.EightDiagramsView;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    EightDiagramsView e;
    int j = -90;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e = findViewById(R.id.eight);
        handler.postDelayed(runnable, 100);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            e.set(j);
            j--;//逆时针旋转
            //j++;//顺时针旋转
            handler.postDelayed(this, 10);
        }
    };
}
