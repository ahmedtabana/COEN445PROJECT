package coen445.server;

import java.io.Serializable;

/**
 * Created by Ahmed on 15-11-28.
 */
public class DateTime implements Serializable{

    private int day;
    private int month;
    private int year;
    private int time;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


}
