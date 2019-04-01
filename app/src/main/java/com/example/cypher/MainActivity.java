package com.example.cypher;

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
    ProgressBar progressBar;
    EditText et;
    TextView tv;
    int progress;
    public static int key;
    String startingText;
    char[] upperCase = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    char[] lowerCase = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.cypher_text);
        et = findViewById(R.id.shift_by_view);
        progressBar = findViewById(R.id.progressBar);
        startingText =  tv.getText().toString();
        Log.i("test2", String.valueOf(startingText.length()));



        Button button = findViewById(R.id.button_Shift);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                key = Integer.parseInt(et.getText().toString());
                ThreadClass threadClass = new ThreadClass();
                threadClass.execute();
            }
        });
    }


    public class ThreadClass extends AsyncTask{
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            tv.setText(String.valueOf(o));
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            progressBar.setMax(startingText.length());
            progressBar.setProgress(progress);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String text = startingText;
            char[] textCharArray = text.toCharArray();
            char[] textShifted = new char[textCharArray.length];
            String output = "";

            for(int i = 0; i < textCharArray.length; i++){
                for(int j = 0; j < lowerCase.length; j++){
                    if(textCharArray[i] == upperCase[j]){
                        textShifted[i] = upperCase[((j+key)%upperCase.length)];
                        j=0;
                        break;
                    }else if(textCharArray[i] == lowerCase[j]) {
                        textShifted[i] = lowerCase[((j + key) % lowerCase.length)];
                        j=0;
                        break;
                    }
                }if(textShifted[i]=='\u0000'){textShifted[i] = textCharArray[i];}
                progress = i;
                publishProgress();
                output = output + String.valueOf(textShifted[i]);


            }return output;

        }
    }


}
