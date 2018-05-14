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
import com.example.danif.hoponcmu.command.LogInCommand;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class LogInActivity extends AppCompatActivity {

    private EditText editUsername;
    private EditText editCode;
    private Button btnSignUp;

    private boolean loggedIn = false;

    public class LogInAction extends AsyncTask<Void, Void, Void>{
        private String username;
        private String code;
        private boolean emptyUsername = false;
        private boolean emptyCode = false;

        @Override
        protected Void doInBackground(Void... voids) {
            CommandHandlerImpl chi = new CommandHandlerImpl();

            this.username = LogInActivity.this.editUsername.getText().toString().trim();
            this.code = LogInActivity.this.editCode.getText().toString().trim();

            if (!this.valid_input()) {
                return null;
            }

            // Random used for test purpose
            LogInActivity.this.loggedIn = new Random().nextBoolean();

            LogInCommand suc = new LogInCommand(username, code);
            // suc.handle(chi);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (this.emptyUsername) {
                LogInActivity.this.editUsernameSetError("This field cannot be empty");
            }
            if (this.emptyCode) {
                LogInActivity.this.editCodeSetError("This field cannot be empty");
            }
            if (!this.emptyCode && !this.emptyUsername && !LogInActivity.this.loggedIn) {
                Toast.makeText(LogInActivity.this, "Login FAILED", Toast.LENGTH_SHORT).show();
            }
            if (LogInActivity.this.loggedIn) {
                Toast.makeText(LogInActivity.this, "Logged in with SUCCESS", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_log_in);

        setResult(Constants.LOGIN_FAILED);

        this.editUsername = (EditText) findViewById(R.id.logInUsername);
        this.editCode = (EditText) findViewById(R.id.logInCode);

        this.btnSignUp = (Button) findViewById(R.id.btnLogIn_LogIn);
        this.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<Void, Void, Void> task = new LogInAction();
                try {
                    task.execute().get();
                } catch (InterruptedException e){
                    // TODO
                } catch (ExecutionException e) {
                    // TODO
                }
                if (LogInActivity.this.loggedIn) {
                    // login()
                    setResult(Constants.LOGIN_OK);
                    LogInActivity.this.finish();
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
