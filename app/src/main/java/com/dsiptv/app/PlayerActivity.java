package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.ui.PlayerView;

public class PlayerActivity extends Activity {

    private ExoPlayer player;

    // 🔥 Direct M3U URL
    private String M3U_URL = "https://raw.githubusercontent.com/diljankhehra-cpu/Test/refs/heads/main/1";

    private final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Fullscreen + Landscape
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // ✅ Runtime INTERNET permission check (XML-free)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.INTERNET}, PERMISSION_REQUEST_CODE);
            } else {
                initPlayer();
            }
        } else {
            initPlayer();
        }
    }

    // Handle runtime permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPlayer();
            } else {
                Toast.makeText(this, "Internet permission required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 🔥 Player init method
    private void initPlayer() {
        // ✅ PlayerView programmatic
        PlayerView playerView = new PlayerView(this);
        playerView.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));
        setContentView(playerView);

        // ✅ ExoPlayer init
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // 🔥 Load M3U in background thread
        new Thread(() -> {
            List<String> urls = loadM3U(M3U_URL);

            runOnUiThread(() -> {
                if (urls.isEmpty()) {
                    Toast.makeText(this, "No channels found", Toast.LENGTH_LONG).show();
                    return;
                }

                for (String url : urls) {
                    try {
                        MediaItem item = MediaItem.fromUri(Uri.parse(url));
                        player.addMediaItem(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // Loop playlist & auto-play first channel
                player.setRepeatMode(Player.REPEAT_MODE_ALL);
                player.prepare();
                player.play();
            });
        }).start();
    }

    // 🔥 Simple M3U parser
    private List<String> loadM3U(String urlString) {
        List<String> list = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    list.add(line);
                }
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
