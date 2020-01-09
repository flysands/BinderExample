// This file is intentionally left blank as placeholder for parcel declaration.
package com.flysands.binderexample;

import android.os.Parcel;
import android.os.Parcelable;

public class MyMessage implements Parcelable {

    public MyMessage() {
    }

    public String text;

    protected MyMessage(Parcel in) {
        text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
    }

    public static final Creator<MyMessage> CREATOR = new Creator<MyMessage>() {
        @Override
        public MyMessage createFromParcel(Parcel in) {
            return new MyMessage(in);
        }

        @Override
        public MyMessage[] newArray(int size) {
            return new MyMessage[size];
        }
    };

}
