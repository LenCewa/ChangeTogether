package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.SearchItemFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchedItem {

    private Context context;
    private String[] searchedItem;
    private String id;
    private String email;
    private String tag;
    private String description;
    private String location;
    private float averageRating;
    private String count;
    private String distance;
    private String date;
    private String time;
    private String part;
    private String maxParticipators;
    private String encodedPic;

    public SearchedItem(Context context, String id, String email, String tag, String description, String location, String averageRating, String count, String distance, String date, String time, String part, String maxParticipators, String encodedPic){

        this.context = context;
        searchedItem = new String[] {id, email, tag, description, location, averageRating, count, distance, date, time, part, maxParticipators, encodedPic};
        this.id = id;
        this.email = email;
        this.tag = tag;
        this.description = description;
        this.location = location;
        this.averageRating = Float.parseFloat(averageRating);
        this.count = count;
        this.distance = distance;
        this.date = date;
        this.time = time;
        this.part = part;
        this.maxParticipators = maxParticipators;
        this.encodedPic = encodedPic;
    }

    public String[] getSearchedItem() { return searchedItem;}

    public String getId(){
        return id;
    }

    public String getEmail() { return email; }

    public String getTag(){
        return tag;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation(){
        return location;
    }

    public float getAverageRating(){
        return averageRating;
    }

    public String getCount(){
        return count;
    }

    public String getDistance(){
        return distance;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getParticipators(){ return part; }

    public String getMaxParticipators(){
        return maxParticipators;
    }

    public Bitmap getProfilePic(){

        if(encodedPic.length() > 0) {
            byte[] decodedString = Base64.decode(encodedPic, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.blank_profile_pic);
            return bitmap;
        }
    }
}
