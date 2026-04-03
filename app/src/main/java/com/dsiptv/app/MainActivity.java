package com.dsiptv.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private Button btnLoad;
    private RecyclerView recyclerView;
    private ChannelAdapter adapter;
    private List<String> channelUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.editTextUrl);
        btnLoad = findViewById(R.id.btnLoad);
        recyclerView = findViewById(R.id.recyclerView);

        channelUrls = new ArrayList<>();
        adapter = new ChannelAdapter(channelUrls, url -> {
            // On channel click -> open PlayerActivity
            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
            intent.putExtra("CHANNEL_URL", url);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnLoad.setOnClickListener(v -> {
            String playlistUrl = editTextUrl.getText().toString().trim();
            if (playlistUrl.isEmpty()) {
                Toast.makeText(this, "Enter playlist URL", Toast.LENGTH_SHORT).show();
            } else {
                loadPlaylist(playlistUrl);
            }
        });
    }

    private void loadPlaylist(String urlString) {
        new Thread(() -> {
            try {
                URL url = new URL(urlString);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                List<String> urls = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.startsWith("#") && !line.isEmpty()) {
                        urls.add(line);
                    }
                }
                reader.close();

                runOnUiThread(() -> {
                    channelUrls.clear();
                    channelUrls.addAll(urls);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Loaded " + urls.size() + " channels", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Failed to load playlist", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
