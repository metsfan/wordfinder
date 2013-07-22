package com.aeskreis.wordfinder.constraint;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.aeskreis.wordfinder.R;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/22/13
 * Time: 12:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContainsConstraintView extends ConstraintView
{
    EditText mContains;

    public ContainsConstraintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate()
    {
        super.onFinishInflate();

        mContains = (EditText)this.findViewById(R.id.contains);
    }

    public String getContainsString()
    {
        return mContains.getText().toString().toLowerCase();
    }


    @Override
    public Constraint getConstraint() {
        return new ContainsConstraint(this.getContainsString());
    }
}
