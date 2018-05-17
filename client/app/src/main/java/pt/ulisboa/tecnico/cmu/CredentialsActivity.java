package pt.ulisboa.tecnico.cmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pt.ulisboa.tecnico.cmu.R;

public class CredentialsActivity extends AppCompatActivity {

  private Button btnLogin;
  private Button btnSignUp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_credentials);
    setResult(Constants.AUTH_FAILED);

    this.btnLogin = (Button) findViewById(R.id.button_login);
    this.btnSignUp = (Button) findViewById(R.id.button_signup);

    this.btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
    } else if (requestCode == Constants.REQUEST_SIGNUP) {
      if (resultCode == Constants.SIGNUP_OK) {
        setResult(Constants.AUTH_OK);
        this.finish();
      } else {  // resultCode == Constants.AUTH_FAILED
        // Do Nothing
      }
    }
  }

}
