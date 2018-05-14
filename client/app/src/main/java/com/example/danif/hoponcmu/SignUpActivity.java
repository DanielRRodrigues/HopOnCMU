package com.example.danif.hoponcmu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danif.hoponcmu.DataObjects.Constants;
import com.example.danif.hoponcmu.client.CommandHandlerImpl;
import com.example.danif.hoponcmu.command.SignUpCommand;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity {

    private EditText editUsername;
    private EditText editCode;
    private Button btnSignUp;

    private boolean loggedIn = false;

    public class SignUpAction extends AsyncTask<Void, Void, Void>{
        private String username;
        private String code;
        private boolean emptyUsername = false;
        private boolean emptyCode = false;

        @Override
        protected Void doInBackground(Void... voids) {
            CommandHandlerImpl chi = new CommandHandlerImpl();

            this.username = SignUpActivity.this.editUsername.getText().toString().trim();
            this.code = SignUpActivity.this.editCode.getText().toString().trim();

            if (!this.valid_input()) {
                return null;
            }

            // Random used for test purpose
            SignUpActivity.this.loggedIn = new Random().nextBoolean();

            SignUpCommand suc = new SignUpCommand(username, code);
            // suc.handle(chi);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (this.emptyUsername) {
                SignUpActivity.this.editUsernameSetError("This field cannot be empty");
            }
            if (this.emptyCode) {
                SignUpActivity.this.editCodeSetError("This field cannot be empty");
            }
            if (!this.emptyCode && !this.emptyUsername && !SignUpActivity.this.loggedIn) {
                Toast.makeText(SignUpActivity.this, "Sign up FAILED", Toast.LENGTH_SHORT).show();
            }
            if (SignUpActivity.this.loggedIn) {
                Toast.makeText(SignUpActivity.this, "Signed up with SUCCESS", Toast.LENGTH_SHORT).show();
            }
        }

        private boolean valid_input() {
            if (this.username.isEmpty()) {
                this.emptyUsername = true;
                return false;
            }

            if (this.code.isEmpty()) {
                this.emptyCode = true;
                return false;
            }
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setResult(Constants.SIGNUP_FAILED);

        this.editUsername = (EditText) findViewById(R.id.signUpUsername);
        this.editCode = (EditText) findViewById(R.id.signUpCode);

        this.btnSignUp = (Button) findViewById(R.id.btnSignUp_SignUp);
        this.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<Void, Void, Void> task = new SignUpAction();
                try {
                    task.execute().get();
                } catch (InterruptedException e){
                    // TODO
                } catch (ExecutionException e) {
                    // TODO
                }
                if (SignUpActivity.this.loggedIn) {
                    // login()
                    setResult(Constants.SIGNUP_OK);
                    SignUpActivity.this.finish();
                }
            }
        });
    }

    private void editUsernameSetError(String text) {
        this.editUsername.setError(text);
    }

    private void editCodeSetError(String text) {
        this.editCode.setError(text);
    }
}
