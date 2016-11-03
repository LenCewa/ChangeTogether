package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.UploadImage;

/**
 * Created by Ludwig on 29.10.2016.
 */

public class SettingsActivity extends Activity {
    private static final int PICK_IMAGE = 1;
    Button save;
    Account account;
    EditText location;
    EditText language;
    EditText password;
    EditText passwordConfirm;
    ImageView profilePic;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        account = (Account) getApplication();
        save = (Button) findViewById(R.id.Save);
        location = (EditText) findViewById(R.id.changeLocation);
        language = (EditText) findViewById(R.id.changeLanguage);
        password = (EditText) findViewById(R.id.changePassword);
        passwordConfirm = (EditText) findViewById(R.id.ConfirmPassword);
        profilePic = (ImageView) findViewById(R.id.changeProfilePic);

        profilePic.setImageBitmap(account.getProfilePic());

        location.setText(account.getLocation());
        language.setText(account.getLanguage());

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().length() == 0 && passwordConfirm.getText().toString().length() == 0) {
                    if (bitmap != null) {
                        account.addUserInfo(SettingsActivity.this, account.getPassword(), location.getText().toString(), language.getText().toString(), bitmap);
                        account.uploadProfilePic(SettingsActivity.this, bitmap);
                    }
                    else
                        account.addUserInfo(SettingsActivity.this, account.getPassword(), location.getText().toString(), language.getText().toString(), ((BitmapDrawable)profilePic.getDrawable()).getBitmap());
                }
                else {
                    if (password.getText().toString().equals(passwordConfirm.getText().toString())) {
                        if (bitmap != null) {
                            account.addUserInfo(SettingsActivity.this, password.getText().toString(), location.getText().toString(), language.getText().toString(), bitmap);
                            account.uploadProfilePic(SettingsActivity.this, bitmap);
                        }
                        else
                            account.addUserInfo(SettingsActivity.this, password.getText().toString(), location.getText().toString(), language.getText().toString(), ((BitmapDrawable) profilePic.getDrawable()).getBitmap());
                    }
                    else
                        Toast.makeText(SettingsActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    profilePic.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
