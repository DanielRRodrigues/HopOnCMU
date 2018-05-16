package com.example.danif.hoponcmu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.danif.hoponcmu.client.CommandHandlerImpl;
import com.example.danif.hoponcmu.command.SignUpCommand;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity {

  private EditText etUsername;
  private EditText etCode;
  private Button btnSignUp;

  private boolean loggedIn = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    setResult(Constants.SIGNUP_FAILED);

    this.etUsername = (EditText) findViewById(R.id.edit_username);
    this.etCode = (EditText) findViewById(R.id.edit_code);
    this.btnSignUp = (Button) findViewById(R.id.button_signup);

    this.btnSignUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AsyncTask<Void, Void, Void> task = new SignUpAction();
        try {
          task.execute().get();
        } catch (InterruptedException e) {
          Log.d(Constants.LOG_TAG, "onClick error: InterruptedException");
        } catch (ExecutionException e) {
          Log.d(Constants.LOG_TAG, "onClick error: ExecutionException");
        }
        if (SignUpActivity.this.loggedIn) {
          // login()
          // TODO: 16/05/2018
          setResult(Constants.SIGNUP_OK);
          SignUpActivity.this.finish();
        }
      }
    });

  }

  private void editUsernameSetError(String text) {
    this.etUsername.setError(text);
  }

  private void editCodeSetError(String text) {
    this.etCode.setError(text);
  }

  public class SignUpAction extends AsyncTask<Void, Void, Void> {

    private String username;
    private String code;
    private boolean emptyUsername = false;
    private boolean emptyCode = false;

    @Override
    protected Void doInBackground(Void... voids) {
      CommandHandlerImpl chi = new CommandHandlerImpl();

      this.username = SignUpActivity.this.etUsername.getText().toString().trim();
      this.code = SignUpActivity.this.etCode.getText().toString().trim();

      if (!this.validate()) {
        return null;
      }

      // Random used for test purpose
      SignUpActivity.this.loggedIn = new Random().nextBoolean();

      SignUpCommand suc = new SignUpCommand(this.username, this.code);
      // suc.handle(chi);
      // TODO: 16/05/2018

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      if (this.emptyUsername) {
        SignUpActivity.this.editUsernameSetError(Constants.ERROR_EMPTY_EDIT_TEXT);
      }
      if (this.emptyCode) {
        SignUpActivity.this.editCodeSetError(Constants.ERROR_EMPTY_EDIT_TEXT);
      }
      if (!this.emptyCode && !this.emptyUsername && !SignUpActivity.this.loggedIn) {
        Toast.makeText(SignUpActivity.this, Constants.TOAST_SIGNUP_FAILED, Toast.LENGTH_SHORT)
            .show();
      }
      if (SignUpActivity.this.loggedIn) {
        Toast.makeText(SignUpActivity.this, Constants.TOAST_SIGNUP_SUCCESS, Toast.LENGTH_SHORT)
            .show();
      }
    }

    private boolean validate() {
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
}
