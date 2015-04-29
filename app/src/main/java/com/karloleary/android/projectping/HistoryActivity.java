package com.karloleary.android.projectping;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class HistoryActivity extends ActionBarActivity {

    private TextView textOutput = null;
    private DataStore dataStore = null;

    @Override
    protected void onPostResume() {
        super.onPostResume();

        new DisplayMessagesTask().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textOutput = (TextView) findViewById(R.id.textOutput);
        dataStore = DataStore.getInstance();

        new DisplayMessagesTask().execute();
    }

    private class DisplayMessagesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... m) {

            String[] arr = dataStore.getMessages();

            String output = "Length = "+arr.length+"\n\n";
            for (int i=0; i<arr.length; i++)
                output += i+". "+(String)arr[i]+"\n";

            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            textOutput.setText(s);
        }
    }
}
