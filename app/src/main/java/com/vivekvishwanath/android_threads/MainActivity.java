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
        shift = Integer.parseInt(userInput.getText().toString());
        progressBar = findViewById(R.id.progress_bar);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cipher = cipherTextView.getText().toString();
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
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... strings) {
            String newString = "";
            char c;
            for (int i = 0; i < strings[0].length(); i++) {
                c = strings[0].charAt(i);
                if (Character.isLetter(c)) {
                    c = (char) (strings[0].charAt(i) + shift);
                }
                    /*if (Character.isLowerCase((cipher.charAt(i))) && c > 'z'
                    || Character.isUpperCase(cipher.charAt(i)) && c > 'Z') {
                        c = (char) (cipher.charAt(i) - (26 - shift));
                    } */

                if (strings[0].charAt(i) > 'Z' - shift || strings[0].charAt(i) > 'z' - shift) {
                    c = (char) (strings[0].charAt(i) + shift);
                }
                newString += c;
            }
           // System.out.println(newString);
            return newString;
        }
    }
}
