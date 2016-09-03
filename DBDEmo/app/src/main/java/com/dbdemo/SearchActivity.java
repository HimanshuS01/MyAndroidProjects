package com.dbdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dbdemo.db.DatabaseHandler;
import com.dbdemo.model.Contact;

public class SearchActivity extends AppCompatActivity {
    private EditText mobile;
    Button display;
    TextView dataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mobile = (EditText) findViewById(R.id.mobile);
        dataTv = (TextView) findViewById(R.id.data);
        display = (Button) findViewById(R.id.search);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.getText().toString().trim().equals("")) {
                    mobile.setError("Please enter mobile no");
                    mobile.requestFocus();
                    return;
                }

                DatabaseHandler databaseHandler = new DatabaseHandler(SearchActivity.this);
                Contact contact = databaseHandler.getContact(mobile.getText().toString());

                dataTv.setText("Name: " + contact.get_name() + "\n" +
                                "" + "Email :" + contact.get_email() + "\n"
                                + "Mobile :" + contact.get_phone_number() + "\n"
                                + "DOB :" + contact.getDob() + "\n"
                                + "Address :" + contact.getAddress() + "\n"
                                + "State :" + contact.getState() + "\n"


                );

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
