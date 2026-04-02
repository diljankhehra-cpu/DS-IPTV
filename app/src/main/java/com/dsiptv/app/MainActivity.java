package com.dsiptv.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import android.hardware.camera2.CameraManager;
import android.content.Context;

public class MainActivity extends Activity {

    private boolean isOn = false;
    private CameraManager cameraManager;
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Title
        TextView title = new TextView(this);
        title.setText("Powerful Functions");
        title.setTextSize(24);

        // Button
        Button btn = new Button(this);
        btn.setText("POWER OFF");

        // Camera setup
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Button click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    isOn = !isOn;
                    cameraManager.setTorchMode(cameraId, isOn);

                    if (isOn) {
                        btn.setText("POWER ON 🔦");
                    } else {
                        btn.setText("POWER OFF");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 100, 40, 40);

        layout.addView(title);
        layout.addView(btn);

        setContentView(layout);
    }
}
