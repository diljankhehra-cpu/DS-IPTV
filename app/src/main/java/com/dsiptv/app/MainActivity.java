package com.dsiptv.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private boolean isOn = false;
    private CameraManager cameraManager;
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btnFlash);

        // Permission check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);
        }

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Permission check again
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {

                    try {
                        isOn = !isOn;
                        cameraManager.setTorchMode(cameraId, isOn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
