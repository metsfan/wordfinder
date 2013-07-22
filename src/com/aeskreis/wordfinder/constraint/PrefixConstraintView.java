package com.aeskreis.wordfinder.constraint;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.aeskreis.wordfinder.R;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrefixConstraintView extends ConstraintView
{
    EditText mPrefix;

    public PrefixConstraintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate()
    {
        super.onFinishInflate();

        mPrefix = (EditText)this.findViewById(R.id.prefix);
    }

    public String getPrefixString()
    {
        return mPrefix.getText().toString().toLowerCase();
    }

    @Override
    public Constraint getConstraint() {
        return new PrefixConstraint(this.getPrefixString());
    }
}
