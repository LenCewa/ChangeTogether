package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.Application;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.net.Uri;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.BieteFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.HomeFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.SearchFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.SearchItemFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.SuperProfileFragment;
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
import comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities.Logout;
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
    private String sessionId;
    public Uri uri;
    //private FragmentManager fm;

    public void login(Activity callingActivity, String email, String password) {

        self = new UserProfile();
        Login login = new Login(callingActivity, email, password);
        login.execute();
    }

    public void setSessionId(String sessionId){
        this.sessionId = sessionId;
    }

    public String getSessionId(){
       return sessionId;
    }

    public void getAccessToken(Activity callingActivity){

        GetAccessToken token = new GetAccessToken(callingActivity, getEmail(), getSessionId(), getEmail());
        token.execute();
    }

    public void loginWithAccessToken(Activity callingActivity, String email, String token){

        self = new UserProfile();
        LoginWithAccessToken login = new LoginWithAccessToken(callingActivity, email, token);
        login.execute();
    }

    public void logout(Activity callingActivity){

        Logout l = new Logout(callingActivity, getEmail(), getSessionId());
        l.execute();
    }

    public void deleteInfo(){
        self = null;
    }

    public void searchUser(SearchItemFragment callingActivity, String email, String tag, String description){

        SearchUser s = new SearchUser(callingActivity, getEmail(), getSessionId(), email, tag, description);
        s.execute();
    }

    public void setSearchedItem(UserProfile userProfile, String tag, String description){

        searchedItem = new SearchedItem(userProfile, tag, description);
    }

    public void homeShowBids(HomeFragment callingFragment, double lat, double lng){

        HomeShowBids h = new HomeShowBids(callingFragment, getEmail(), getSessionId(), lat, lng);
        h.execute();
    }

    public void loadBids(BieteFragment callingFragment){

        LoadBids l = new LoadBids(callingFragment, getEmail(), getSessionId(), getEmail());
        l.execute();
    }

    public void loadHelpingLocations(Fragment callingFragment) {
        LoadHelpingLocations l = new LoadHelpingLocations(callingFragment, getEmail());
        l.execute();
    }

    public void loadBidsActivity(SuperProfileFragment callingActivity, String searchedEmail){

        LoadBidsActivity l = new LoadBidsActivity(callingActivity, getEmail(), getSessionId(), searchedEmail);
        l.execute();
    }

    public void loadHelpingLocationsActivity(Fragment callingActivity) {
        LoadHelpingLocationsActivity l = new LoadHelpingLocationsActivity(callingActivity, getSearchEmail());
        l.execute();
    }

    public void searchBid(SearchFragment callingFragment, String tag, double lat, double lng){

        SearchBid s = new SearchBid(callingFragment, getEmail(), getSessionId(), tag, lat, lng);
        s.execute();
    }

    public void searchHelpingLocation(Fragment callingFragment, String tag){
        SearchHelpingLocation s = new SearchHelpingLocation(callingFragment, tag);
        s.execute();
    }

    public void addBid(Fragment callingFragment, String tag, String description, String location, double lat, double lng){

        AddBid a = new AddBid(callingFragment, getEmail(), getSessionId(), getEmail(), tag, description, location, lat, lng);
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

        SearchFeedback s = new SearchFeedback(callingActivity, getEmail(), getSessionId(), id, tag);
        s.execute();
    }

    public void addFeedback(DialogFragment callingDialog, int id,String tag, String text, float rating){
        AddFeedback a = new AddFeedback(callingDialog, getEmail(), getSessionId(), id, tag, getEmail(), text, rating);
        a.execute();
    }

    public void uploadProfilePic(Activity callingActivity, Bitmap pic){

        UploadImage u = new UploadImage(callingActivity, getEmail(), getSessionId(), getEmail(), pic);
        u.execute();
    }

    public void shoPic(Activity callingActivity){

        ShowPic s = new ShowPic(callingActivity, getEmail(), getSessionId(), getEmail());
        s.execute();
    }

    public void editPassword(Activity callingActivity, String oldPw, String newPw){

        AddInfo a = new AddInfo(callingActivity, getEmail(), getSessionId(), getEmail(), "password", oldPw, newPw);
        a.execute();
    }

    public void editLocation(Activity callingActivity, String location){

        AddInfo a = new AddInfo(callingActivity, getEmail(), getSessionId(), getEmail(), "location", location);
        a.execute();
    }

    public void editLanguage(Activity callingActivity, String language){

        AddInfo a = new AddInfo(callingActivity, getEmail(), getSessionId(), getEmail(), "language", language);
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