package com.example.marcorosas.multi_threaded_programming;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    public void createFile(View v) {

        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setProgress(0);

        final Handler progressBarHandler = new Handler();

        new Thread(new Runnable() {
            public void run() {
                String filename = "numbers.txt";
                String content = "";
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(content.getBytes());
                    outputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    for (int i = 1; i <= 10; i++) {
                        outputStream = openFileOutput(filename,  MODE_APPEND);
                        outputStream.write((i + "\n").getBytes());
                        outputStream.close();

                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                bar.setProgress(bar.getProgress() + 10);
                            }
                        });

                        Thread.sleep(250);
                    }
                }
                catch (Exception error) {}
            }
        }).start();

    } // end of createFile

    public void loadFile(View v) {

        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setProgress(0);

        final List<String> numberList = new ArrayList<>();

        final Handler progressBarHandler = new Handler();
        final Handler listViewHandler = new Handler();

        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numberList);
        final ListView listView = (ListView) findViewById(R.id.listView);

        Thread aThread = new Thread(new Runnable() {

            public void run() {
                try {
                    // open the file for reading

                    FileInputStream file = openFileInput("numbers.txt");
                    BufferedReader settingsReader = new BufferedReader(new InputStreamReader(file));

                    String line;
                    while ((line = settingsReader.readLine()) != null) {
                        // do something with the bar
                        numberList.add(line);

                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                bar.setProgress(bar.getProgress() + 10);
                            }
                        });

                        Thread.sleep(250);
                    }

                    settingsReader.close();
                    file.close();

                    listViewHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(itemsAdapter);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        aThread.start();

    }

    public void clearList(View v) {
        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setProgress(0);

        List<String> emptyList = new ArrayList<>();
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emptyList);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);
    }

} // end of class