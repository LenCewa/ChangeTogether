package app.radiant.c.lly.Activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.radiant.c.lly.NetworkUtilities.Register;
import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;
import app.radiant.c.lly.Utilities.Constants;

/**
 * Created by Yannick on 28.10.2016.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText passwordConfirm;
    Button register;
    Account account;
    ImageView imageView;

    // Befidet sich ein Nutzer in der MainAppActivity wurde er eingeloggt und wird hier
    // im Hintergrund in Firebase eingeloggt
    private FirebaseAuth userAuth;
    private FirebaseAuth.AuthStateListener userAuthListener;
    private static final String TAG = "RegisterActivity";
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userAuth = FirebaseAuth.getInstance();
        userAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.e(TAG, "onAuthStateChanged:sign_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.e(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, r.getDisplayMetrics());
        imageView = ((ImageView) findViewById(R.id.imageView3));
        imageView.setImageBitmap(Constants.decodeBitmap(r, R.drawable.logo, (int)px, (int)px));

        account = (Account) getApplication();
        email = (EditText) findViewById(R.id.emailTxt2);
        password = (EditText) findViewById(R.id.passwordTxt2);
        passwordConfirm = (EditText) findViewById(R.id.passwordConfirmTxt);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail(email.getText().toString())) {
                    if (password.getText().toString().length() != 0 && passwordConfirm.getText().toString().length() != 0) {
                        if (password.getText().toString().equals(passwordConfirm.getText().toString())) {
                            createUser(email.getText().toString(), Constants.FIREBASE_DEFAULT_PASSWORD);
                            Register register = new Register(RegisterActivity.this, email.getText().toString(), password.getText().toString());
                            register.execute();
                        } else
                            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(RegisterActivity.this, "Password can not be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "E-Mail can not be empty / or incorrect form", Toast.LENGTH_SHORT).show();
                    Log.e("FUCK: ", email.toString());
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        userAuth.addAuthStateListener(userAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (userAuthListener != null) {
            userAuth.removeAuthStateListener(userAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageView.setImageBitmap(null);
    }

    private void createUser(String email, String password) {
        userAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.e(TAG, "createUser:failed", task.getException());
                            Log.e(TAG, task.getException().getMessage());
                        }
                    }
                });
    }

    public final static boolean validateEmail(String email) {
        CharSequence cs = email;
        if (cs == null) {
            Log.d("FUCK validate if", cs.toString());
            return false;
        }
        else {
            Log.d("FUCK validate else", cs.toString());
            return android.util.Patterns.EMAIL_ADDRESS.matcher(cs).matches();
        }
    }
}
