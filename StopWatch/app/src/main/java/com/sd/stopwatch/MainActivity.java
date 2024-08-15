package com.sd.stopwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Chronometer chrono;
    private ImageButton start;
    private ImageButton pause;
    private ImageButton reset;
    private ImageButton laps;
    private RecyclerView recyclerView;
    private boolean isRunning = false;
    private long pauseOffset = 0;
    private final List<String> laptimes = new ArrayList<>();
    private LapAdapter lapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        chrono = findViewById(R.id.chrono);
        start = findViewById(R.id.start_btn);
        pause = findViewById(R.id.pause_btn);
        reset = findViewById(R.id.reset_btn);
        laps = findViewById(R.id.lap_btn);
        recyclerView = findViewById(R.id.recycler_view);
        lapAdapter = new LapAdapter(laptimes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lapAdapter);

      start.setOnClickListener(v -> {
          if (!isRunning) {
              chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset);
              chrono.start();
              isRunning = true;
              start.setVisibility(View.GONE);
              pause.setVisibility(View.VISIBLE);
              laps.setVisibility(View.VISIBLE);
              reset.setVisibility(View.GONE);
          }
      });

      pause.setOnClickListener(v -> {
          if (isRunning) {
              chrono.stop();
              pauseOffset = SystemClock.elapsedRealtime() - chrono.getBase();
              isRunning = false;
              pause.setVisibility(View.GONE);
              start.setVisibility(View.VISIBLE);
              reset.setVisibility(View.VISIBLE);
              laps.setVisibility(View.GONE);
          }
      });

      reset.setOnClickListener(v -> {
          chrono.setBase(SystemClock.elapsedRealtime());
          pauseOffset = 0;
          chrono.stop();
          isRunning = false;
          start.setVisibility(View.VISIBLE);
          pause.setVisibility(View.GONE);
          reset.setVisibility(View.GONE);
          laps.setVisibility(View.GONE);
          laptimes.clear();
          lapAdapter.notifyDataSetChanged();
          recyclerView.setVisibility(View.GONE);
      });

      laps.setOnClickListener(v -> {
          if (isRunning) {
              recyclerView.setVisibility(View.VISIBLE);
              long elapsedRealTime = SystemClock.elapsedRealtime() - chrono.getBase();
              int minutes = (int) ((elapsedRealTime / 1000) / 60);
              int seconds = (int) ((elapsedRealTime / 1000) % 60);
              int millis = (int) ((elapsedRealTime % 1000) / 10);
              @SuppressLint("DefaultLocale")
              String laptime = String.format("%02d:%02d:%02d", minutes, seconds, millis);
              laptimes.add(laptime);
              lapAdapter.notifyDataSetChanged();
          }
      });
    }
}