package com.rybarstudios.cyphers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button shiftButton;
    EditText mEditText;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);
        mTextView = findViewById(R.id.text_view);
        shiftButton = findViewById(R.id.button);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mTextView.getText().toString();
                int charsToShift = Integer.parseInt(mEditText.getText().toString());
            }
        });
    }

    private String shift(String content, int numToShift) {
        //TODO write algo to shift the content by n number of characters

        return content;
    }
}
