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

    String cipher(String msg, int shift){
        String temp = "";
        int length = msg.length();
        for(int i = 0; i < length; i++){
            char character = (char)(msg.charAt(i) + shift);
            if (character > 'z')
                temp += (char)(msg.charAt(i) - (26-shift));
            else
                temp += (char)(msg.charAt(i) + shift);
        }
        return temp;
    }
}
