package com.mact.simpleautomationapp.Utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ContactRetrieval {

    public static String getContactNumber(String name, Context context){
        String number = "";
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                "DISPLAY_NAME = '" + name + "'", null, null);
        if (cursor.moveToFirst()) {
            String contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //
            //  Get all phone numbers.
            //
            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phones.moveToNext()) {

                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                switch (type) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        // do something with the Mobile number here...
                        number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        break;
                }
            }
            phones.close();
        }
        cursor.close();
        return number;
    }

    public static List<String> getContactList(Context context){
        List<String> contactNames = new ArrayList<>();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, sort);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactNames.add(name);
            }
        }
        return contactNames;
    }

    public static boolean isSmsPermissionGranted(Context context){
        int grantReceiveSms = ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS);
        int grantReadSms = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS);
        int grantReadContact = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        if(grantReceiveSms != PackageManager.PERMISSION_GRANTED
                && grantReadSms != PackageManager.PERMISSION_GRANTED
                && grantReadContact != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }



}
