package com.unrealmojo.hamsters.models;

public class Hamster implements Comparable{
    private String title;
    private String desc;
    private boolean pinned;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
                ", desc='" + desc + '\'' +
                ", pinned=" + pinned +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Hamster right = (Hamster) o;
        if (isPinned() && !right.isPinned()) return 1;
        else if (right.isPinned()) return -1;
        return 0;
    }
}
