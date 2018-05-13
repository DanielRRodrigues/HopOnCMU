package com.example.danif.hoponcmu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        private boolean emptyUsername = false;
        private boolean emptyCode = false;

        @Override
        protected Void doInBackground(Void... voids) {
            CommandHandlerImpl chi = new CommandHandlerImpl();

            String username = SignUpActivity.this.editUsername.getText().toString().trim();
            String code = SignUpActivity.this.editCode.getText().toString().trim();

            if (username.isEmpty()) {
                emptyUsername = true;
                return null;
            }

            if (code.isEmpty()) {
                emptyCode = true;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editUsername = (EditText) findViewById(R.id.signUpUsername);
        editCode = (EditText) findViewById(R.id.signUpCode);

        btnSignUp = (Button) findViewById(R.id.btnSignUp_SignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
