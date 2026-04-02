package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;

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
        title.setGravity(Gravity.CENTER);
        main.addView(title);

        // SLIDER (strobe feel)
        SeekBar slider = new SeekBar(this);
        slider.setMax(10);
        main.addView(slider);

        // COMPASS ICON (fake)
        TextView compass = new TextView(this);
        compass.setText("🧭");
        compass.setTextSize(50);
        compass.setPadding(0, 40, 0, 40);
        compass.setGravity(Gravity.CENTER);
        main.addView(compass);

        // POWER BUTTON
        Button btn = new Button(this);
        btn.setText("⏻");
        btn.setTextSize(40);
        btn.setTextColor(Color.WHITE);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(Color.parseColor("#333333")); // OFF color
        btn.setBackground(shape);

        LinearLayout.LayoutParams btnParams =
                new LinearLayout.LayoutParams(350, 350);
        btnParams.setMargins(0, 40, 0, 40);

        main.addView(btn, btnParams);

        // CAMERA
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (Exception e) {}

        // BUTTON CLICK
        btn.setOnClickListener(v -> {
            try {
                isOn = !isOn;
                cameraManager.setTorchMode(cameraId, isOn);

                if (isOn) {
                    shape.setColor(Color.parseColor("#00FFAA")); // glow green
                } else {
                    shape.setColor(Color.parseColor("#333333"));
                }

            } catch (Exception e) {}
        });

        // BOTTOM MENU
        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setGravity(Gravity.CENTER);
        bottom.setPadding(0, 80, 0, 40);

        TextView morse = new TextView(this);
        morse.setText("MORSE");
        morse.setTextColor(Color.GRAY);
        morse.setTextSize(16);

        TextView color = new TextView(this);
        color.setText("COLOR");
        color.setTextColor(Color.GRAY);
        color.setTextSize(16);
        color.setPadding(100, 0, 0, 0);

        bottom.addView(morse);
        bottom.addView(color);

        main.addView(bottom);

        setContentView(main);
    }
}
