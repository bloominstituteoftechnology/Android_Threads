package com.example.android_threads;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    static boolean backgroundCancel = false;
    TextView             textView;
    ProgressBar          progressBar;
    Spinner              spinner;
    Context              context;
    ArrayAdapter<String> spinnerAdapter;
    Button               buttonsave;
    int                  ciphersteps;

    static String cipher(String msg, int shift, backgroundCipher task) {
        StringBuilder temp = new StringBuilder();
        shift = shift % 26;
        if (shift == 0) return msg;
        for (int i = 0; i < msg.length(); i++) {
            task.doProgress(i * 100 / msg.length());
            if (backgroundCancel == true) return msg;
            char character = msg.charAt(i);
            char shiftedChar = (char) (msg.charAt(i) + shift);
            int endOfAlphaShift = msg.charAt(i) - (26 - shift);
            int startOfAlphaShift = msg.charAt(i) + (26 + shift);
            if (character >= 'A' && character <= 'Z') {
                if (shiftedChar > 'Z') { //case where shiftedChar goes out of bounds (positive)
                    temp.append((char) endOfAlphaShift);
                } else if (shiftedChar < 'A') { //case where shiftedChar goes out of bounds (negative)
                    temp.append((char) startOfAlphaShift);
                } else { //case where shiftedChar is in bounds
                    temp.append((char) (msg.charAt(i) + shift));
                }
            } else if (character >= 'a' && character <= 'z') {
                if (shiftedChar > 'z') { //case where shiftedChar goes out of bounds (positive)
                    temp.append((char) endOfAlphaShift);
                } else if (shiftedChar < 'a') { //case where shiftedChar goes out of bounds (negative)
                    temp.append((char) startOfAlphaShift);
                } else { //case where shiftedChar is in bounds
                    temp.append((char) (msg.charAt(i) + shift));
                }
            } else { //case where char isn't a letter, and shouldn't be shifted
                temp.append(msg.charAt(i));
            }
        }
        return temp.toString();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        spinner = findViewById(R.id.spinner);
        buttonsave = findViewById(R.id.button_save);
        String[] items;
        context = this;
        ciphersteps = 0;
        try {
            items = getAssets().list("");
            assert items != null;
            for (int i = items.length - 1; i >= 0; --i) { //removes any elements that aren't .txt files
                if (!items[i].contains(".txt")) {
                    String[] temp = new String[items.length - 1]; //transfers array to an identical array, minus the last element
                    System.arraycopy(items, 0, temp, 0, items.length - 1);
                    items = temp;
                }
            }
            spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //read in the file
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    //InputStream is given filename
                    //InputStreamReader is given inputStream
                    //BufferedReader is given inputStreamReader
                    BufferedReader bufferedReader = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    String fileName = (String) spinner.getItemAtPosition(position);
                    InputStream inputStream = context.getAssets().open(fileName);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    ciphersteps = 0;


                    while (bufferedReader.readLine() != null) {
                        stringBuilder.append(bufferedReader.readLine());
                    }
                    String output = stringBuilder.toString();
                    textView.setText(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = spinner.getSelectedItem().toString() + "_steps_" + ciphersteps;
                try {
                    File file = File.createTempFile(filename, ".txt", context.getCacheDir());
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(textView.getText().toString());
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button cancelButton = findViewById(R.id.button_cancel);
        Button shiftButton = findViewById(R.id.button_shift_cipher);
        final EditText editText = findViewById(R.id.edit_text);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ciphersteps = ciphersteps + Integer.valueOf(editText.getText().toString());
                new backgroundCipher().execute(textView.getText().toString(), editText.getText().toString());
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundCancel = true;
            }
        });
    }

    class backgroundCipher extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            backgroundCancel = false;
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = cipher(strings[0], Integer.parseInt(strings[1]), this);
            if (backgroundCancel == true) {
                cancel(true);
            }
            return result;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setMax(100);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            progressBar.setVisibility(View.GONE);
            Log.i("appLog", "asynctask canceled successfully");
        }

        public void doProgress(int value) {
            publishProgress(value);
        }
    }

}
