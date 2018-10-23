package com.unrealmojo.hamsters.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Hamster implements Comparable, Parcelable {
    private String title;
    private String description;
    private boolean pinned;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Hamster{" +
                "title='" + title + '\'' +
                ", desc='" + description + '\'' +
                ", pinned=" + pinned +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Hamster right = (Hamster) o;
        if (isPinned() && !right.isPinned()) return -1;
        else if (right.isPinned()) return 1;
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeByte(this.pinned ? (byte) 1 : (byte) 0);
        dest.writeString(this.image);
    }

    public Hamster() {
    }

    protected Hamster(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.pinned = in.readByte() != 0;
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Hamster> CREATOR = new Parcelable.Creator<Hamster>() {
        @Override
        public Hamster createFromParcel(Parcel source) {
            return new Hamster(source);
        }

        @Override
        public Hamster[] newArray(int size) {
            return new Hamster[size];
        }
    };
}
