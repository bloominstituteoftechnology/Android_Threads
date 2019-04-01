package com.jakeesveld.android_threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText editInput;
    Button buttonSubmit;
    TextView textCipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        editInput = findViewById(R.id.edit_input);
        buttonSubmit = findViewById(R.id.button_shift);
        textCipher = findViewById(R.id.text_view_cypher);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String cipherString = textCipher.getText().toString();
                int shiftTimes = Integer.parseInt(editInput.getText().toString());
                String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
                String UPPER_CASE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

                String shiftedCipher = "";
                for(int i = 0; i < cipherString.length(); i++){
                    if (ALPHABET.indexOf(cipherString.charAt(i)) != -1) {
                        int position = ALPHABET.indexOf(cipherString.charAt(i));
                        int shiftedPosition = (position + shiftTimes) % 26;
                        /*if(shiftedPosition > 0){
                            shiftedPosition = ALPHABET.length() + shiftedPosition;
                        }*/
                        char shiftedChar = ALPHABET.charAt(shiftedPosition);
                        shiftedCipher += shiftedChar;
                    }else if(UPPER_CASE_ALPHABET.indexOf(cipherString.charAt(i)) != -1){
                        int position = UPPER_CASE_ALPHABET.indexOf(cipherString.charAt(i));
                        int shiftedPosition = (position + shiftTimes) % 26;
                        char shiftedChar = UPPER_CASE_ALPHABET.charAt(shiftedPosition);
                        shiftedCipher += shiftedChar;
                    }else{
                        shiftedCipher += cipherString.charAt(i);
                    }

                }

                textCipher.setText(shiftedCipher);

                progressBar.setVisibility(View.GONE);


            }
        });

    }



}
