package com.dsiptv.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🚀 App start hunda hi PlayerActivity launch
        try {
            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            // ⚠️ Agar PlayerActivity start fail hove ta error show karo
            Toast.makeText(this, "Player start failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        // 🔒 MainActivity close kar do, back press PlayerActivity te hi hoye
        finish();
    }
}
