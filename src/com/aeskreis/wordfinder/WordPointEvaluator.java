package com.aeskreis.wordfinder;

import com.aeskreis.wordfinder.constraint.Constraint;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 7:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordPointEvaluator
{
    HashMap<Character, Integer> mPointsMap;

    public WordPointEvaluator(HashMap<Character, Integer> pointsMap)
    {
        mPointsMap = pointsMap;
    }

    public int evaluateWord(String word, ArrayList<Constraint> constraints)
    {
        int total = 0;

        for(int i = 0; i < word.length(); i++) {
            total += mPointsMap.get(word.charAt(i));
        }

        return total;
    }
}
