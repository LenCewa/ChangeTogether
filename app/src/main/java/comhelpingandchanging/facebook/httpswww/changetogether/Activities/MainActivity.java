package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;


/**
 * Created by Yannick on 28.10.2016.
 */

public class MainActivity extends Activity {

    Button login;
    Button register;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        boolean isOnline = sp.getBoolean("onlineStatus", false);
        String email = sp.getString("email", "");
        String password = sp.getString("password", "");

        if(isOnline) {
            account.setOnlineStatus(isOnline);
            account.login(this, email, password);
        }

        setContentView(R.layout.activity_main);
        account = (Account) getApplication();

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
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("onlineStatus", account.getOnlineStatus());
        editor.putString("email", account.getEmail());
        editor.putString("password", account.getPassword());
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
