package com.thadocizn.threading;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    TextView msg;
    Spinner spinner;
    EditText shift;
    ProgressBar progressBar;
    AsyncTask task;
    private String strMsg;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        msg         = findViewById(R.id.textViewMsg);
        shift       = findViewById(R.id.editTextShift);
        progressBar = findViewById(R.id.progressBar);
        spinner     = findViewById(R.id.spinner);
        ArrayList<String> textFiles = new ArrayList<>();


        try {
            String[] strFiles = getAssets().list("");
            for (String strFile:strFiles) {
                if ( strFile.endsWith(".txt")){
                    textFiles.add(strFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, textFiles);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strMsg = msg.getText().toString();
                progressBar.setMax(strMsg.length());

                task = new Task().execute();

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (task != null){
            task.cancel(true);
            Log.i(TAG, "onStop" + " " + task.isCancelled());
        }
    }

    public class Task extends AsyncTask<Void, Integer, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            msg.setText(s);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i(TAG, "onCanceled" + " " + task.isCancelled());

        }

        @Override
        protected String doInBackground(Void... voids) {
            int shifted = Integer.parseInt(shift.getText().toString());
            int counter = 0;

            if (strMsg != null) {
                String newText = "";
                for (int i = 0; i < strMsg.length(); ++i) {
                    char unicode = strMsg.charAt(i);
                    if ((unicode >= 'A' && unicode <= 'Z') || ((unicode >= 'a' && unicode <= 'z'))) {
                        for (int n = 0; n < shifted; ++n) {
                            ++unicode;

                            if (unicode > 'Z' && unicode < 'a') {
                                unicode = 'A';
                            }
                            if (unicode > 'z') {
                                unicode = 'a';
                            }
                        }
                    }
                    ++counter;
                    newText += unicode;
                    publishProgress(counter);
                    if (isCancelled()){
                        progressBar.setVisibility(View.GONE);
                        msg.setText(strMsg);
                        shift.setText(0);
                    }

                }
                return newText;
            }
            return null;
        }
    }
}
