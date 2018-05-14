package com.example.danif.hoponcmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.danif.hoponcmu.DataObjects.Constants;

public class CredentialsActivity extends AppCompatActivity {

    private Button btnLogIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);

        setResult(Constants.AUTH_FAILED);

        this.getViews();
        this.setListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == Constants.LOGIN_OK) {
                setResult(Constants.AUTH_OK);
                this.finish();
            } else {  // resultCode == Constants.LOGIN_FAILED
                // Do Nothing
            }
        }
        else if (requestCode == Constants.REQUEST_SIGNUP) {
            if (resultCode == Constants.SIGNUP_OK) {
                setResult(Constants.AUTH_OK);
                this.finish();
            } else {  // resultCode == Constants.AUTH_FAILED
                // Do Nothing
            }
        }
    }

    private void getViews() {
        this.btnLogIn = (Button) findViewById(R.id.btnLogIn_Credentials);
        this.btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    private void setListeners() {
        this.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
                startActivityForResult(intent, Constants.REQUEST_LOGIN);
            }
        });
        this.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, Constants.REQUEST_SIGNUP);
            }
        });
    }
}
