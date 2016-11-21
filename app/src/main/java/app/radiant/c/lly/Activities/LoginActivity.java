package app.radiant.c.lly.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;

public class LoginActivity extends AppCompatActivity {
    //Push
    Button loginBtn;
    EditText email, password;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.emailTxt);
        password = (EditText) findViewById(R.id.passwordTxt);

        account = (Account) getApplication();

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String e = email.getText().toString();
                String pw = password.getText().toString();

                if(e.length() > 0 && pw.length() > 0)
                    account.login(LoginActivity.this, email.getText().toString(), password.getText().toString());
            }
        });
    }
}
