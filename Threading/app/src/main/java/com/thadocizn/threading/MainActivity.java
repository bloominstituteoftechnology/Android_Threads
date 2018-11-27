package com.thadocizn.threading;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView msg;
    EditText shift;
    ProgressBar progressBar;
    private String strMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msg = findViewById(R.id.textViewMsg);
        shift = findViewById(R.id.editTextShift);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strMsg = msg.getText().toString();
                progressBar.setMax(strMsg.length());

                new Task().execute();

            }
        });

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
            progressBar = findViewById(R.id.progressBar);
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
                    counter++;
                    newText += unicode;
                    publishProgress(counter);


                }
                return newText;
            }
            return null;
        }
    }
}
