package com.example.dell.contactsdemo;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.example.dell.contactsdemo.Utils.ContactUtil;
import com.example.dell.contactsdemo.entity.Contact;

import java.util.ArrayList;

/**
 * Created by dell on 2016/4/12.
 */
public class SQLTest extends AndroidTestCase {
    public  void testgetobject(){
        ArrayList<Contact> contacts = ContactUtil.getContacts(getContext(), 11);
        for (Contact contact :contacts) {
            Log.i("lhd", "@@@@@@@@"+contact.getName());
        }
    }
}
