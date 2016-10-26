package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Login;

public class MainActivity extends AppCompatActivity {
    //Push
    Button login;
    EditText email, password;
    boolean connectionEstablished = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.emailTxt);
        password = (EditText) findViewById(R.id.passwordTxt);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Login l = new Login(email.getText().toString(), password.getText().toString());
                l.execute();

                while(l.isFinished() != true);

                if(l.isConnectionEstablished()) {
                    Intent search = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(search);
                }
            }
        });
    }
}
