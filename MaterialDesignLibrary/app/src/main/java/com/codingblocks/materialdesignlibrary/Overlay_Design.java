package com.codingblocks.materialdesignlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class Overlay_Design extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay__design);
        final FrameLayout container=(FrameLayout)findViewById(R.id.root_view);
        View inflatedLayout= getLayoutInflater().inflate(R.layout.my_background_view_for_overlay, null, false);
        container.addView(inflatedLayout);

        final View layoutOverlay=getLayoutInflater().inflate(R.layout.my_foreground_view_for_overlay,null,false);
        container.addView(layoutOverlay);

//        inflatedLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                container.removeView(layoutOverlay);
//            }
//        });
    }
}
