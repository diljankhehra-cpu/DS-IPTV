package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.content.pm.ActivityInfo;
import android.widget.Toast;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.ui.PlayerView;

public class PlayerActivity extends Activity {

    private ExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1️⃣ Fullscreen + landscape
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // 2️⃣ Create root layout
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));

        // 3️⃣ EditText for playlist URL input
        EditText urlInput = new EditText(this);
        urlInput.setHint("Enter video URL or .m3u8 playlist");
        urlInput.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        rootLayout.addView(urlInput);

        // 4️⃣ Load Button
        Button loadButton = new Button(this);
        loadButton.setText("Load");
        loadButton.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        rootLayout.addView(loadButton);

        // 5️⃣ PlayerView programmatically
        playerView = new PlayerView(this);
        playerView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1.0f // weight to fill remaining space
        ));
        rootLayout.addView(playerView);

        setContentView(rootLayout);

        // 6️⃣ Initialize ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // 7️⃣ Load Button click
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlInput.getText().toString().trim();
                if (url.isEmpty()) {
                    Toast.makeText(PlayerActivity.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadPlaylist(url);
            }
        });
    }

    // 8️⃣ Playlist loader (single or multiple URLs separated by comma)
    private void loadPlaylist(String input) {
        player.clearMediaItems();

        String[] urls = input.split(","); // comma-separated multiple URLs
        List<MediaItem> items = new ArrayList<>();

        for (String url : urls) {
            url = url.trim();
            if (url.isEmpty()) continue;
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
            items.add(mediaItem);
        }

        if (items.isEmpty()) {
            Toast.makeText(this, "No valid URLs found", Toast.LENGTH_SHORT).show();
            return;
        }

        for (MediaItem item : items) {
            player.addMediaItem(item);
        }

        // Loop playlist
        player.setRepeatMode(Player.REPEAT_MODE_ALL);

        // Prepare and play
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
