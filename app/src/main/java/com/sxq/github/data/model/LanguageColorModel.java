package com.sxq.github.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LanguageColorModel implements Parcelable {
    public String color;
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);
        dest.writeString(this.url);
    }

    public LanguageColorModel() {
    }

    private LanguageColorModel(Parcel in) {
        this.color = in.readString();
        this.url = in.readString();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static final Creator<LanguageColorModel> CREATOR = new Creator<LanguageColorModel>() {
        @Override
        public LanguageColorModel createFromParcel(Parcel source) {
            return new LanguageColorModel(source);
        }

        @Override
        public LanguageColorModel[] newArray(int size) {
            return new LanguageColorModel[size];
        }
    };
}

