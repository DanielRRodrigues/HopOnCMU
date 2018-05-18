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
import pt.ulisboa.tecnico.cmu.client.ResponseHandlerImpl;
import pt.ulisboa.tecnico.cmu.command.SignUpCommand;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import pt.ulisboa.tecnico.cmu.response.SignUpResponse;

public class SignUpActivity extends AppCompatActivity {

  public static String sessionId = null;

  private EditText etUsername;
  private EditText etCode;
  private Button btnSignUp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(pt.ulisboa.tecnico.cmu.R.layout.activity_sign_up);
    setResult(Constants.SIGNUP_FAILED);

    this.etUsername = (EditText) findViewById(pt.ulisboa.tecnico.cmu.R.id.edit_username);
    this.etCode = (EditText) findViewById(pt.ulisboa.tecnico.cmu.R.id.edit_code);
    this.btnSignUp = (Button) findViewById(pt.ulisboa.tecnico.cmu.R.id.button_signup);

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
        if (SignUpActivity.sessionId != null) {
          Intent resultIntent = new Intent();
          resultIntent.putExtra(Constants.EXTRA_SESSION_ID, SignUpActivity.sessionId);
          setResult(Constants.SIGNUP_OK, resultIntent);
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

      this.username = SignUpActivity.this.etUsername.getText().toString().trim();
      this.code = SignUpActivity.this.etCode.getText().toString().trim();

      if (!this.validate()) {
        return null;
      }

      Socket server = null;
      ResponseHandlerImpl rhi = new ResponseHandlerImpl();
      SignUpCommand suc = new SignUpCommand(this.username, this.code);

      try {
        server = new Socket(MainActivity.HOST, MainActivity.PORT);

        ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
        oos.writeObject(suc);

        ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
        SignUpResponse sur = (SignUpResponse) ois.readObject();
        sur.handle(rhi);

        oos.close();
        ois.close();
        Log.d(Constants.LOG_TAG, "SignUpAction");
      }
      catch (Exception e) {
        Log.d(Constants.LOG_TAG, "SignUpAction failed..." + e.getMessage());
        e.printStackTrace();
      } finally {
        if (server != null) {
          try { server.close(); }
          catch (Exception e) { }
        }
      }
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
      if (!this.emptyCode && !this.emptyUsername && SignUpActivity.this.sessionId == null) {
        Toast.makeText(SignUpActivity.this, Constants.TOAST_SIGNUP_FAILED, Toast.LENGTH_SHORT)
            .show();
      }
      if (SignUpActivity.this.sessionId != null) {
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
