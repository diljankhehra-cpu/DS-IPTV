package com.dsiptv.app;

import android.os.Bundle;
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

        playerView = new PlayerView(this);
        setContentView(playerView);

        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        String url = getIntent().getStringExtra("CHANNEL_URL");
        if (url == null || url.isEmpty()) {
            url = "https://iptv-org.github.io/iptv/index.m3u"; // default GitHub IPTV playlist
        }

        MediaItem mediaItem = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
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
