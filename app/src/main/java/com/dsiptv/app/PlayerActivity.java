package com.dsiptv.app;

import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.MediaItem;

public class PlayerActivity extends AppCompatActivity {

    private SimpleExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize PlayerView and ExoPlayer
        playerView = new PlayerView(this);
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Set your playlist URL here
        MediaItem mediaItem = MediaItem.fromUri("https://raw.githubusercontent.com/diljankhehra-cpu/Test/refs/heads/main/1");
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

        // Watermark overlay
        FrameLayout overlay = new FrameLayout(this);
        overlay.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        TextView watermark = new TextView(this);
        watermark.setText("SmartHack IPTV");  // Customize text
        watermark.setTextColor(Color.RED);
        watermark.setTextSize(18);
        watermark.setGravity(Gravity.TOP | Gravity.RIGHT);
        watermark.setPadding(0, 20, 20, 0);
        overlay.addView(watermark);

        // Combine player view and watermark overlay
        FrameLayout root = new FrameLayout(this);
        root.addView(playerView);
        root.addView(overlay);

        setContentView(root);
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
