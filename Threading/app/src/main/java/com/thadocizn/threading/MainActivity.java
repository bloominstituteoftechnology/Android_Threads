package com.thadocizn.threading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView msg;
    EditText shift;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msg = findViewById(R.id.textViewMsg);
        shift = findViewById(R.id.editTextShift);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMsg = msg.getText().toString();
                int shifted = Integer.parseInt(shift.getText().toString());
                progressBar.setVisibility(View.VISIBLE);

                if (strMsg != null) {
                    String newText = "";
                    for (int i = 0; i < strMsg.length(); ++i) {
                        char unicode = strMsg.charAt(i);
                        if ((unicode >= 'A' && unicode <= 'Z') || ((unicode >= 'a' && unicode <= 'z'))) {
                            for (int n = 0; n < shifted; ++n) {
                                ++unicode;
                                if (unicode > 'Z' && unicode < 'a') {
                                    unicode = 'A';
                                }
                                if (unicode > 'z') {
                                    unicode = 'a';
                                }
                            }
                        }
                        newText += unicode;
                        msg.setText(newText);

                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
