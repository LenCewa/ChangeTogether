package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
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
                String loc = location.getText().toString();
                String lang = language.getText().toString();
                String pw = password.getText().toString();
                String pwConfirm = passwordConfirm.getText().toString();

                if(loc.length() > 0 && !loc.equals(account.getLocation())){
                    account.setLocation(loc);
                    account.editLocation(SettingsActivity.this, loc);
                }

                if(lang.length() > 0 && !lang.equals(account.getLanguage())){
                    account.setLanguage(lang);
                    account.editLanguage(SettingsActivity.this, lang);
                }

                if(pw.length() > 0 && pwConfirm.length() > 0) {
                    if (pw.equals(pwConfirm) && !pw.equals(account.getPassword())) {
                        account.setPassword(pw);
                        account.editPassword(SettingsActivity.this, pw);
                    } else
                        Toast.makeText(SettingsActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                if(loc.equals(account.getLocation()) && lang.equals(account.getLanguage()) && ((pw.length() == 0 && pwConfirm.length() == 0) || pw.equals(account.getPassword()) && pwConfirm.equals(account.getPassword())))
                    finish();
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
                    account.setProfilePic(bitmap);

                    UploadImage u = new UploadImage(this, account.getEmail(), bitmap);
                    u.execute();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
