package com.example.cypher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText et;
    TextView tv;
    char[] upperCase = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    char[] lowerCase = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.cypher_text);
        et = findViewById(R.id.shift_by_view);
        progressBar = findViewById(R.id.progressBar);

        final String StartingText = tv.getText().toString();

        Button button = findViewById(R.id.button_Shift);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int key = Integer.parseInt(et.getText().toString());
                String text = StartingText;
                char[] textCharArray = text.toCharArray();
                char[] textShifted = new char[textCharArray.length];

                for(int i = 0; i < textCharArray.length; i++){
                    for(int j = 0; j < lowerCase.length; j++){
                            if(textCharArray[i] == upperCase[j]){
                                textShifted[i] = upperCase[((j+key)%upperCase.length)];
                                j=0;
                                break;
                            }else if(textCharArray[i] == lowerCase[j]) {
                                textShifted[i] = lowerCase[((j + key) % lowerCase.length)];
                                j=0;
                                break;
                            }
                        }if(textShifted[i]=='\u0000'){textShifted[i] = textCharArray[i];}
                    }
                    tv.setText(String.valueOf(textShifted));


            }
        });
    }


}
