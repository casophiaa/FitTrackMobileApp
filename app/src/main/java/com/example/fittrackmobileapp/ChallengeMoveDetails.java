package com.example.fittrackmobileapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ChallengeMoveDetails implements Parcelable {
    private String moveName;
    private String duration;
    private String imageUrl;

    // Default constructor required for calls to DataSnapshot.getValue
    public ChallengeMoveDetails() {}

    // Parameterized constructor
    public ChallengeMoveDetails(String moveName, String duration, String imageUrl) {
        this.moveName = moveName;
        this.duration = duration;
        this.imageUrl = imageUrl;
    }

    // Parcelable implementation
    protected ChallengeMoveDetails(Parcel in) {
        moveName = in.readString();
        duration = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<ChallengeMoveDetails> CREATOR = new Creator<ChallengeMoveDetails>() {
        @Override
        public ChallengeMoveDetails createFromParcel(Parcel in) {
            return new ChallengeMoveDetails(in);
        }

        @Override
        public ChallengeMoveDetails[] newArray(int size) {
            return new ChallengeMoveDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moveName);
        dest.writeString(duration);
        dest.writeString(imageUrl);
    }

    // Getters and Setters
    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
