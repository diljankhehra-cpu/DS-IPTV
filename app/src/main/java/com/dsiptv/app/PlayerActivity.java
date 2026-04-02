package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;

import android.net.Uri;

public class PlayerActivity extends Activity {

    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("url");

        PlayerView playerView = new PlayerView(this);
        playerView.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));

        setContentView(playerView);

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        DefaultDataSource.Factory dataSourceFactory =
                new DefaultDataSource.Factory(this);

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));

        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}
