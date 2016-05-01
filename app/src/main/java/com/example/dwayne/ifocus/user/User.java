package com.example.dwayne.ifocus.user;

import java.io.Serializable;

/**
 * Created by Sneha on 4/29/2016.
 */
public class User implements Serializable{
    private String userName;
    private String userEmailID;
    private String userType;
    private String userPassword;
    private String userPartnerEmail;
    private int userScore;
    private int userBudget;
    private static User user;

    public static User getInstance(){
        if(user==null){
            user = new User();
        }
        return user;
    }

    private User(){
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPartnerEmail() {
        return userPartnerEmail;
    }

    public void setUserPartnerEmail(String userPartnerEmail) {
        this.userPartnerEmail = userPartnerEmail;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserBudget() {
        return userBudget;
    }

    public void setUserBudget(int userBudget) {
        this.userBudget = userBudget;
    }

}
