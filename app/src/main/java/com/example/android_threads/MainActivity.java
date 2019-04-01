package com.example.android_threads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button shiftButton = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.edit_text);
        final TextView textView = findViewById(R.id.text_view);

        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(cipher(textView.getText().toString(), Integer.parseInt(editText.getText().toString())));
            }
        });
    }

    String cipher(String msg, int shift) {
        String temp = "";
        shift = shift%26;
        int length = msg.length();
        for (int i = 0; i < length; i++) {
            char character = msg.charAt(i);
            char shiftedChar = (char) (msg.charAt(i) + shift);
            if (character >= 'A' && character <= 'Z') {
                if (shiftedChar > 'Z') { //case where shiftedChar goes out of bounds (positive)
                    temp += (char) (msg.charAt(i) - (26 - shift));
                } else if (shiftedChar < 'A') { //case where shiftedChar goes out of bounds (negative)
                    temp += (char) (msg.charAt(i) + (26 + shift));
                } else { //case where shiftedChar is in bounds
                    temp += (char) (msg.charAt(i) + shift);
                }
            } else if (character >= 'a' && character <= 'z') {
                if (shiftedChar > 'z') { //case where shiftedChar goes out of bounds (positive)
                    temp += (char) (msg.charAt(i) - (26 - shift));
                } else if (shiftedChar < 'a') { //case where shiftedChar goes out of bounds (negative)
                    temp += (char) (msg.charAt(i) + (26 + shift));
                } else { //case where shiftedChar is in bounds
                    temp += (char) (msg.charAt(i) + shift);
                }
            } else { //case where char isn't a letter, and shouldn't be shifted
                temp += (char) (msg.charAt(i));
            }
        }
        return temp;
    }
}
