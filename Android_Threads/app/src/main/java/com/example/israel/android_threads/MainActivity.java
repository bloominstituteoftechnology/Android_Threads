package com.example.israel.android_threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int MAX_SHIFT = 25;
    private DecryptorAsyncTask decryptorAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (decryptorAsyncTask != null) { // still decrypting
                    return;
                }

                EditText shiftEditText = findViewById(R.id.edit_text_shift);
                String shiftStr = shiftEditText.getText().toString();
                try {
                    int shift = Integer.parseInt(shiftStr);
                    if (shift < -MAX_SHIFT || shift > MAX_SHIFT) { // shift oob
                        ((TextView)findViewById(R.id.text_view_status)).setText(getResources().getString(R.string.text_error_shift_oob));
                        return;
                    }

                    decryptorAsyncTask = new DecryptorAsyncTask(MainActivity.this, shift);
                    String encryptedStr = getResources().getString(R.string.contents_shifted);
                    ((ProgressBar)findViewById(R.id.progress_bar_decryption)).setMax(encryptedStr.length());
                    decryptorAsyncTask.execute(encryptedStr);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ((TextView)findViewById(R.id.text_view_status)).setText(getResources().getString(R.string.text_error_shift_invalid));
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        // cancel to avoid leak
        if (decryptorAsyncTask != null) {
            decryptorAsyncTask.cancel(false);
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    private static class DecryptorAsyncTask extends AsyncTask<String, Integer, String> {

        public DecryptorAsyncTask(MainActivity mainActivity, int shift) {
            this.mainActivity = mainActivity;
            this.shift = shift;
        }

        public static final int ALPHABET_SIZE = 26;
        public static final int MIN_LOWERCASE_CHAR = 'a';
        public static final int MAX_LOWERCASE_CHAR = 'z';
        public static final int MIN_UPPERCASE_CHAR = 'A';
        public static final int MAX_UPPERCASE_CHAR = 'Z';

        private MainActivity mainActivity;
        private int shift;

        @Override
        protected void onPreExecute() {
            ((TextView)mainActivity.findViewById(R.id.text_view_status)).setText(mainActivity.getResources().getString(R.string.text_status_decrypting));
            mainActivity.findViewById(R.id.progress_bar_decryption).setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String encryptedString = strings[0];
            int onePercentSize = Math.max((int)(encryptedString.length() * 0.01), 1);

            // @NOTE: for testing purpose only. StringBuilder is a must
            String decryptedStr = ""; // new StringBuilder(encryptedString.length());
            for (int i = 0; i < encryptedString.length(); ++i) {
                char c = encryptedString.charAt(i);

                if (!Character.isLetter(c)) { // do not shift non alphabet
                    decryptedStr += (c);
                    continue;
                }

                int shiftedCInt = c + shift;
                if (Character.isLowerCase(c)) { // originally a lowercase
                    if (shiftedCInt < MIN_LOWERCASE_CHAR) { // under-shifted
                        shiftedCInt += ALPHABET_SIZE;
                    } else if (shiftedCInt > MAX_LOWERCASE_CHAR) { // over-shifted
                        shiftedCInt -= ALPHABET_SIZE;
                    }
                } else {
                    if (shiftedCInt < MIN_UPPERCASE_CHAR) {
                        shiftedCInt += ALPHABET_SIZE;
                    } else if (shiftedCInt > MAX_UPPERCASE_CHAR) {
                        shiftedCInt -= ALPHABET_SIZE;
                    }
                }

                decryptedStr += ((char)shiftedCInt);

                int finishedSize = i + 1;
                if (finishedSize % onePercentSize == 0) {
                    publishProgress(finishedSize);
                    if (isCancelled()) {
                        break;
                    }
                }
            }

            return decryptedStr;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ((ProgressBar)mainActivity.findViewById(R.id.progress_bar_decryption)).setProgress(values[0]);

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mainActivity.findViewById(R.id.progress_bar_decryption).setVisibility(View.INVISIBLE);
            ((TextView)mainActivity.findViewById(R.id.text_view_status)).setText(mainActivity.getResources().getString(R.string.text_status_idle));
            ((TextView)mainActivity.findViewById(R.id.text_view_decrypted)).setText(s);
            mainActivity.decryptorAsyncTask = null;
        }

        @Override
        protected void onCancelled() {
            mainActivity.findViewById(R.id.progress_bar_decryption).setVisibility(View.INVISIBLE);
            ((TextView)mainActivity.findViewById(R.id.text_view_status)).setText(mainActivity.getResources().getString(R.string.text_status_cancelled));
            ((TextView)mainActivity.findViewById(R.id.text_view_decrypted)).setText("");
            super.onCancelled();
        }
    }
}
