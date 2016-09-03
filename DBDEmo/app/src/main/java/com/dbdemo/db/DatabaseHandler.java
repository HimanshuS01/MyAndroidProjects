package com.dbdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbdemo.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";

    private static final String KEY_DOB= "dop";
    private static final String KEY_STATE= "state";
    private static final String KEY_ADDRESS = "address";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_DOB + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_STATE + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    public long addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_name()); // Contact Name
        values.put(KEY_PH_NO, contact.get_phone_number()); // Contact Phone
        values.put(KEY_EMAIL, contact.get_email());
        values.put(KEY_DOB, contact.getDob()); // Contact Name
        values.put(KEY_STATE, contact.getState()); // Contact Phone
        values.put(KEY_ADDRESS, contact.getAddress());
 
        // Inserting Row
        long id=db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        return id;
    }
 
    // Getting single contact
    public  Contact getContact(String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();
        Contact contact=null;
        String query="select * from " + TABLE_CONTACTS + " where " + KEY_PH_NO + "=" + mobile;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();

            contact = new Contact();
            contact.set_phone_number(cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
            contact.set_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            contact.set_email(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            contact.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
            contact.setDob(cursor.getString(cursor.getColumnIndex(KEY_DOB)));
            contact.setState(cursor.getString(cursor.getColumnIndex(KEY_STATE)));

            // return contact
        }
        return contact;
    }
     
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                contact.set_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                contact.set_phone_number(cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
                contact.set_email(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_name()); // Contact Name
        values.put(KEY_PH_NO, contact.get_phone_number()); // Contact Phone
        values.put(KEY_EMAIL, contact.get_email());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.get_id()) });
    }
 
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.get_id()) });
        db.close();
    }
 
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
}