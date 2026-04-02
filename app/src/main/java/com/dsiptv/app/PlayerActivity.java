package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;

import android.net.Uri;
import java.util.List;
import java.util.Arrays;

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.ui.PlayerView;

public class PlayerActivity extends Activity {

    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1️⃣ Fullscreen + landscape
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // 2️⃣ Programmatic PlayerView
        PlayerView playerView = new PlayerView(this);
        playerView.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));
        setContentView(playerView);

        // 3️⃣ Initialize ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // 4️⃣ Playlist (dynamic or hardcoded)
        List<String> playlist = Arrays.asList(
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
        );

        // Optional: Get playlist from intent extra (if passed)
        List<String> intentPlaylist = getIntent().getStringArrayListExtra("playlist");
        if (intentPlaylist != null && !intentPlaylist.isEmpty()) {
            playlist = intentPlaylist;
        }

        // 5️⃣ Add media items to player
        for (String url : playlist) {
            if (url == null || url.isEmpty()) continue; // skip invalid
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
            player.addMediaItem(mediaItem);
        }

        // 6️⃣ Loop playlist
        player.setRepeatMode(Player.REPEAT_MODE_ALL);

        // 7️⃣ Prepare and start playback
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
