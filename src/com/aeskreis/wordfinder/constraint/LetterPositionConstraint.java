package com.aeskreis.wordfinder.constraint;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class LetterPositionConstraint extends Constraint
{
    private String mLetter;
    private int mPosition;

    public LetterPositionConstraint(String letter, int position)
    {
        mLetter = letter;
        mPosition = position;
    }

    public LetterPositionConstraint(Parcel p)
    {
        mLetter = p.readString();
        mPosition = p.readInt();
    }

    public String getLetter()
    {
        return mLetter;
    }

    public int getPosition()
    {
        return mPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLetter);
        dest.writeInt(mPosition);
    }

    @Override
    public String toString()
    {
        return "Contains letter " + mLetter + " at position " + (mPosition > 0 ? mPosition : "any");
    }

    @Override
    public boolean validate(String tiles)
    {
        return tiles.contains(mLetter);
    }

    @Override
    public boolean passes(String word) {
        if(mPosition == 0) {
            return word.contains(mLetter);
        }

        if(word.length() < mPosition) {
            return false;
        }

        return word.charAt(mPosition-1) == mLetter.charAt(0);
    }

    public static final Parcelable.Creator<LetterPositionConstraint> CREATOR
            = new Parcelable.Creator<LetterPositionConstraint>() {
        public LetterPositionConstraint createFromParcel(Parcel in) {
            return new LetterPositionConstraint(in);
        }

        public LetterPositionConstraint[] newArray(int size) {
            return new LetterPositionConstraint[size];
        }
    };
}
