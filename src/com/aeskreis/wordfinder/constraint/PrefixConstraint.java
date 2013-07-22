package com.aeskreis.wordfinder.constraint;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrefixConstraint extends Constraint {

    private String mPrefixString;

    public PrefixConstraint(String prefixString)
    {
        mPrefixString = prefixString;
    }

    public PrefixConstraint(Parcel p)
    {
        mPrefixString = p.readString();
    }

    @Override
    public boolean validate(String tiles) {
        for(int i = 0; i < mPrefixString.length(); i++) {
            if(tiles.indexOf(mPrefixString.charAt(i)) < 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean passes(String word) {
        if(word.length() < mPrefixString.length()) {
            return false;
        }

        return word.startsWith(mPrefixString);
    }

    @Override
    public String toString() {
        return "Starts with " + mPrefixString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPrefixString);
    }

    public static final Parcelable.Creator<PrefixConstraint> CREATOR
            = new Parcelable.Creator<PrefixConstraint>() {
        public PrefixConstraint createFromParcel(Parcel in) {
            return new PrefixConstraint(in);
        }

        public PrefixConstraint[] newArray(int size) {
            return new PrefixConstraint[size];
        }
    };
}
