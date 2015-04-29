package com.karloleary.android.projectping;

// http://simpledeveloper.com/network-on-main-thread-error-solution/
// http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    DataStore dataStore = null;
    Button sendButton = null;
    Button historyButton = null;
    EditText messageField = null;
    TextView textOutput = null;
    String address = "http://karlolearyapps.com/ping.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setOnClickListener(this);

        messageField = (EditText) findViewById(R.id.messageText);
        textOutput = (TextView) findViewById(R.id.textOutput);
        textOutput.setText("Blank");

        dataStore = DataStore.getInstance();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendButton:
                String msg = String.valueOf(messageField.getText());

                dataStore.addMessage(msg);
                sendButton.setText("Sending [" + dataStore.getSize() + "]");
                messageField.setText("");
                textOutput.setText("Waiting on response\n\n");

                Toast.makeText(getBaseContext(), "Please wait, connecting to server.", Toast.LENGTH_SHORT).show();

                new SendMessageTask().execute(msg);
                break;

            case R.id.historyButton:
                Intent h = new Intent(this, HistoryActivity.class);
                startActivity(h);
                break;
        }
    }



    private class SendMessageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... m) {
            String output = "";

            try {
                HttpClient Client = new DefaultHttpClient();
                String URL = address+"?msg="+ URLEncoder.encode(m[0],"UTF-8");
                output = "URL = "+URL+"\n\n";

                HttpGet httpget = new HttpGet(URL);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                output += "Response = "+Client.execute(httpget, responseHandler)+"\n\n";
            }
            catch(UnsupportedEncodingException ex) {
                output += "Failure1: "+ex.getMessage()+"\n\n";
            }
            catch(Exception ex) {
                output += "Failure2: "+ex.toString()+"\n\n";
            }

            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            textOutput.append(s+"All Done!!!.");
        }
    }
}


