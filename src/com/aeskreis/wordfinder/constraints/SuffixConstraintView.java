package com.aeskreis.wordfinder.constraints;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.aeskreis.wordfinder.R;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuffixConstraintView extends ConstraintView {
    EditText mSuffix;

    public SuffixConstraintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate()
    {
        super.onFinishInflate();

        mSuffix = (EditText)this.findViewById(R.id.suffix);
    }

    public String getSuffixString()
    {
        return mSuffix.getText().toString().toLowerCase();
    }

    @Override
    public Constraint getConstraint() {
        return new SuffixConstraint(this.getSuffixString());
    }
}
