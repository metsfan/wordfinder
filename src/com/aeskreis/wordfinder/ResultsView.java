package com.aeskreis.wordfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.*;
import com.aeskreis.wordfinder.constraints.Constraint;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultsView extends LinearLayout
{
    public ResultsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void loadResults()
    {
        Activity context = (Activity)this.getContext();
        Intent intent = context.getIntent();

        String tiles = intent.getStringExtra("tiles");
        ArrayList<Word> words = intent.getParcelableArrayListExtra("words");

        TextView tilesView = (TextView)this.findViewById(R.id.letters_values);

        String tilesText = "";
        for(int i = 0; i < tiles.length(); i++) {
            if(i != 0) {
                tilesText += ", ";
            }
            tilesText += tiles.charAt(i);
        }
        tilesView.setText(tilesText);

        LinearLayout constraints = (LinearLayout)this.findViewById(R.id.constraints_values);

        ArrayList<Constraint> constraintsList = intent.getParcelableArrayListExtra("constraints");
        if(constraintsList == null) {
            TextView constraintLabel = new TextView(this.getContext());
            constraintLabel.setText("None");
            constraints.addView(constraintLabel);
        } else {
            for(Constraint constraint : constraintsList) {
                TextView constraintLabel = new TextView(this.getContext());
                constraintLabel.setText(constraint.toString());
                constraints.addView(constraintLabel);
            }
        }


        TableLayout resultsTable = (TableLayout)this.findViewById(R.id.results_table);
        for(Word word : words) {
            TableRow row = new TableRow(this.getContext());

            TextView label = new TextView(this.getContext());
            label.setText(word.getWord());
            label.setMinWidth(200);
            row.addView(label);

            TextView value = new TextView(this.getContext());
            value.setText(word.getValue() + " points");
            row.addView(value);

            resultsTable.addView(row);
        }
    }
}
