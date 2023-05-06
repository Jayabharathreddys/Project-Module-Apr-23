package com.jaya;

public class Greeting {

    public String greeting(){
        int hour = java.time.LocalTime.now().getHour();
        if(hour >= 0 && hour <= 12){
            return "Good Morning";
        }else if(hour >= 12 && hour <= 16){
            return "Good Afternoon";
        }else
            return  "Good Evening";
    }
}
