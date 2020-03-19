package com.rohankadkol.demointernet;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rohankadkol.demointernet.asyncTasks.CustomAsyncTask;

public class MainActivity extends AppCompatActivity {
    TextView tvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTasks = findViewById(R.id.tv_tasks);

        CustomAsyncTask customAsyncTask = new CustomAsyncTask(tvTasks);
        customAsyncTask.execute("https://jsonplaceholder.typicode.com/todos");
    }
}
