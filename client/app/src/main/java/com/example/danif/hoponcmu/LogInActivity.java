package com.example.danif.hoponcmu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.danif.hoponcmu.DataObjects.Constants;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setResult(Constants.LOGIN_FAILED);

        Button btnLogIn = (Button) findViewById(R.id.btnLogIn_LogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LogInActivity.this.validate()) {
                    setResult(Constants.LOGIN_OK);
                    LogInActivity.this.finish();
                }
            }
        });
    }

    private boolean validate() {
        return true;
    }
}
