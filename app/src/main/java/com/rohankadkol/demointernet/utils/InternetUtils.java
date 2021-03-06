package com.rohankadkol.demointernet.utils;

import com.rohankadkol.demointernet.pojo.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class InternetUtils {
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Task} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    public static List<Task> extractTasksFromJson(String jsonResponse) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            JSONArray rootArray = new JSONArray(jsonResponse);
            for(int i=0; i<rootArray.length(); i++) {
                JSONObject taskJson = rootArray.getJSONObject(i);
                int userId = taskJson.getInt("userId");
                int id = taskJson.getInt("id");
                String title = taskJson.getString("title");
                boolean completed = taskJson.getBoolean("completed");
                Task task = new Task(userId, id, title, completed);
                tasks.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
