package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.graphics.*;

import android.hardware.camera2.CameraManager;
import android.content.Context;

public class MainActivity extends Activity {

    private boolean isOn = false;
    private CameraManager cameraManager;
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MAIN LAYOUT
        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(Color.BLACK);
        main.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

        // TITLE
        TextView title = new TextView(this);
        title.setText("Powerful Functions");
        title.setTextSize(26);
        title.setTextColor(Color.WHITE);
        title.setPadding(0, 80, 0, 40);
        main.addView(title);

        // SLIDER (fake strobe)
        SeekBar slider = new SeekBar(this);
        slider.setMax(10);
        main.addView(slider);

        // COMPASS (fake)
        TextView compass = new TextView(this);
        compass.setText("🧭");
        compass.setTextSize(50);
        compass.setPadding(0, 40, 0, 40);
        main.addView(compass);

        // BUTTON
        Button btn = new Button(this);
        btn.setText("⏻");
        btn.setTextSize(40);
        btn.setTextColor(Color.WHITE);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(Color.parseColor("#333333"));
        btn.setBackground(shape);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(300, 300);
        params.setMargins(0, 40, 0, 40);

        main.addView(btn, params);

        // CAMERA
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (Exception e) {}

        // CLICK
        btn.setOnClickListener(v -> {
            try {
                isOn = !isOn;
                cameraManager.setTorchMode(cameraId, isOn);

                if (isOn) {
                    shape.setColor(Color.parseColor("#00FFAA")); // glow
                } else {
                    shape.setColor(Color.parseColor("#333333"));
                }

            } catch (Exception e) {}
        });

        // BOTTOM
        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setGravity(Gravity.CENTER);
        bottom.setPadding(0, 80, 0, 40);

        TextView morse = new TextView(this);
        morse.setText("MORSE");
        morse.setTextColor(Color.GRAY);

        TextView color = new TextView(this);
        color.setText("COLOR");
        color.setTextColor(Color.GRAY);
        color.setPadding(100, 0, 0, 0);

        bottom.addView(morse);
        bottom.addView(color);

        main.addView(bottom);

        setContentView(main);
    }
}
