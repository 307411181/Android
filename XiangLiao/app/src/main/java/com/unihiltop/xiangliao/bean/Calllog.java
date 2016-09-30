package com.unihiltop.xiangliao.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Calllog implements Parcelable {
    private long    id;
    private int     photoId;
    private String  name;
    private String  number;
    private int     type;
    private long    date;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public Calllog() {
    }

    public Calllog(int id, int photoId, String name, String number, int type,
                   long date, boolean isChecked) {
        super();
        this.id = id;
        this.photoId = photoId;
        this.name = name;
        this.number = number;
        this.type = type;
        this.date = date;
        this.isChecked = isChecked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(photoId);
        dest.writeString(name);
        dest.writeString(number);
        dest.writeInt(type);
        dest.writeLong(date);
    }

    public static final Creator<Calllog> CREATOR = new Creator<Calllog>() {

        @Override
        public Calllog createFromParcel(Parcel source) {
            Calllog calllog = new Calllog();
            calllog.id = source.readLong();
            calllog.photoId = source.readInt();
            calllog.name = source.readString();
            calllog.number = source.readString();
            calllog.type = source.readInt();
            calllog.date = source.readLong();
            return calllog;
        }

        @Override
        public Calllog[] newArray(int size) {
            return new Calllog[size];
        }

    };

    @Override
    public boolean equals(Object o) {
        Calllog calllog = (Calllog) o;
        if (!TextUtils.isEmpty(number) &&
                number.equals(calllog.getNumber()) &&
                date == calllog.getDate()) {

            return true;
        } else {
            return false;
        }

    }
}
