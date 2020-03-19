package com.rohankadkol.demointernet.asyncTasks;

import android.os.AsyncTask;
import android.widget.TextView;

import com.rohankadkol.demointernet.pojo.Task;
import com.rohankadkol.demointernet.utils.InternetUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomAsyncTask extends AsyncTask<String, Void, List<Task>> {
    private TextView tvTasks;

    public CustomAsyncTask(TextView tvTasks) {
        this.tvTasks = tvTasks;
    }

    @Override
    public List<Task> doInBackground(String... strings) {
        List<Task> tasks = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            String jsonResponse = InternetUtils.makeHttpRequest(url);
            tasks = InternetUtils.extractTasksFromJson(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        String output = "";
        int counter = 0;
        for (Task task : tasks) {
            counter++;
        }
        output = counter + " tasks";
        tvTasks.setText(output);
    }
}
