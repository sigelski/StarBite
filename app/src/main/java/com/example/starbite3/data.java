package com.example.starbite3;

public class data {
         String username = "mattb996";
         String password = "AuburnHacks1";
         String appId = "d8665ade-9673-4e27-9ff6-92db4ce13d13";
         String sessionId = "0000000-0000-0000-0000-00000000000";

         String glucose = "";

         int trend = 135;

    public String getUsername() {
        return username;
    }

    public  String getPassword() {
        return password;
    }

    public  String getAppId() {
        return appId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getGlucose(){
        return glucose;
    }

    public void setGlucose(String glucose){
        this.glucose = glucose;
    }

    public int getTrend(){
        return trend;
    }

    public void setTrend(String trend){
        if(trend.compareTo("DoubleUp") == 0){
            this.trend = 45;
        }
        else if(trend.compareTo("SingleUp") == 0){
            this.trend = 45;
        }
        else if(trend.compareTo("FortyFiveUp") == 0){
            this.trend = 90;
        }
        else if(trend.compareTo("Flat") == 0){
            this.trend = 135;
        }
        else if(trend.compareTo("FortyFiveDown") == 0){
            this.trend = 180;
        }
        else if(trend.compareTo("SingleDown") == 0){
            this.trend = 225;
        }
        else if(trend.compareTo("DoubleDown") == 0){
            this.trend = 225;
        }
        else {
            this.trend = 135;
        }
    }
}