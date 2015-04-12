package com.karloleary.android.projectping;

// http://simpledeveloper.com/network-on-main-thread-error-solution/

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
        sendButton.setText("Sending"+dots);
        String msg = String.valueOf(messageField.getText());

        messageField.setText("");
        textOutput.setText("Waiting on response");
        Toast.makeText(getBaseContext(),"Please wait, connecting to server.", Toast.LENGTH_LONG).show();
        sendPing(msg);
    }

    private void sendPing(String msg) {
        try{
            HttpClient Client = new DefaultHttpClient();
            String URL = address+"?msg="+ URLEncoder.encode(msg,"UTF-8");
            textOutput.setText("URL = "+URL+"\n\n");

            String response = null;

            HttpGet httpget = new HttpGet(URL);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = Client.execute(httpget, responseHandler);

            textOutput.append("Response = "+response+"\n\n");
            //Toast.makeText(getBaseContext(),"Response: "+response,Toast.LENGTH_LONG).show();
        }
        catch(UnsupportedEncodingException ex) {
            //Toast.makeText(getBaseContext(),"Failure: "+ex.getMessage(),Toast.LENGTH_LONG).show();
            textOutput.append("Failure1: "+ex.getMessage()+"\n\n");
        }
        catch(Exception ex) {
            //Toast.makeText(getBaseContext(),"Failure: "+ex.getMessage(),Toast.LENGTH_LONG).show();
            //textOutput.setText("Failure: "+ex.getMessage());
            textOutput.append("Failure2: "+ex.toString()+"\n\n");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
