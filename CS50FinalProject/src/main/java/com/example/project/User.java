package com.example.project;

public class User {

    private Boolean Google;
    private Boolean Apple;
    private Boolean Microsoft;
    private Boolean Tesla;
    private Boolean Amazon;
    private String dateValue;
    private String firstChoice;
    private String secondChoice;
    private String thirdChoice;
    private String fourthChoice;
    private String fifthChoice;

    public User() {
    }

    public User(Boolean google, Boolean apple, Boolean microsoft, Boolean tesla, Boolean amazon, String dateValue, String firstChoice, String secondChoice, String thirdChoice, String fourthChoice, String fifthChoice) {
        Google = google;
        Apple = apple;
        Microsoft = microsoft;
        Tesla = tesla;
        Amazon = amazon;
        this.dateValue = dateValue;
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        this.thirdChoice = thirdChoice;
        this.fourthChoice = fourthChoice;
        this.fifthChoice = fifthChoice;
    }

    public Boolean getGoogle() {
        return Google;
    }

    public void setGoogle(Boolean google) {
        Google = google;
    }

    public Boolean getApple() {
        return Apple;
    }

    public void setApple(Boolean apple) {
        Apple = apple;
    }

    public Boolean getMicrosoft() {
        return Microsoft;
    }

    public void setMicrosoft(Boolean microsoft) {
        Microsoft = microsoft;
    }

    public Boolean getTesla() {
        return Tesla;
    }

    public void setTesla(Boolean tesla) {
        Tesla = tesla;
    }

    public Boolean getAmazon() {
        return Amazon;
    }

    public void setAmazon(Boolean amazon) {
        Amazon = amazon;
    }

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    public String getFirstChoice() {
        return firstChoice;
    }

    public void setFirstChoice(String firstChoice) {
        this.firstChoice = firstChoice;
    }

    public String getSecondChoice() {
        return secondChoice;
    }

    public void setSecondChoice(String secondChoice) {
        this.secondChoice = secondChoice;
    }

    public String getThirdChoice() {
        return thirdChoice;
    }

    public void setThirdChoice(String thirdChoice) {
        this.thirdChoice = thirdChoice;
    }

    public String getFourthChoice() {
        return fourthChoice;
    }

    public void setFourthChoice(String fourthChoice) {
        this.fourthChoice = fourthChoice;
    }

    public String getFifthChoice() {
        return fifthChoice;
    }

    public void setFifthChoice(String fifthChoice) {
        this.fifthChoice = fifthChoice;
    }

    @Override
    public String toString() {
        return "User{" +
                "Google=" + Google +
                ", Apple=" + Apple +
                ", Microsoft=" + Microsoft +
                ", Tesla=" + Tesla +
                ", Amazon=" + Amazon +
                ", dateValue='" + dateValue + '\'' +
                ", firstChoice='" + firstChoice + '\'' +
                ", secondChoice='" + secondChoice + '\'' +
                ", thirdChoice='" + thirdChoice + '\'' +
                ", fourthChoice='" + fourthChoice + '\'' +
                ", fifthChoice='" + fifthChoice + '\'' +
                '}';
    }
}
