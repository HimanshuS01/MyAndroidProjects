package com.codingblocks.materialdesignlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

public class SwitchActivity extends Activity {

    //Parse the color string, and return the corresponding color-int
    int backgroundColor = Color.parseColor("#1E88E5");

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        int color = getIntent().getIntExtra("BACKGROUND", Color.BLACK);
        findViewById(R.id.checkBox).setBackgroundColor(color);
        findViewById(R.id.switchView).setBackgroundColor(color);
    }
}
