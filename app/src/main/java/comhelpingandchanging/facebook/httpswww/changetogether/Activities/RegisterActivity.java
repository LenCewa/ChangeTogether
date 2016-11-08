package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.Register;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 28.10.2016.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText passwordConfirm;
    Button register;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        account = (Account) getApplication();
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
