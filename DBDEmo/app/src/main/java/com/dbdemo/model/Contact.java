package com.dbdemo.model;

public class Contact {
     
    //private variables
    int _id;
    private String _name;
    private String _phone_number;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    private String state;
    private String address;
    String dob;

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phone_number() {
        return _phone_number;
    }

    public void set_phone_number(String _phone_number) {
        this._phone_number = _phone_number;
    }

    private   String _email;
     
    // Empty constructor
    public Contact(){
         
    }
    // constructor
    public Contact(int id, String name, String _phone_number,String _email){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._email=_email;
    }
     
    // constructor
    public Contact(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
    }

}