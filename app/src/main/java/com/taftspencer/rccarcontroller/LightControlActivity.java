package com.taftspencer.rccarcontroller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LightControlActivity extends AppCompatActivity {
    private HttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_control);

        // Initialize HTTP client and set endpoint (example IP/port)
        httpClient = new HttpClient();
        httpClient.setEndpoint("10.0.0.104", 4242); // You can change this as needed

        Button btnOn = findViewById(R.id.btn_light_on);
        Button btnOff = findViewById(R.id.btn_light_off);
        Button btnPwm = findViewById(R.id.btn_pwm_light);

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLightOnClicked();
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLightOffClicked();
            }
        });

        btnPwm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPwmLightClicked();
            }
        });
    }

    private void onLightOnClicked() {
        String json = "{\"config1\": {\"ID\": \"flashlight\", \"pin\": 15, \"isOn\": true, \"isRamp\": false, \"LMax\": 85, \"rampUpTimeMs\": 5000}}";
        new Thread(() -> {
            try { httpClient.postJson(json); } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    private void onLightOffClicked() {
        String json = "{\"config1\": {\"ID\": \"flashlight\", \"pin\": 15, \"isOn\": false, \"isRamp\": false, \"LMax\": 85, \"rampUpTimeMs\": 5000}}";
        new Thread(() -> {
            try { httpClient.postJson(json); } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    private void onPwmLightClicked() {
        String json = "{\"config1\": {\"ID\": \"flashlight\", \"pin\": 15, \"isOn\": true, \"isRamp\": true, \"LMax\": 85, \"rampUpTimeMs\": 5000}}";
        new Thread(() -> {
            try { httpClient.postJson(json); } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }
}
