package com.karloleary.android.projectping;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by karl on 29/04/15.
 */
public class DataStore {

    private static DataStore ourInstance = new DataStore();
    private ArrayList<String> messages = null;

    public static DataStore getInstance() {
        return ourInstance;
    }

    private DataStore() {
        messages = new ArrayList<String>();
    }

    public void addMessage(String s) {
        messages.add(s);
    }

    public String[] getMessages() {
        Object[] arr = messages.toArray();
        String[] res = new String[arr.length];
        for (int i=0; i<arr.length; i++)
            res[i] = (String)arr[i];
        return res;
    }

    public int getSize() {
        return messages.size();
    }
}
