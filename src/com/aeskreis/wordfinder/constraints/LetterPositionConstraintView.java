package com.aeskreis.wordfinder.constraints;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import com.aeskreis.wordfinder.R;
import com.aeskreis.wordfinder.constraints.Constraint;
import com.aeskreis.wordfinder.constraints.LetterPositionConstraint;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class LetterPositionConstraintView extends ConstraintView
{
    EditText letter;
    Spinner position;

    public LetterPositionConstraintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate()
    {
        super.onFinishInflate();

        letter = (EditText)this.findViewById(R.id.letter);
        position = (Spinner)this.findViewById(R.id.position);

        ArrayAdapter<CharSequence> positionChoices = ArrayAdapter.createFromResource(getContext(),
                R.array.letter_position_choices, android.R.layout.simple_spinner_item);
        positionChoices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(positionChoices);
    }

    public String getLetter()
    {
        return letter.getText().toString().toLowerCase();
    }

    public int getPosition()
    {
        String value = position.getSelectedItem().toString().toLowerCase();
        if(value.equals("any")) {
            return 0;
        }

        return Integer.parseInt(value);
    }

    @Override
    public Constraint getConstraint()
    {
        return new LetterPositionConstraint(this.getLetter(), this.getPosition());
    }
}
