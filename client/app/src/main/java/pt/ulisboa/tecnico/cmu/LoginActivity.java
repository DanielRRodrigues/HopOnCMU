package pt.ulisboa.tecnico.cmu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import pt.ulisboa.tecnico.cmu.client.ResponseHandlerImpl;
import pt.ulisboa.tecnico.cmu.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.response.LoginResponse;

public class LoginActivity extends AppCompatActivity {

  public static String sessionId = null;

  private EditText etUsername;
  private EditText etCode;
  private Button btnLogin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(pt.ulisboa.tecnico.cmu.R.layout.activity_login);
    setResult(Constants.LOGIN_FAILED);

    this.etUsername = (EditText) findViewById(pt.ulisboa.tecnico.cmu.R.id.edit_username);
    this.etCode = (EditText) findViewById(pt.ulisboa.tecnico.cmu.R.id.edit_code);
    this.btnLogin = (Button) findViewById(pt.ulisboa.tecnico.cmu.R.id.button_login);

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
        if (LoginActivity.sessionId != null) {
          Intent resultIntent = new Intent();
          resultIntent.putExtra(Constants.EXTRA_SESSION_ID, LoginActivity.sessionId);
          setResult(Constants.LOGIN_OK, resultIntent);
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
      this.username = LoginActivity.this.etUsername.getText().toString().trim();
      this.code = LoginActivity.this.etCode.getText().toString().trim();

      if (!this.validate()) {
        return null;
      }

      Socket server = null;
      ResponseHandlerImpl rhi = new ResponseHandlerImpl();
      LoginCommand lc = new LoginCommand(this.username, this.code);

      try {
        server = new Socket(MainActivity.HOST, MainActivity.PORT);

        ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
        oos.writeObject(lc);

        ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
        LoginResponse lr = (LoginResponse) ois.readObject();
        lr.handle(rhi);

        oos.close();
        ois.close();
        Log.d(Constants.LOG_TAG, "LoginAction");
      } catch (Exception e) {
        Log.d(Constants.LOG_TAG, "LoginAction failed..." + e.getMessage());
        e.printStackTrace();
      } finally {
        if (server != null) {
          try {
            server.close();
          } catch (Exception e) {
          }
        }
      }
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
      if (!this.emptyCode && !this.emptyUsername && LoginActivity.sessionId == null) {
        Toast.makeText(LoginActivity.this, Constants.TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show();
      }
      if (LoginActivity.sessionId != null) {
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
