package com.example.jacob.android_threads;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView textView;
    EditText editText;
    Context context;
    Button button;
    offloadTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        progressBar = findViewById(R.id.progress_bar);
        button = findViewById(R.id.button_update);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView = findViewById(R.id.text_main_content);
                editText = findViewById(R.id.edit_shift);
                String unShiftedString = getResources().getString(R.string.contents_shifted);
                String shiftAmount = editText.getText().toString();

                task = new offloadTask();
                task.execute(unShiftedString, shiftAmount);
            }
        });

        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task != null) {
                    task.cancel(false);
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (task != null) {
            task.cancel(false);
        }
    }

    public class offloadTask extends AsyncTask<String, Integer, String> {

        public static final int progressResolution = 50;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(getResources().getString(R.string.contents_shifted).length());

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
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            progressBar.setVisibility(View.GONE);
            textView.setText(s);
            Toast.makeText(getApplicationContext(), "Processing Cancelled", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            if (params[0] != null) {
                StringBuilder outputStringBuilder = new StringBuilder();
                int shift = Integer.parseInt(params[1]);
                if (shift < 0) {
                    shift = shift % -26;
                    shift = 26 + shift;
                }
                int loops = params[0].length();

                for (int i = 0; i < loops; ++i) {
                    char unicode = params[0].charAt(i);
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

                    //Add some pi digits calculations to slow it down a little
                    Log.i("piCalc=", pi_digits(100));

                    if (i % Math.round(loops/ progressResolution) == 0) {
                        publishProgress(i);
                        if (isCancelled()) {
                            outputStringBuilder.append("...[Processing canceled.  Press Update to refresh.]");
                            return outputStringBuilder.toString();
                        }
                    }
                    outputStringBuilder.append(unicode);
                }
                return outputStringBuilder.toString();
            } else {
                return "";
            }

        }
    }

    public static String pi_digits(int digits) {
        int SCALE = 10000;
        int ARRINIT = 2000;
        StringBuffer pi = new StringBuffer();
        int[] arr = new int[digits + 1];
        int carry = 0;

        for (int i = 0; i <= digits; ++i)
            arr[i] = ARRINIT;

        for (int i = digits; i > 0; i -= 14) {
            int sum = 0;
            for (int j = i; j > 0; --j) {
                sum = sum * j + SCALE * arr[j];
                arr[j] = sum % (j * 2 - 1);
                sum /= j * 2 - 1;
            }
            pi.append(String.format("%04d", carry + sum / SCALE));
            carry = sum % SCALE;
        }
        return pi.toString();
    }
}
