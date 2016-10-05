package com.hossam.devloper.meplace.DataEncapsulation;

/**
 * Created by Hossam on 9/21/16.
 */
public class EncapUser {

    public String userID;
    public String userName;
    public String userPass;
    public String userPhone;
    public String userEmail;
    public String userAge;
    public String userHome; // name of local place of user
    private String userTitle;
    private String userState;
    private String userTargetPlace;
    private String userPost;
    private String userDate;
    private long userTime;

    public EncapUser(String userName, String userPass, String userPhone, String userEmail, String userAge, String userHome) {
        this.userName = userName;
        this.userPass = userPass;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.userHome = userHome;
    }

    public EncapUser() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserHome() {
        return userHome;
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }
}
