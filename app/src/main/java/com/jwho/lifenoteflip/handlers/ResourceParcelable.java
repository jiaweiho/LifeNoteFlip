package com.jwho.lifenoteflip.handlers;

import android.os.Parcel;
import android.os.Parcelable;

public class ResourceParcelable implements Parcelable {
    private String mime;
    private short width;
    private short height;

    public ResourceParcelable(String mime, short width, short height) {
        this.mime = mime;
        this.width = width;
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {this.mime});
        parcel.writeIntArray(new int[]{this.width, this.height});
    }


}
