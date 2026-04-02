package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.BLACK);
        layout.setGravity(Gravity.CENTER);

        // Loading text
        TextView loading = new TextView(this);
        loading.setText("Loading...");
        loading.setTextColor(Color.WHITE);
        loading.setTextSize(24);
        layout.addView(loading);

        setContentView(layout);

        // Delay → show input screen
        new android.os.Handler().postDelayed(() -> {
            showInputScreen();
        }, 2000);
    }

    private void showInputScreen() {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.BLACK);
        layout.setPadding(40, 100, 40, 40);

        TextView title = new TextView(this);
        title.setText("Enter Playlist URL");
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);
        layout.addView(title);

        EditText input = new EditText(this);
        input.setHint("http://example.com/playlist.m3u");
        input.setTextColor(Color.WHITE);
        layout.addView(input);

        Button btn = new Button(this);
        btn.setText("LOAD");
        layout.addView(btn);

        btn.setOnClickListener(v -> {
            String url = input.getText().toString();

            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        });

        setContentView(layout);
    }
}
