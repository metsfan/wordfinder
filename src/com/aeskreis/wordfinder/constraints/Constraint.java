package com.aeskreis.wordfinder.constraints;

import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Constraint implements Parcelable
{
    public abstract boolean validate(String tiles);
    public abstract boolean passes(String word);
    public abstract String toString();
}
