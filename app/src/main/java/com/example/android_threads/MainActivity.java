package com.example.android_threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);

        Button shiftButton = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.edit_text);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 //               textView.setText(cipher(textView.getText().toString(), Integer.parseInt(editText.getText().toString())));

                new backgroundCipher().execute(textView.getText().toString(),editText.getText().toString());
            }
        });
    }

    String cipher(String msg, int shift, backgroundCipher task) {
    String temp = "";
    shift = shift % 26;
    int length = msg.length();
            for (int i = 0; i < length; i++) {
                task.doProgress(i);
        char character = msg.charAt(i);
        char shiftedChar = (char) (msg.charAt(i) + shift);
        if (character >= 'A' && character <= 'Z') {
            if (shiftedChar > 'Z') { //case where shiftedChar goes out of bounds (positive)
                temp += (char) (msg.charAt(i) - (26 - shift));
            } else if (shiftedChar < 'A') { //case where shiftedChar goes out of bounds (negative)
                temp += (char) (msg.charAt(i) + (26 + shift));
            } else { //case where shiftedChar is in bounds
                temp += (char) (msg.charAt(i) + shift);
            }
        } else if (character >= 'a' && character <= 'z') {
            if (shiftedChar > 'z') { //case where shiftedChar goes out of bounds (positive)
                temp += (char) (msg.charAt(i) - (26 - shift));
            } else if (shiftedChar < 'a') { //case where shiftedChar goes out of bounds (negative)
                temp += (char) (msg.charAt(i) + (26 + shift));
            } else { //case where shiftedChar is in bounds
                temp += (char) (msg.charAt(i) + shift);
            }
        } else { //case where char isn't a letter, and shouldn't be shifted
            temp += (char) (msg.charAt(i));
        }
    }
            return temp;

    }


    class backgroundCipher extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            return cipher(strings[0],Integer.parseInt(strings[1]), this);

        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setMax(31467);
            progressBar.setProgress(values[0]);
            Log.i("tracer", values[0].toString());
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        public void doProgress(int value) {
            publishProgress(value);
        }
    }

}
