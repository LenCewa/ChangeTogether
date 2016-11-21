package app.radiant.c.lly.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;


/**
 * Created by Yannick on 28.10.2016.
 */

public class MainActivity extends AppCompatActivity {

    Button login;
    Button register;
    Account account;
    CheckBox stayLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = (Account) getApplication();
        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        if(!sp.getString("email","").equals("") && !sp.getString("accessToken","").equals(""))
            account.loginWithAccessToken(this, sp.getString("email",""), sp.getString("accessToken",""));

        login = (Button) findViewById(R.id.loginBtn);
        register = (Button) findViewById(R.id.registerBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
