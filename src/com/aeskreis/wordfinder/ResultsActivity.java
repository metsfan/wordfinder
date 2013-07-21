package com.aeskreis.wordfinder;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: Adam
 * Date: 7/20/13
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultsActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setContentView(R.layout.results);

        ResultsView resultsView = (ResultsView)this.findViewById(R.id.results);
        resultsView.loadResults();
    }
}
