package com.aeskreis.wordfinder.constraint;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/22/13
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContainsConstraint extends Constraint
{
    String mContainsString;

    public ContainsConstraint(String containsString)
    {
        mContainsString = containsString;
    }

    public ContainsConstraint(Parcel in)
    {
        mContainsString = in.readString();
    }


    @Override
    public boolean validate(String tiles) {
        for(int i = 0; i < mContainsString.length(); i++) {
            if(tiles.indexOf(mContainsString.charAt(i)) < 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean passes(String word) {
        return word.contains(mContainsString);
    }

    @Override
    public String toString() {
        return "Contains string " + mContainsString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mContainsString);
    }

    public static final Parcelable.Creator<ContainsConstraint> CREATOR
            = new Parcelable.Creator<ContainsConstraint>() {
        public ContainsConstraint createFromParcel(Parcel in) {
            return new ContainsConstraint(in);
        }

        public ContainsConstraint[] newArray(int size) {
            return new ContainsConstraint[size];
        }
    };
}
