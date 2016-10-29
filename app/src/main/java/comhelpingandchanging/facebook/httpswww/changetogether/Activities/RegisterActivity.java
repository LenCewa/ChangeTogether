package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Register;

/**
 * Created by Yannick on 28.10.2016.
 */

public class RegisterActivity extends Activity {

    EditText email;
    EditText password;
    EditText passwordConfirm;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.emailTxt2);
        password = (EditText) findViewById(R.id.passwordTxt2);
        passwordConfirm = (EditText) findViewById(R.id.passwordConfirmTxt);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().length() != 0) {
                    if (password.getText().toString().length() != 0 && passwordConfirm.getText().toString().length() != 0) {
                        if (password.getText().toString().equals(passwordConfirm.getText().toString())) {
                            Register register = new Register(RegisterActivity.this, email.getText().toString(), password.getText().toString());
                            register.execute();
                        } else
                            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(RegisterActivity.this, "Password can not be empty", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(RegisterActivity.this, "E-Mail can not be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
