package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;

import android.net.Uri;

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

public class PlayerActivity extends Activity {

    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1️⃣ Get URL from intent
        String url = getIntent().getStringExtra("url");
        if (url == null || url.isEmpty()) {
            finish(); // Close activity if URL invalid
            return;
        }

        // 2️⃣ Create PlayerView programmatically
        PlayerView playerView = new PlayerView(this);
        playerView.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));
        setContentView(playerView);

        // 3️⃣ Initialize ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // 4️⃣ Create MediaSource depending on URL type
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
        MediaSource mediaSource;

        if (url.endsWith(".m3u8")) {
            // HLS Playlist
            mediaSource = new HlsMediaSource.Factory(
                    new DefaultHttpDataSource.Factory()
            ).createMediaSource(mediaItem);
        } else {
            // MP4 or other progressive formats
            mediaSource = new ProgressiveMediaSource.Factory(
                    new DefaultDataSource.Factory(this)
            ).createMediaSource(mediaItem);
        }

        // 5️⃣ Prepare and play
        player.setMediaSource(mediaSource);
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
