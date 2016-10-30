package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;


/**
 * Created by Yannick on 28.10.2016.
 */

public class MainActivity extends Activity {

    Button login;
    Button register;
    Account account;
    CheckBox stayLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        account = (Account) getApplication();
        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        setContentView(R.layout.activity_main);

        stayLoggedIn = (CheckBox) findViewById(R.id.checkBox);
        stayLoggedIn.setChecked(sp.getBoolean("stayLoggedIn", false));
        stayLoggedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("stayLoggedIn", stayLoggedIn.isChecked());
                editor.commit();
            }
        });

        if(stayLoggedIn.isChecked() && !sp.getString("email", "").equals("") && !sp.getString("password", "").equals(""))
            account.login(this, sp.getString("email",""), sp.getString("password",""));

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
