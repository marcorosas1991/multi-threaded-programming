package com.example.marcorosas.multi_threaded_programming;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.Toast;

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

        String filename = "numbers.txt";
        String content = "";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();

            Toast.makeText(this, "Saved " + getFilesDir(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (int i = 1; i <= 10; i++) {
                outputStream = openFileOutput(filename,  MODE_APPEND);
                outputStream.write((i + "\n").getBytes());
                outputStream.close();

                Thread.sleep(250);
            }
        }
        catch (Exception error) {}
    } // end of createFile

    public void loadFile(View v) {

        List<String> numberList = new ArrayList<>();

        try {
            // open the file for reading

            FileInputStream file = openFileInput("numbers.txt");
            BufferedReader settingsReader = new BufferedReader(new InputStreamReader(file));

            String line;
            while ((line = settingsReader.readLine()) != null) {
                numberList.add(line);
                Thread.sleep(250);
            }
            settingsReader.close();
            file.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numberList);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);
    }

} // end of class

class WriteNumbers implements Runnable {

    String filename;
    FileOutputStream outputStream;

    public WriteNumbers(String filename) {
        this.filename = filename;
        run();
    }

    public void run() {

    }
}