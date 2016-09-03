package com.dbdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dbdemo.db.DatabaseHandler;
import com.dbdemo.model.Contact;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameEdt, phoneEdt, emailEdt, address;
    Spinner state;
    private Button submitBtn;
    DatePicker dobP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        nameEdt = (EditText) findViewById(R.id.nameEdt);
        phoneEdt = (EditText) findViewById(R.id.mobileEdt);
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        state = (Spinner) findViewById(R.id.stateEdt);
        address = (EditText) findViewById(R.id.addressEdt);
        dobP = (DatePicker) findViewById(R.id.dobEdt);
        submitBtn = (Button) findViewById(R.id.submit);

        submitBtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {

        if (nameEdt.getText().toString().trim().equals("")) {
            nameEdt.setError("Please enter name");
            nameEdt.requestFocus();
            return;
        }

        if (phoneEdt.getText().toString().trim().equals("")) {
            phoneEdt.setError("Please enter name");
            phoneEdt.requestFocus();
            return;
        }
        if (emailEdt.getText().toString().trim().equals("")) {
            emailEdt.setError("Please enter email");
            emailEdt.requestFocus();
            return;
        }

        if (address.getText().toString().trim().equals("")) {
            address.setError("Please enter address");
            address.requestFocus();
            return;
        }


        Contact contact = new Contact();
        contact.set_name(nameEdt.getText().toString());
        contact.set_phone_number(phoneEdt.getText().toString());
        contact.set_email(emailEdt.getText().toString());
        contact.setAddress(address.getText().toString());
        contact.setDob(dobP.getDayOfMonth() + "/" + dobP.getMonth() + "/" + dobP.getYear());
        contact.setState(state.getSelectedItem().toString());


        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        if (databaseHandler.addContact(contact) > 0) {
            Intent in = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(in);
        } else {
            Toast.makeText(MainActivity.this, "Error in saving contact", Toast.LENGTH_SHORT).show();
        }


    }
}
