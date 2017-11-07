package com.example.student1.todolist;


import android.os.Parcel;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Task extends TaskObject {
    private String name;
    private Date expireDate;
//    private ArrayList<SubTask> subTaskList;

    public Task(){
        expireDate = new Date();
//        subTaskList = new ArrayList<>();
    }

    public String getExpireDateString(){
        return Constants.DATE_FORMAT.format(expireDate);
    }

    public boolean isExpired(){
        return expireDate.compareTo(new Date()) < 1;
    }

    public String getLeftTime(){
        long difference = expireDate.getTime() - System.currentTimeMillis();
        long values;
        String timeName = "";

        if (difference < 0){
            return "expired";
        } else {
            if ((values = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)) > 0){
                timeName = "day";
            }else if ((values = TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS)) > 0){
                timeName = "hour";
            }else if ((values = TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS)) > 0){
                timeName = "minute";
            }
        }
        if (values > 1)
            timeName += "s";
        return String.format(Locale.getDefault(), "%d " + timeName, values);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

/*    public ArrayList<SubTask> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(ArrayList<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }*/


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeLong(this.expireDate != null ? this.expireDate.getTime() : -1);
    }

    protected Task(Parcel in) {
        super(in);
        this.name = in.readString();
        long tmpExpireDate = in.readLong();
        this.expireDate = tmpExpireDate == -1 ? null : new Date(tmpExpireDate);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
