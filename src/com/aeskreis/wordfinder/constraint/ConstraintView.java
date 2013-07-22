package com.aeskreis.wordfinder.constraint;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import com.aeskreis.wordfinder.R;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConstraintView extends GridLayout
{
    Button closeButton;

    public ConstraintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate()
    {
        final ConstraintView constraintView = this;

        closeButton = (Button)this.findViewById(R.id.constraint_close);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup)constraintView.getParent()).removeView(constraintView);
            }
        });
    }

    public abstract Constraint getConstraint();
}
