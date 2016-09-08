package com.codingblocks.materialdesignlibrary;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class CircularFloatingActionButton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_floating_action_button);

        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getDrawable(R.drawable.float_button));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView ItemIconOne = new ImageView(this);
        ItemIconOne.setImageDrawable(getDrawable(R.drawable.movie));

        ImageView ItemIconTwo = new ImageView(this);
        ItemIconTwo.setImageDrawable(getDrawable(R.drawable.ratings));

        ImageView ItemIconThree = new ImageView(this);
        ItemIconThree.setImageDrawable(getDrawable(R.drawable.home));

        SubActionButton button1 = itemBuilder.setContentView(ItemIconOne).build();
        SubActionButton button2 = itemBuilder.setContentView(ItemIconTwo).build();
        SubActionButton button3 = itemBuilder.setContentView(ItemIconThree).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(actionButton)
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircularFloatingActionButton.this, "Movie Icon clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
