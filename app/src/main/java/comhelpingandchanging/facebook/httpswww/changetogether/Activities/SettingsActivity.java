package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.PlacesAutoCompleteAdapter;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.UploadImage;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Ludwig on 29.10.2016.
 */

public class SettingsActivity extends Activity {
    private static final int PICK_IMAGE = 1;
    Button save;
    String city;
    Account account;
    AutoCompleteTextView location;
    EditText language;
    EditText currentPassword;
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
        location = (AutoCompleteTextView) findViewById(R.id.changeLocation);
        language = (EditText) findViewById(R.id.changeLanguage);
        currentPassword = (EditText)findViewById(R.id.password);
        password = (EditText) findViewById(R.id.changePassword);
        passwordConfirm = (EditText) findViewById(R.id.ConfirmPassword);
        profilePic = (ImageView) findViewById(R.id.changeProfilePic);

        profilePic.setImageBitmap(account.getProfilePic());
        location.setText(account.getLocation());
        language.setText(account.getLanguage());
        city = account.getLocation();

        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.changeLocation);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
            }
        });

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
                    String currentPw = currentPassword.getText().toString();
                    String pw = password.getText().toString();
                    String pwConfirm = passwordConfirm.getText().toString();

                    if (loc.length() > 0 && city.equals(location.getText().toString())) {
                            account.setLocation(loc);
                            account.editLocation(SettingsActivity.this, loc);
                    } else
                            location.setError("Select existing location");

                    if (lang.length() > 0) {
                            account.setLanguage(lang);
                            account.editLanguage(SettingsActivity.this, lang);
                    }

                    if (currentPw.length() > 0 && pw.length() > 0 && pwConfirm.length() > 0) {
                        if (pw.equals(pwConfirm)) {
                                account.editPassword(SettingsActivity.this, currentPw, pw);
                        } else
                                passwordConfirm.setError("Passwords do not match");
                    }

                    if(city.equals(location.getText().toString()) && pw.equals(pwConfirm))
                        finish();
                }
            });
        };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImageUri = data.getData();
                
                    String s = getRealPathFromURI(selectedImageUri);
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inSampleSize = 2;
                    bitmap = BitmapFactory.decodeFile(s, o);
                    profilePic.setImageBitmap(bitmap);
                    account.setProfilePic(bitmap);

                    UploadImage u = new UploadImage(this, account.getEmail(), account.getSessionId(), account.getEmail(), bitmap);
                    u.execute();
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
