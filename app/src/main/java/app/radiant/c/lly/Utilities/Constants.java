package app.radiant.c.lly.Utilities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Yannick on 28.10.2016.
 */

public class Constants {

    public static String lastIdHome = "-1";
    public static String lastIdOwnBids = "-1";

    public static final String DBREGISTER = "http://change-together.spdns.org/app/register.php";
    public static final String DBLOGIN = "http://change-together.spdns.org/app/login.php";
    public static final String DBLOGOUT = "http://change-together.spdns.org/app/logout.php";
    public static final String DBGETACCESTOKEN = "http://change-together.spdns.org/app/getAccessToken.php";
    public static final String DBLOGINWITHACCESSTOKEN = "http://change-together.spdns.org/app/loginWithAccessToken.php";

    public static final String DBHOMESHOWBIDS = "http://change-together.spdns.org/app/homeShowBids.php";
    public static final String DBSEARCHBID = "http://change-together.spdns.org/app/searchBid.php";
    public static final String DBADDBID = "http://change-together.spdns.org/app/addBid.php";
    public static final String DBEDITBID = "http://change-together.spdns.org/app/editBid.php";
    public static final String DBLOADBID = "http://change-together.spdns.org/app/loadBids.php";
    public static final String DBLOADOWNBID = "http://change-together.spdns.org/app/loadOwnBids.php";
    public static final String DBDELETEBID = "http://change-together.spdns.org/app/deleteBid.php";
    public static final String DBPARTICIPATE = "http://change-together.spdns.org/app/participate.php";
    public static final String DBGETPARTICIPATIONS = "http://change-together.spdns.org/app/getParticipations.php";

    public static final String DBUPLOADPIC = "http://change-together.spdns.org/app/uploadPic.php";
    public static final String DBSHOWPIC = "http://change-together.spdns.org/app/showPic.php";
    public static final String DBEDITPASSWORD = "http://change-together.spdns.org/app/editPassword.php";
    public static final String DBEDITLOCATION = "http://change-together.spdns.org/app/editLocation.php";
    public static final String DBEDITLANGUAGE = "http://change-together.spdns.org/app/editLanguage.php";
    public static final String DBSEARCHUSER = "http://change-together.spdns.org/app/searchUser.php";
    public static final String DBSEARCHFEEDBACK = "http://change-together.spdns.org/app/searchFeedback.php";
    public static final String DBADDFEEDBACK = "http://change-together.spdns.org/app/addFeedback.php";

    public static final String USERTABLE = "User";
    public static final String SUPPLYTABLE = "Supply";
    public static final String BIDTABLE = "Bidding";

    public static final String EMAILROW = "email";
    public static final String BIDDINGTAG = "tag";

    // Auto Fill Location Name Google API Key String Constant
    public static final String PRIVATE_GOOGLE_API_KEY = "AIzaSyCDLrf4wg0oCpzYepocnaBMoXvoYws1cpw";
    public static final String PRIVATE_GOOGLE_FIREBASE_API_KEY = "AIzaSyCpfiikIRLYxFAoP_VIEXWElB4DiW-CLLU";


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeBitmap(byte[] pic, int length,
                                      int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(pic, 0, length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(pic, 0, length, options);
    }

    public static Bitmap decodeBitmap(Resources res, int resId,
                                      int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
