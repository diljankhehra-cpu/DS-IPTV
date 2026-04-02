package com.dsiptv.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Direct PlayerActivity start kar do
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        startActivity(intent);

        // MainActivity khatam kar do, back press PlayerActivity te hi ho
        finish();
    }
}
