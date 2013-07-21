package com.aeskreis.wordfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.aeskreis.wordfinder.constraints.Constraint;
import com.aeskreis.wordfinder.constraints.ConstraintView;
import com.aeskreis.wordfinder.constraints.LetterPositionConstraintView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchView extends LinearLayout
{
    static final int LETTER_POSITION_CONSTRAINT = 0;
    static final int PREFIX_CONSTRAINT = 1;
    static final int SUFFIX_CONSTRAINT = 2;

    EditText mTiles;
    EditText mBoardLetters;
    LinearLayout mConstraints;
    WordsDictionary mDictionary;
    Spinner mMinLength;
    Spinner mMaxLength;
    WordPointEvaluator mWordEvaluator;

    public SearchView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate()
    {
        super.onFinishInflate();

        Button searchButton = (Button)this.findViewById(R.id.search);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                executeSearch();
            }
        });

        Button addConstraintButton = (Button)this.findViewById(R.id.add_constraint);
        addConstraintButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstraint();
            }
        });

        mTiles = (EditText)this.findViewById(R.id.tiles);
        mBoardLetters = (EditText)this.findViewById(R.id.board_letters);
        mConstraints = (LinearLayout)this.findViewById(R.id.constraints);
        mMinLength = (Spinner)this.findViewById(R.id.min_length);
        mMaxLength = (Spinner)this.findViewById(R.id.max_length);

        ArrayAdapter<CharSequence> lengthChoices = ArrayAdapter.createFromResource(getContext(),
                R.array.word_length_choices, android.R.layout.simple_spinner_item);
        lengthChoices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mMinLength.setAdapter(lengthChoices);
        mMinLength.setSelection(0);

        mMaxLength.setAdapter(lengthChoices);
        mMaxLength.setSelection(lengthChoices.getCount()-1);
    }

    public void loadResources()
    {
        // Load Dictionary Entries
        ArrayList<String> words = new ArrayList<String>();
        this.readDictionaryFile(getContext(), "enable1.txt", words);
        this.readDictionaryFile(getContext(), "supplement.txt", words);

        mDictionary = new WordsDictionary(words);

        // Load Point Values
        String[] pointValues = this.getContext().getResources().getStringArray(R.array.words_letter_values);
        HashMap<Character, Integer> pointValuesMap = new HashMap<Character, Integer>();
        for(int i = 0; i < pointValues.length; i++) {
            String[] entry = pointValues[i].split("-", 2);

            char character = entry[0].charAt(0);
            int value = Integer.parseInt(entry[1]);
            pointValuesMap.put(character, value);
        }
        mWordEvaluator = new WordPointEvaluator(pointValuesMap);
    }

    private void readDictionaryFile(Context context, String filename, ArrayList<String> wordsList)
    {
        try {
            InputStream file = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            String line;
            while((line = reader.readLine()) != null) {
                wordsList.add(line);
            }
        } catch(Exception e) {
            Log.d("File not found", "Could not open dictionary file");
        }
    }

    private void executeSearch()
    {
        String tiles = mTiles.getText().toString().toLowerCase();
        tiles += mBoardLetters.getText().toString().toLowerCase();
        ArrayList<String> words = null;

        ArrayList<Constraint> constraints = null;
        if(mConstraints.getChildCount() > 0) {
            constraints = new ArrayList<Constraint>();
            for(int i = 0; i < mConstraints.getChildCount(); i++) {
                ConstraintView constraintView = (ConstraintView)mConstraints.getChildAt(i);
                constraints.add(constraintView.getConstraint());
            }
        }

        String error = this.validate(tiles, constraints);

        if(error == null) {
            int minLength = Integer.parseInt(mMinLength.getSelectedItem().toString());
            int maxLength = Integer.parseInt(mMaxLength.getSelectedItem().toString());

            words = mDictionary.findWordsWithConstraints(tiles, minLength, maxLength, constraints);

            if(words.size() > 0) {
                this.showResults(words, tiles, constraints);
            } else {
                error = "No results found.  Try broadening your search.";
            }
        }

        if(error != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            AlertDialog dialog = builder.create();
            dialog.setTitle("Error");
            dialog.setMessage(error);
            dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    private String validate(String tiles, ArrayList<Constraint> constraints)
    {
        if(tiles.length() < 7) {
            return "You must enter all 7 tiles";
        }

        if(constraints != null) {
            for(Constraint constraint : constraints) {
                if(!constraint.validate(tiles)) {
                    return "Your constraints may not contain letters not in your tiles or on the board.";
                }
            }
        }

        return null;
    }

    private void showResults(ArrayList<String> words, String tiles, ArrayList<Constraint> constraints)
    {
        ArrayList<Word> sortedWordsList = new ArrayList<Word>();
        for(String word : words) {
            sortedWordsList.add(new Word(word, mWordEvaluator.evaluateWord(word, constraints)));
        }

        Collections.sort(sortedWordsList, new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return rhs.getValue() - lhs.getValue();
            }
        });

        Activity context = (Activity)this.getContext();
        Intent intent = new Intent(context, ResultsActivity.class);

        intent.putParcelableArrayListExtra("words", sortedWordsList);
        intent.putExtra("tiles", tiles);
        intent.putParcelableArrayListExtra("constraints", constraints);

        context.startActivity(intent);
    }

    private void addConstraint()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Select Constraint Type");
        builder.setItems(R.array.constraint_types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int resource = R.layout.letter_position_constraint;

                switch(which)
                {
                    case LETTER_POSITION_CONSTRAINT:
                        resource = R.layout.letter_position_constraint;
                        break;

                    case PREFIX_CONSTRAINT:
                        resource = R.layout.prefix_constraint;
                        break;

                    case SUFFIX_CONSTRAINT:
                        resource = R.layout.suffix_constraint;
                        break;
                }

                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View constraint = inflater.inflate(resource, null);
                mConstraints.addView(constraint);

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
