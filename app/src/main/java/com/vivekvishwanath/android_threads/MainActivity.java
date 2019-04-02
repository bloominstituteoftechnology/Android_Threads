package com.vivekvishwanath.android_threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button shiftButton;
    TextView cipherTextView;
    EditText userInput;
    ProgressBar progressBar;
    int shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cipherTextView = findViewById(R.id.cipher_data_text);
        userInput = findViewById(R.id.shift_input);
        shiftButton = findViewById(R.id.shift_button);
        progressBar = findViewById(R.id.progress_bar);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cipher = cipherTextView.getText().toString();
                shift = Integer.parseInt(userInput.getText().toString());
                progressBar.setMax(cipher.length());
                new DecryptCypherAsync().execute(cipher);
            }
        });

    }

    class DecryptCypherAsync extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            cipherTextView.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... strings) {
            int shifts = 0;
            String newString = "";
            char c;
            for (int i = 0; i < strings[0].length(); i++) {
                c = strings[0].charAt(i);
                if (Character.isLetter(c)) {
                    c = (char) (strings[0].charAt(i) + shift);
                }
                if ((c < 'A' && c > 'A' + shift)
                || (c < 'a' && c > 'a' + shift )) {
                    c += 26;
                }
                newString += c;
                publishProgress(shifts);
                shifts++;
            }
           // System.out.println(newString);
            return newString;
        }
    }
}
