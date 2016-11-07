package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.Application;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.BieteFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.HomeFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchItemActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.AddBid;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.AddFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.AddHelpingLocation;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.AddInfo;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.DeleteBid;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.DeleteHelpingLocations;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.GetAccessToken;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.HomeShowBids;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.LoadBids;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.LoadBidsActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.LoadHelpingLocations;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.LoadHelpingLocationsActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.Login;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.LoginWithAccessToken;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.SearchBid;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.SearchFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.SearchHelpingLocation;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.SearchUser;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.ShowPic;
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.UploadImage;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Account extends Application {

    private UserProfile self = null;
    private SearchedItem searchedItem = null;
    //private FragmentManager fm;

    public void login(Activity callingActivity, String email, String password) {

        self = new UserProfile();
        Login login = new Login(callingActivity, email, password);
        login.execute();
    }

    public void getAccessToken(Activity callingActivity){

        GetAccessToken token = new GetAccessToken(callingActivity, getEmail());
        token.execute();
    }

    public void loginWithAccessToken(Activity callingActivity, String email, String token){

        self = new UserProfile();
        LoginWithAccessToken login = new LoginWithAccessToken(callingActivity, email, token);
        login.execute();
    }

    public void logout(){

        self = null;
        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("stayLoggedIn");
        editor.remove("email");
        editor.remove("password");
        editor.apply();
    }

    public void searchUser(SearchItemActivity callingActivity, String email, String tag, String description){

        SearchUser s = new SearchUser(callingActivity, email, tag, description);
        s.execute();
    }

    public void setSearchedItem(UserProfile userProfile, String tag, String description){

        searchedItem = new SearchedItem(userProfile, tag, description);
    }

    public void homeShowBids(HomeFragment callingFragment, double lat, double lng){

        HomeShowBids h = new HomeShowBids(callingFragment, lat, lng);
        h.execute();
    }

    public void loadBids(BieteFragment callingFragment){

        LoadBids l = new LoadBids(callingFragment, getEmail());
        l.execute();
    }

    public void loadHelpingLocations(Fragment callingFragment) {
        LoadHelpingLocations l = new LoadHelpingLocations(callingFragment, getEmail());
        l.execute();
    }

    public void loadBidsActivity(ProfileActivity callingActivity){

        LoadBidsActivity l = new LoadBidsActivity(callingActivity, getSearchEmail());
        l.execute();
    }

    public void loadHelpingLocationsActivity(Activity callingActivity) {
        LoadHelpingLocationsActivity l = new LoadHelpingLocationsActivity(callingActivity, getSearchEmail());
        l.execute();
    }

    public void searchBid(SearchFragment callingFragment, String tag, double lat, double lng){

        SearchBid s = new SearchBid(callingFragment, tag, lat, lng);
        s.execute();
    }

    public void searchHelpingLocation(Fragment callingFragment, String tag){
        SearchHelpingLocation s = new SearchHelpingLocation(callingFragment, tag);
        s.execute();
    }

    public void addBid(Fragment callingFragment, String tag, String description, String location, double lat, double lng){

        AddBid a = new AddBid(callingFragment, getEmail(), tag, description, location, lat, lng);
        a.execute();
    }

    public void addHelpingLocation(Fragment callingFragment, String tag, String description){

        AddHelpingLocation a = new AddHelpingLocation(callingFragment, getEmail(), tag, description);
        a.execute();
    }

    public void deleteBid(Fragment callingFragment, String tag, String description){

        DeleteBid d = new DeleteBid(callingFragment, getEmail(), tag, description);
        d.execute();
    }

    public void deleteHelpingLocations(String tag, String description) {
        DeleteHelpingLocations d = new DeleteHelpingLocations(getEmail(), tag, description);
        d.execute();
    }

    public void searchFeedback(ShowBidFeedback callingActivity, int id, String tag) {

        SearchFeedback s = new SearchFeedback(callingActivity, id, tag);
        s.execute();
    }

    public void addFeedback(DialogFragment callingDialog, int id,String tag, String text, float rating){
        AddFeedback a = new AddFeedback(callingDialog, id, tag, getEmail(), text, rating);
        a.execute();
    }

    public void uploadProfilePic(Activity callingActivity, Bitmap pic){

        UploadImage u = new UploadImage(callingActivity, getEmail(), pic);
        u.execute();
    }

    public void showPic(Activity callingActivity){

        ShowPic s = new ShowPic(callingActivity, getEmail());
        s.execute();
    }

    public void editPassword(Activity callingActivity, String password){

        AddInfo a = new AddInfo(callingActivity, getEmail(), "password", password);
        a.execute();
    }

    public void editLocation(Activity callingActivity, String location){

        AddInfo a = new AddInfo(callingActivity, getEmail(), "location", location);
        a.execute();
    }

    public void editLanguage(Activity callingActivity, String language){

        AddInfo a = new AddInfo(callingActivity, getEmail(), "language", language);
        a.execute();
    }


    public String getEmail(){
        return self.getUsername();
    }

    public String getLocation(){
        return self.getLocation();
    }

    public String getLanguage(){
        return self.getLanguage();
    }

    public Bitmap getProfilePic(){ return self.getProfilePic(); }


    public String getSearchTag(){
        return searchedItem.getTag();
    }

    public String getSearchDescription(){
        return searchedItem.getDescription();
    }

    public String getSearchEmail(){
        return searchedItem.getUsername();
    }

    public String getSearchLocation(){
        return searchedItem.getLocation();
    }

    public String getSearchLanguage(){
        return searchedItem.getLanguage();
    }

    public Bitmap getSearchProfilePic(){ return searchedItem.getProfilePic(); }


    /*public FragmentManager getFragmentManager(){
        return fm;
    }*/

    public void setEmail(String email){
        self.setEmail(email);
    }

    public void setLocation(String location){
        self.setLocation(location);
    }

    public void setLanguage(String language){
        self.setLanguage(language);
    }

    public void setProfilePic(Bitmap profilePic){
        self.setProfilePic(profilePic);
    }

    /*public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }*/
}