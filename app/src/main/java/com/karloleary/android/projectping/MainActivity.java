package com.karloleary.android.projectping;

// http://simpledeveloper.com/network-on-main-thread-error-solution/
// http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    Button sendButton = null;
    EditText messageField = null;
    TextView textOutput = null;
    String address = "http://karlolearyapps.com/ping.php";
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageField = (EditText) findViewById(R.id.messageText);
        textOutput = (TextView) findViewById(R.id.textOutput);
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);

        textOutput.setText("Blank");
    }

    public void onClick(View v) {
        count++;
        String dots = "";
        for (int i=0; i<count; i++)
            dots += ".";
        sendButton.setText("Sending "+dots);

        String msg = String.valueOf(messageField.getText());

        messageField.setText("");
        textOutput.setText("Waiting on response\n\n");
        Toast.makeText(getBaseContext(),"Please wait, connecting to server.", Toast.LENGTH_LONG).show();

        new SendMessageTask().execute(msg);
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


