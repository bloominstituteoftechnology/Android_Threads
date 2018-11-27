package com.example.patrickjmartin.android_threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView cypherTV;
    private EditText shiftString;
    private ProgressBar bar;
    private AsyncTask cypherTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cypherTV = findViewById(R.id.cypher_translate);
        shiftString = findViewById(R.id.editText2);
        bar = findViewById(R.id.progressBar2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence cypherText = cypherTV.getText();
                cypherTask = (new CypherTask()).execute(cypherText.toString());
            }
        });
    }

    public class CypherTask extends AsyncTask<String, Integer, String>{



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
            bar.setMax(3);

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
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
