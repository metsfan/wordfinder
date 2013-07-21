package com.aeskreis.wordfinder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 7:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Word implements Parcelable
{
    private String mWord;
    private int mValue;

    public Word(String word, int value)
    {
        mWord = word;
        mValue = value;
    }

    public Word(Parcel in)
    {
        mWord = in.readString();
        mValue = in.readInt();
    }

    public String getWord() {
        return mWord;
    }

    public int getValue() {
        return mValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mWord);
        dest.writeInt(mValue);
    }

    public static final Parcelable.Creator<Word> CREATOR
            = new Parcelable.Creator<Word>() {
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
