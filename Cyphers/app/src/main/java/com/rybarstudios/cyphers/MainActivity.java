package com.rybarstudios.cyphers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button shiftButton;
    EditText mEditText;
    TextView mTextView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);
        mTextView = findViewById(R.id.text_view);
        mProgressBar = findViewById(R.id.progressBar);
        shiftButton = findViewById(R.id.button);
        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                String content = mTextView.getText().toString();
                int charsToShift = Integer.parseInt(mEditText.getText().toString());
                mTextView.setText(shift(content, charsToShift));
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private StringBuilder shift(String content, int numToShift) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < content.length(); i++) {
            if(Character.isUpperCase(content.charAt(i))) {
                char ch = (char)(((int)content.charAt(i) +
                        numToShift - 65) % 26 + 65);
                builder.append(ch);
            }else if(Character.isLowerCase(content.charAt(i))){
                char ch = (char)(((int)content.charAt(i) +
                        numToShift - 97) % 26 + 97);
                builder.append(ch);
            }else {
                builder.append(content.charAt(i));
            }
        }
        return builder;
    }
}
