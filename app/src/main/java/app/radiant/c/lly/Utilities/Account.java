package app.radiant.c.lly.Utilities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

import app.radiant.c.lly.Activities.ShowBidFeedbackActivity;
import app.radiant.c.lly.Fragments.BieteFragment;
import app.radiant.c.lly.Fragments.HomeFragment;
import app.radiant.c.lly.Fragments.OwnBidsFragment;
import app.radiant.c.lly.Fragments.ProfileFragment;
import app.radiant.c.lly.Fragments.SearchFragment;
import app.radiant.c.lly.Fragments.SearchItemFragment;
import app.radiant.c.lly.Fragments.SuperProfileFragment;
import app.radiant.c.lly.NetworkUtilities.AddBid;
import app.radiant.c.lly.NetworkUtilities.AddFeedback;
import app.radiant.c.lly.NetworkUtilities.AddInfo;
import app.radiant.c.lly.NetworkUtilities.DeleteBid;
import app.radiant.c.lly.NetworkUtilities.GetAccessToken;
import app.radiant.c.lly.NetworkUtilities.HomeShowBids;
import app.radiant.c.lly.NetworkUtilities.LoadBids;
import app.radiant.c.lly.NetworkUtilities.LoadOwnBids;
import app.radiant.c.lly.NetworkUtilities.Login;
import app.radiant.c.lly.NetworkUtilities.LoginWithAccessToken;
import app.radiant.c.lly.NetworkUtilities.Logout;
import app.radiant.c.lly.NetworkUtilities.Participate;
import app.radiant.c.lly.NetworkUtilities.SearchBid;
import app.radiant.c.lly.NetworkUtilities.SearchFeedback;
import app.radiant.c.lly.NetworkUtilities.SearchUser;
import app.radiant.c.lly.NetworkUtilities.UploadImage;
import app.radiant.c.lly.NetworkUtilities.EditBid;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Account extends Application {

    private UserProfile self = null;
    private UserProfile searchedUser = new UserProfile();
    private SearchedItem searchedItem = null;
    private boolean searchSet = false;
    private String sessionId;
    public HashMap<String, Bitmap> userPics = new HashMap<>();
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
        searchedUser = new UserProfile();
        searchedItem = null;
        searchSet = false;
        Constants.lastIdHome = "-1";
        Constants.lastIdOwnBids = "-1";
    }

    public void searchUer(ProfileFragment callingActivity){

        SearchUser s = new SearchUser(callingActivity, getEmail(), getSessionId(), getSearchEmail());
        s.execute();
    }

    public void setSearchedUser(String location, String language){

        searchedUser.setLocation(location);
        searchedUser.setLanguage(language);
    }

    public void setSearchedItem(Context context, String id, String email, String tag, String description, String location, String averageRating, String count, String distance, String date, String time, String part, String maxParticipators, String encodedPic){

        searchSet = true;
        searchedItem = new SearchedItem(context, id, email, tag, description, location, averageRating, count, distance, date, time, part, maxParticipators, encodedPic);
    }

    public void homeShowBids(HomeFragment callingFragment, double lat, double lng){

        HomeShowBids h = new HomeShowBids(callingFragment, getEmail(), getSessionId(), lat, lng, Constants.lastIdHome);
        h.execute();
    }

    public void loadOwnBids(BieteFragment callingActivity){

        LoadOwnBids l = new LoadOwnBids(callingActivity, getEmail(), getSessionId(), getEmail(), getLat(), getLng(), Constants.lastIdOwnBids);
        l.execute();
    }

    public void loadOwnBids(OwnBidsFragment callingActivity){

        LoadOwnBids l = new LoadOwnBids(callingActivity, getEmail(), getSessionId(), getEmail(), getLat(), getLng(), Constants.lastIdOwnBids);
        l.execute();
    }

    public void loadBidsActivity(SuperProfileFragment callingActivity, String searchedEmail, double lat, double lng){

        LoadBids l = new LoadBids(callingActivity, getEmail(), getSessionId(), searchedEmail, lat, lng);
        l.execute();
    }

    public void searchBid(SearchFragment callingFragment, String tag, double lat, double lng){

        SearchBid s = new SearchBid(callingFragment, getEmail(), getSessionId(), tag, lat, lng);
        s.execute();
    }

    public void addBid(Fragment callingFragment, String tag, String description, String location, double lat, double lng, String date, String time, String maxParticipators){

        AddBid a = new AddBid(callingFragment, getEmail(), getSessionId(), getEmail(), tag, description, location, lat, lng, date, time, maxParticipators);
        a.execute();
    }

    public void editBid(Fragment callingFragment, String id, String tag, String description, String location, double lat, double lng, String date, String time, String maxParticipators){

        EditBid e = new EditBid(callingFragment, getEmail(), getSessionId(), id, getEmail(), tag, description, location, lat, lng, date, time, maxParticipators);
        e.execute();
    }

    public void participate(SearchItemFragment callingFragment, String bidId, String email){

        Participate p = new Participate(callingFragment, getEmail(), getSessionId(), bidId, email);
        p.execute();
    }

    public void deleteBid(Fragment callingFragment, String tag, String description){

        DeleteBid d = new DeleteBid(callingFragment, getEmail(), tag, description);
        d.execute();
    }

    public void searchFeedback(ShowBidFeedbackActivity callingActivity, int id, String tag) {

        SearchFeedback s = new SearchFeedback(callingActivity, getEmail(), getSessionId(), id, tag);
        s.execute();
    }

    public void addFeedback(DialogFragment callingDialog, int id, String tag, String text, float rating){
        AddFeedback a = new AddFeedback(callingDialog, getEmail(), getSessionId(), id, tag, getEmail(), text, rating);
        a.execute();
    }

    public void uploadProfilePic(Activity callingActivity, Bitmap pic){

        UploadImage u = new UploadImage(callingActivity, getEmail(), getSessionId(), getEmail(), pic);
        u.execute();
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

    public double getLat(){ return self.getLat(); }

    public double getLng(){ return self.getLng(); }

    public ArrayList<String[]> getParticipations() { return self.getParticipations(); }

    public ArrayList<String[]> getOwnBids() { return self.getOwnBids(); }

    public String[] getSearchedItem(){

        if(searchSet)
            return searchedItem.getSearchedItem();
        else
            return null;
    }

    public String getSearchID(){

        if(searchSet)
            return searchedItem.getId();
        else
            return null;
    }

    public String getSearchTag(){

        if(searchSet)
            return searchedItem.getTag();
        else
            return null;
    }

    public String getSearchDescription(){

        if(searchSet)
            return searchedItem.getDescription();
        else
            return null;
    }

    public String getSearchLocation(){

        if(searchSet)
            return searchedItem.getLocation();
        else
            return null;
    }

    public float getSearchAverageRating(){

        if(searchSet)
            return searchedItem.getAverageRating();
        else
            return 0;
    }

    public String getSearchCount(){

        if(searchSet)
            return searchedItem.getCount();
        else
            return null;
    }

    public String getSearchDistance(){

        if(searchSet)
            return searchedItem.getDistance();
        else
            return null;
    }

    public String getSearchDate(){

        if(searchSet)
            return searchedItem.getDate();
        else
            return null;
    }

    public String getSearchTime(){

        if(searchSet)
            return searchedItem.getTime();
        else
            return null;
    }

    public String getSearchParticipators(){

        if(searchSet)
            return searchedItem.getParticipators();
        else
            return null;
    }

    public String getSearchMaxParticipators(){

        if(searchSet)
            return searchedItem.getMaxParticipators();
        else
            return null;
    }

    public String getSearchEmail(){

        if(searchSet)
            return searchedItem.getEmail();
        else
            return null;
    }

    public String getSearchUserLocation(){

        return searchedUser.getLocation();
    }

    public String getSearchLanguage(){

        return searchedUser.getLanguage();
    }

    public Bitmap getSearchProfilePic(){

        return searchedUser.getProfilePic();
    }

    public boolean isUserSearched(){

        if(searchedUser == null)
            return false;
        else
            return true;
    }

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

    public void setLat(double lat){ self.setLat(lat);}

    public void setLng(double lng) { self.setLng(lng);}

    public void setParticipations(ArrayList<String[]> l) { self.setParticipations(l);}

    public void setOwnBids(ArrayList<String[]> l) { self.setOwnBids(l);}

    public void setSearchedUserEmail(String email){
        searchedUser.setEmail(email);
    }

    public void setSearchedUserProfilePic(Bitmap profilePic){
        searchedUser.setProfilePic(profilePic);
    }

    /*public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }*/
}