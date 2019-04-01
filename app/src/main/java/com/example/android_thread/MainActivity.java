package com.example.android_thread;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public  class MainActivity extends AppCompatActivity {
    public int MAXIMUM = 26;
    public ShifterTask shifterTask;
    public Button shiftButton;
    public EditText editText;
    public TextView textView;
    public ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       progressBar = findViewById(R.id.progress_bar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shiftButton = findViewById(R.id.shift_button);

       shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                if (shifterTask != null) {
                    return;
                }

                editText = findViewById(R.id.shift_edit_text);
                String shiftStr = editText.getText().toString();
                try {
                    int shift = Integer.parseInt(shiftStr);
                    if (shift < -MAXIMUM || shift > MAXIMUM) {
                        textView.setText(getString(R.string.OutOfBounds));
                        return;
                    }

                    shifterTask = new ShifterTask(MainActivity.this, shift);
                    String encryptedStr = getResources().getString(R.string.contents_shifted);
                    progressBar.setMax(encryptedStr.length());
                    shifterTask.execute(encryptedStr);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    textView.setText(getString(R.string.invalidString));
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        // cancel to avoid leak
        if (shifterTask != null) {
            shifterTask.cancel(false);
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    private static class ShifterTask extends AsyncTask<String, Integer, String> {

        private ShifterTask(MainActivity mainActivity, int shift) {
            this.mainActivity = mainActivity;
            this.shift = shift;
        }

        private static final int ALPHABET = 26;
        private static final int MIN_LOWERCASE_CHAR = 'a';
        private static final int MAX_LOWERCASE_CHAR = 'z';
        private static final int MIN_UPPERCASE_CHAR = 'A';
        private static final int MAX_UPPERCASE_CHAR = 'Z';

        private MainActivity mainActivity;
        private int shift;

        @Override
        protected void onPreExecute() {
            ((TextView)mainActivity.findViewById(R.id.status_text)).setText("Decyphering");
            mainActivity.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String encryptedString = strings[0];
            int onePercentSize = Math.max((int)(encryptedString.length() * 0.01), 1);

            String decypheredString = "";
            for (int i = 0; i < encryptedString.length(); ++i) {
                char letter = encryptedString.charAt(i);

                if (!Character.isLetter(letter)) {
                    decypheredString += (letter);
                    continue;
                }

                int shiftedLetter = letter + shift;
                if (Character.isLowerCase(letter)) { // originally a lowercase
                    if (shiftedLetter < MIN_LOWERCASE_CHAR) { // under-shifted
                        shiftedLetter += ALPHABET;
                    } else if (shiftedLetter > MAX_LOWERCASE_CHAR) { // over-shifted
                        shiftedLetter -= ALPHABET;
                    }
                } else {
                    if (shiftedLetter < MIN_UPPERCASE_CHAR) {
                        shiftedLetter += ALPHABET;
                    } else if (shiftedLetter > MAX_UPPERCASE_CHAR) {
                        shiftedLetter -= ALPHABET;
                    }
                }

                decypheredString += ((char)shiftedLetter);

                int finishedSize = i + 1;
                if (finishedSize % onePercentSize == 0) {
                    publishProgress(finishedSize);
                    if (isCancelled()) {
                        break;
                    }
                }
            }

            return decypheredString;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ((ProgressBar)mainActivity.findViewById(R.id.progress_bar)).setProgress(values[0]);

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mainActivity.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
            ((TextView)mainActivity.findViewById(R.id.status_text)).setText("idling");
            ((TextView)mainActivity.findViewById(R.id.cipher_text)).setText(s);
            mainActivity.shifterTask = null;
        }

        @Override
        protected void onCancelled() {
            mainActivity.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
            ((TextView)mainActivity.findViewById(R.id.status_text)).setText("Cancelled");
            ((TextView)mainActivity.findViewById(R.id.cipher_text)).setText("");
            super.onCancelled();
        }
    }
}