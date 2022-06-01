package com.example.havefun.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Timestamp {
    long seconds;
    long nanoseconds;
    public Timestamp(long seconds, long nanoseconds) {
        this.seconds = seconds;
        this.nanoseconds = nanoseconds;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime toLocalDateTime(){
        Instant instant = Instant.ofEpochSecond(this.seconds,this.nanoseconds);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        Instant instant = Instant.ofEpochSecond(this.seconds,this.nanoseconds);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    public Timestamp() {
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getNanoseconds() {
        return nanoseconds;
    }

    public void setNanoseconds(long nanoseconds) {
        this.nanoseconds = nanoseconds;
    }
}
