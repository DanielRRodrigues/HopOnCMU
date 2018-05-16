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
import com.example.danif.hoponcmu.command.LogInCommand;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

  private EditText etUsername;
  private EditText etCode;
  private Button btnLogin;

  private boolean loggedIn = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setResult(Constants.LOGIN_FAILED);

    this.etUsername = (EditText) findViewById(R.id.edit_username);
    this.etCode = (EditText) findViewById(R.id.edit_code);
    this.btnLogin = (Button) findViewById(R.id.button_login);

    this.btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AsyncTask<Void, Void, Void> task = new LogInAction();
        try {
          task.execute().get();
        } catch (InterruptedException e) {
          Log.d(Constants.LOG_TAG, "onClick error: InterruptedException");
        } catch (ExecutionException e) {
          Log.d(Constants.LOG_TAG, "onClick error: ExecutionException");
        }
        if (LoginActivity.this.loggedIn) {
          // login()
          // TODO: 16/05/2018
          setResult(Constants.LOGIN_OK);
          LoginActivity.this.finish();
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

  public class LogInAction extends AsyncTask<Void, Void, Void> {

    private String username;
    private String code;
    private boolean emptyUsername = false;
    private boolean emptyCode = false;

    @Override
    protected Void doInBackground(Void... voids) {
      CommandHandlerImpl chi = new CommandHandlerImpl();

      this.username = LoginActivity.this.etUsername.getText().toString().trim();
      this.code = LoginActivity.this.etCode.getText().toString().trim();

      if (!this.validate()) {
        return null;
      }

      // Random used for test purpose
      LoginActivity.this.loggedIn = new Random().nextBoolean();

      LogInCommand suc = new LogInCommand(this.username, this.code);
      // suc.handle(chi);
      // TODO: 16/05/2018

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      if (this.emptyUsername) {
        LoginActivity.this.editUsernameSetError(Constants.ERROR_EMPTY_EDIT_TEXT);
      }
      if (this.emptyCode) {
        LoginActivity.this.editCodeSetError(Constants.ERROR_EMPTY_EDIT_TEXT);
      }
      if (!this.emptyCode && !this.emptyUsername && !LoginActivity.this.loggedIn) {
        Toast.makeText(LoginActivity.this, Constants.TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show();
      }
      if (LoginActivity.this.loggedIn) {
        Toast.makeText(LoginActivity.this, Constants.TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT)
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
