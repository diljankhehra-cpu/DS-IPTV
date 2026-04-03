package com.dsiptv.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Player start failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        // Back press PlayerActivity te hi ho
        finish();
    }
}
