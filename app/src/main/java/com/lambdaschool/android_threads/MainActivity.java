package com.lambdaschool.android_threads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextShifts = findViewById(R.id.edit_text_shifts);
        final TextView textViewCypher = findViewById(R.id.text_view_cypher);
        final ProgressBar progressBar=findViewById(R.id.progress_bar);

        Button buttonDecrypt = findViewById(R.id.button_decrypt);
        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String textToDecrypt = textViewCypher.getText().toString();
                int numberOfShifts = Integer.parseInt(editTextShifts.getText().toString());
                String shiftedText = "";

                for (int i = 0; i < textToDecrypt.length(); i++) {
                    int oldValueOfChar = (int) textToDecrypt.charAt(i);
                    int newValueOfChar = oldValueOfChar;
                    if ((oldValueOfChar >= 65 && oldValueOfChar <= 90) || (oldValueOfChar >= 97 && oldValueOfChar <= 122)) {
                        for (int j = 1; j <= numberOfShifts; j++) {
                            newValueOfChar += 1;
                            if (newValueOfChar > 90 && newValueOfChar < 97) {
                                newValueOfChar = 65;
                            } else if (newValueOfChar > 122) {
                                newValueOfChar = 97;
                            }
                        }
                    }
                    shiftedText += Character.toString((char) newValueOfChar);
                }
                textViewCypher.setText(shiftedText);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
