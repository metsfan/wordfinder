package com.aeskreis.wordfinder.constraint;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuffixConstraint extends Constraint
{
    private String mSuffixString;

    public SuffixConstraint(String SuffixString)
    {
        mSuffixString = SuffixString;
    }

    public SuffixConstraint(Parcel p)
    {
        mSuffixString = p.readString();
    }

    @Override
    public boolean validate(String tiles) {
        for(int i = 0; i < mSuffixString.length(); i++) {
            if(tiles.indexOf(mSuffixString.charAt(i)) < 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean passes(String word) {
        if(word.length() < mSuffixString.length()) {
            return false;
        }

        return word.endsWith(mSuffixString);
    }

    @Override
    public String toString() {
        return "Ends with " + mSuffixString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSuffixString);
    }

    public static final Parcelable.Creator<SuffixConstraint> CREATOR
            = new Parcelable.Creator<SuffixConstraint>() {
        public SuffixConstraint createFromParcel(Parcel in) {
            return new SuffixConstraint(in);
        }

        public SuffixConstraint[] newArray(int size) {
            return new SuffixConstraint[size];
        }
    };
}
