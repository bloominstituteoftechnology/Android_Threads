package com.example.jacob.android_threads;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;


public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textView;
    EditText editText;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
         progressBar = findViewById(R.id.progress_bar);

        textView = findViewById(R.id.text_main_content);
        editText = findViewById(R.id.edit_shift);
        String unShiftedString = getResources().getString(R.string.contents_shifted);
        String shiftAmount = editText.getText().toString();

        new offloadTask().execute(unShiftedString,shiftAmount);
    }


    public static String shiftCypher(String inputString, int shift) {
        if (inputString != null) {
            StringBuilder outputStringBuilder = new StringBuilder();
            if (shift < 0) {
                shift = shift % -26;
                shift = 26 - shift;
            }
            for (int i = 0; i < inputString.length(); ++i) {
                char unicode = inputString.charAt(i);
                if ((unicode >= 'A' && unicode <= 'Z') || ((unicode >= 'a' && unicode <= 'z'))) {
                    for (int n = 0; n < shift; ++n) {
                        ++unicode;
                        if (unicode > 'Z' && unicode < 'a') {
                            unicode = 'A';
                        }
                        if (unicode > 'z') {
                            unicode = 'a';
                        }
                    }
                }
                outputStringBuilder.append(unicode);
            }
            return outputStringBuilder.toString();
        } else {
            return "";
        }
    }

    public class offloadTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            textView.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            String shiftedString = shiftCypher(params[0], Integer.parseInt(params[1]));
            return shiftedString;
        }
    }


}
