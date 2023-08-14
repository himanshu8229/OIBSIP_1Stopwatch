package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timeDisplay;
    private Button startButton, holdButton, resetButton;
    private long startTime = 0;
    private Handler handler = new Handler();
    private boolean isRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeDisplay = findViewById(R.id.timeDisplay);
        startButton = findViewById(R.id.startButton);
        holdButton = findViewById(R.id.holdButton);
        resetButton = findViewById(R.id.resetButton);
    }

    public void startTimer(View view) {
        if (!isRunning) {
            isRunning = true;
            startButton.setEnabled(false);
            holdButton.setEnabled(true);
            resetButton.setEnabled(true);

            startTime = System.currentTimeMillis();
            handler.post(updateTimerRunnable);
        }
    }

    public void holdTimer(View view) {
        if (isRunning) {
            isRunning = false;
            startButton.setEnabled(true);
            holdButton.setEnabled(false);
            resetButton.setEnabled(true);

            handler.removeCallbacks(updateTimerRunnable);
        }
    }

    @SuppressLint("SetTextI18n")
    public void resetTimer(View view) {
        isRunning = false;
        startButton.setEnabled(true);

        holdButton.setEnabled(false);
        resetButton.setEnabled(false);

        timeDisplay.setText("00:00:00");
        handler.removeCallbacks(updateTimerRunnable);
    }

    private final Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (currentTime / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds %= 60;
            minutes %= 60;

            @SuppressLint("DefaultLocale") String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timeDisplay.setText(timeString);

            if (isRunning) {
                handler.postDelayed(this, 1000);
            }
        }
    };
}
