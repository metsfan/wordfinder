package com.aeskreis.wordfinder;

import com.aeskreis.wordfinder.constraint.Constraint;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordsDictionary
{
    ArrayList<String> mWords;

    public WordsDictionary(ArrayList<String> words)
    {
        mWords = words;
    }

    public ArrayList<String> findWords(String letters,
                                       int minLength,
                                       int maxLength)
    {
        return this.findWordsWithConstraints(letters, minLength, maxLength, null);
    }

    public ArrayList<String> findWordsWithConstraints(String letters,
                                                      int minLength,
                                                      int maxLength,
                                                      ArrayList<Constraint> constraints)
    {
        ArrayList<String> outWords = new ArrayList<String>();

        for(String word : mWords) {
            if (word.length() < minLength ||
                    word.length() > maxLength) {
                continue;
            }

            boolean passedConstraints = true;
            if(constraints != null) {
                for(Constraint constraint : constraints) {
                    if(!constraint.passes(word)) {
                        passedConstraints = false;
                        break;
                    }
                }
            }

            if(passedConstraints) {
                if(wordValidForLetters(letters, word)) {
                    outWords.add(word);
                }
            }
        }

        return outWords;
    }

    private boolean wordValidForLetters(String letters, String word)
    {
        int numWildcards = 0;
        ArrayList<Character> lettersList = new ArrayList<Character>();
        for(int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            lettersList.add(c);

            if(c == '?') {
                numWildcards++;
            }
        }

        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if(!this.listContainsCharacter(lettersList, c)) {
                // If we don't have the letter, check if we have any wildcards
                if(numWildcards > 0) {
                    // If we have a wildcard, use it here.
                    numWildcards--;
                } else {
                    // Otherwise, we're done
                    return false;
                }
            }

            // Otherwise, remove the letter from the list, and continue
            for(int k = 0; k < lettersList.size(); k++) {
                if(lettersList.get(k) == c) {
                    lettersList.remove(k);
                    break;
                }
            }
        }

        return true;
    }

    private boolean listContainsCharacter(ArrayList<Character> list, char c)
    {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).charValue() == c) {
                return true;
            }
        }

        return false;
    }
}
