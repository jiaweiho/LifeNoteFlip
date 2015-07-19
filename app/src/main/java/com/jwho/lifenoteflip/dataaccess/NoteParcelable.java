package com.jwho.lifenoteflip.dataaccess;

import android.os.Parcel;
import android.os.Parcelable;

import com.jwho.lifenoteflip.handlers.ResourceParcelable;

public class NoteParcelable implements Parcelable {
    private String guid;
    private String title;
    private String content;
    private ResourceParcelable[] resUrls;

    public NoteParcelable(String guid, String title, String content) {
        this.guid = guid;
        this.title = title;
        this.content = content;
    }

    public NoteParcelable(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.guid = data[0];
        this.title = data[1];
        this.content = data[2];
        resUrls = (ResourceParcelable[]) in.readParcelableArray(ResourceParcelable.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeParcelableArray(resUrls, 0);
        dest.writeStringArray(new String[]{
                this.guid, this.title, this.content
        });
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ResourceParcelable[] getResUrls() {
        return resUrls;
    }

    public void setResUrls(ResourceParcelable[] resUrls) {
        this.resUrls = resUrls;
    }

    public static final Parcelable.Creator CREATOR =
        new Parcelable.Creator<NoteParcelable>() {
            @Override
            public NoteParcelable createFromParcel(Parcel in) {
                return new NoteParcelable(in);
            }

            @Override
            public NoteParcelable[] newArray(int size) {
                return new NoteParcelable[size];
            }
        };
}
