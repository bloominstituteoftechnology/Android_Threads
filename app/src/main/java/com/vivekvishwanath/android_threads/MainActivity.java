package com.vivekvishwanath.android_threads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button shiftButton;
    TextView cipherTextView;
    EditText userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cipherTextView = findViewById(R.id.cipher_data_text);
        userInput = findViewById(R.id.shift_input);
        shiftButton = findViewById(R.id.shift_button);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cipher = cipherTextView.getText().toString();
                int shift = Integer.parseInt(userInput.getText().toString());
                String newString = "";
                char c;
                for (int i = 0; i < cipher.length(); i++) {
                    c = cipher.charAt(i);
                    if (Character.isLetter(c)) {
                        c = (char) (cipher.charAt(i) + shift);
                    }
                    if (Character.isLowerCase((cipher.charAt(i))) && c > 'z'
                    || Character.isUpperCase(cipher.charAt(i)) && c > 'Z') {
                        c = (char) (cipher.charAt(i) - (26 - shift));
                    }
                    newString += c;
                }
                System.out.println(newString);
            }
        });

    }
}
