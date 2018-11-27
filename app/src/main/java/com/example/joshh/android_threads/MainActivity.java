package com.example.joshh.android_threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView cipherTextView;
    private ProgressBar progressBar;
    private EditText shiftTextValue;
    private AsyncTask cipherTextTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        cipherTextView = findViewById(R.id.cipher_text);
        shiftTextValue = findViewById(R.id.shift_text_value);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.decode_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cipherText = cipherTextView.getText().toString();
                String shiftValue = shiftTextValue.getText().toString();
                cipherTextTask = (new CipherTextTask()).execute(cipherText, shiftValue);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cipherTextTask != null){
            cipherTextTask.cancel(true);
        }
    }

    public class CipherTextTask extends AsyncTask<String, Integer, String>{
        int lettersShifted = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(cipherTextView.getText().toString().length());
        }


        @Override
        protected String doInBackground( String... strings) {
            /*int shiftAmount = Integer.parseInt(shiftTextValue.getText().toString());
            if (strings[0] != null) {
                StringBuilder newString = new StringBuilder();
                for (int i = 0; i < strings[0].length(); i++) {
                    if (shiftAmount >= 0) {
                        if (Character.isLowerCase(strings[0].charAt(i))) {
                            newString.append((char) ('a' + (26 + strings[0].charAt(i) - 'a' - shiftAmount % 26) % 26));
                        } else if (Character.isUpperCase(strings[0].charAt(i))) {
                            newString.append((char) ('A' + (26 + strings[0].charAt(i) - 'A' - shiftAmount % 26) % 26));
                        } else {
                            newString.append(strings[0].charAt(i));
                        }
                    } else {
                        if (Character.isLowerCase(strings[0].charAt(i))) {
                            newString.append((char) ('a' + ((strings[0].charAt(i) - 'a' - shiftAmount) % 26)));
                        } else if (Character.isUpperCase(strings[0].charAt(i))) {
                            newString.append((char) ('A' + ((strings[0].charAt(i) - 'A' - shiftAmount) % 26)));
                        } else {
                            newString.append(strings[0].charAt(i));
                        }
                    }
                    if(lettersShifted % 40 == 0){
                        publishProgress(lettersShifted);
                        if(isCancelled()){
                            progressBar.setVisibility(View.GONE);
                            cipherTextView.setText(newString);
                            return newString.toString();
                        }
                    }
                    lettersShifted++;
                }
                return newString.toString();
            }
            return "";*/
            if (strings[0] != null) {
                String newText  = "";
                int shiftAmount = Integer.parseInt(shiftTextValue.getText().toString());
                if(shiftAmount >= 0){
                    for (int i = 0; i < strings[0].length(); i++) {
                        char unicode = strings[0].charAt(i);
                        if ((unicode >= 'A' && unicode <= 'Z') || ((unicode >= 'a' && unicode <= 'z'))) {
                            for (int n = 0; n < shiftAmount; n++) {
                                unicode++;
                                if (unicode > 'Z' && unicode < 'a') {
                                    unicode = 'A';
                                }
                                if (unicode > 'z') {
                                    unicode = 'a';
                                }
                            }
                        }
                        if(lettersShifted % 40 == 0){
                            publishProgress(lettersShifted);
                            if(isCancelled()){
                                progressBar.setVisibility(View.GONE);
                                cipherTextView.setText(newText);
                                return newText;
                            }
                        }
                        lettersShifted++;
                        newText += unicode;
                    }
                }else if(shiftAmount < 0){
                    for (int i = 0; i < strings[0].length(); i++) {
                        char unicode = strings[0].charAt(i);
                        if ((unicode >= 'A' && unicode <= 'Z') || ((unicode >= 'a' && unicode <= 'z'))) {
                            for (int n = 0; n > shiftAmount; n--) {
                                unicode--;
                                if (unicode < 'a' && unicode > 'Z') {
                                    unicode = 'z';
                                }
                                if (unicode < 'A') {
                                    unicode = 'Z';
                                }
                            }
                        }
                        if(lettersShifted % 40 == 0){
                            publishProgress(lettersShifted);
                            if(isCancelled()){
                                progressBar.setVisibility(View.GONE);
                                cipherTextView.setText(newText);
                                return newText;
                            }
                        }
                        lettersShifted++;
                        newText += unicode;
                    }
                }
                return newText;
            } else {
                return "";
            }
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
        protected void onPostExecute(String string) {
            progressBar.setVisibility(View.GONE);
            cipherTextView.setText(string);
        }
    }
}
