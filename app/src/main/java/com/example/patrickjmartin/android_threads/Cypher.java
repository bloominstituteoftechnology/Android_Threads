package com.example.patrickjmartin.android_threads;

import android.widget.ProgressBar;

public class Cypher {

    public static String rightShift(String text, int shift) {
        if (text != null) {
            String newText = "";
            for (int i = 0; i < text.length(); i++) {

                char unicode = text.charAt(i);
                if ((unicode >= 'A' && unicode <= 'Z') || ((unicode >= 'a' && unicode <= 'z'))) {
                    for (int j = 0; j < shift; j++) {

                        unicode++;

                        if (unicode > 'Z' && unicode < 'a') {

                            unicode = 'A';

                        }

                        if (unicode > 'z') {

                            unicode = 'a';

                        }

                    }

                }
                newText += unicode;
            }
            return newText;
        } else {
            return "";
        }
    }


}
