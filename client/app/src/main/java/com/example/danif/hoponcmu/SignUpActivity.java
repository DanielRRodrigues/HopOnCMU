package com.example.danif.hoponcmu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.danif.hoponcmu.client.CommandHandlerImpl;
import com.example.danif.hoponcmu.command.Command;
import com.example.danif.hoponcmu.command.HelloCommand;
import com.example.danif.hoponcmu.command.SignUpCommand;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Button btnSignUp = (Button) findViewById(R.id.btnSignUp_SignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<Void, Void, Void> task = new SignUpAction();
                task.execute();
            }
        });
    }

    public class SignUpAction extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            CommandHandlerImpl chi = new CommandHandlerImpl();

            /*EditText editUsername = (EditText) findViewById(R.id.signUpUsername);
            String username = editUsername.getText().toString();
            EditText editCode = (EditText) findViewById(R.id.signUpCode);
            String code = editCode.getText().toString();

            SignUpCommand suc = new SignUpCommand(username, code);
            suc.handle(chi);*/
            HelloCommand hc = new HelloCommand("Wazzzuuuup");
            hc.handle(chi);

            return null;
        }
    }
}
