
package com.example.klzwii.hit_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class log_signActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign);
        Button log_button = (Button) findViewById(R.id.logbutton);
        Button reg_button = findViewById(R.id.regibutton);
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(log_signActivity.this,log.class);
                log_signActivity.this.startActivity(mainIntent);
            }
        });
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(log_signActivity.this,regiActivity.class);
                log_signActivity.this.startActivity(mainIntent);
            }
        });
    }
}
