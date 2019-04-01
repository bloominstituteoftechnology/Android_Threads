package com.rybarstudios.cyphers;

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

    Button shiftButton;
    EditText mEditText;
    TextView mTextView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);
        mTextView = findViewById(R.id.text_view);
        mProgressBar = findViewById(R.id.progressBar);
        shiftButton = findViewById(R.id.button);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mTextView.getText().toString();
                String charsToShift = mEditText.getText().toString();
                mProgressBar.setMax(31468);
                new AddCypherAsync().execute(content, charsToShift);
            }
        });
    }

    class AddCypherAsync extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = strings[0];
            int numToShift = Integer.parseInt(strings[1]);
            String result = "";

            for(int i = 0; i < content.length(); i++) {
                if(Character.isUpperCase(content.charAt(i))) {
                    char ch = (char)(((int)content.charAt(i) +
                            numToShift - 65) % 26 + 65);
                    result += ch;
                }else if(Character.isLowerCase(content.charAt(i))){
                    char ch = (char)(((int)content.charAt(i) +
                            numToShift - 97) % 26 + 97);
                    result += ch;
                }else {
                    result += content.charAt(i);
                }
                publishProgress(i, content.length());
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setText(result);
        }
    }

/*    private StringBuilder shift(String content, int numToShift) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < content.length(); i++) {
            if(Character.isUpperCase(content.charAt(i))) {
                char ch = (char)(((int)content.charAt(i) +
                        numToShift - 65) % 26 + 65);
                builder.append(ch);
            }else if(Character.isLowerCase(content.charAt(i))){
                char ch = (char)(((int)content.charAt(i) +
                        numToShift - 97) % 26 + 97);
                builder.append(ch);
            }else {
                builder.append(content.charAt(i));
            }
        }
        return builder;
    }*/
}
