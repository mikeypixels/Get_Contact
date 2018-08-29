package com.example.michael.getcontact;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";

    ArrayList<Contact> StoreContacts;
    Cursor cursor;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");

        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);

        //Sender number
        String smsNumber = "";

        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM = (Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.M);

            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                smsNumber = msgs[i].getOriginatingAddress();
                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
            }
            // Log and display the SMS message.
            Log.d(TAG, "onReceive: " + strMessage);
            Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();

            String[] sms_parts = strMessage.split(":");

            if (sms_parts[1].toLowerCase().contains("nitumie") && sms_parts[3].contains("7444") && !(sms_parts[2].contains("/"))) {
                String sms = "";
                StoreContacts = new ArrayList<>();

                String name, phonenumber;

                cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

                while (cursor.moveToNext()) {

                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    StoreContacts.add(new Contact(name, phonenumber));
                }

                cursor.close();

                String contact_name = sms_parts[2].replaceAll(" ", "");
                Log.d(TAG, "onReceive: " + contact_name);
                Log.d(TAG, "length: " + contact_name.length());


                Log.d(TAG, "onReceive: " + contact_name);

                ArrayList<Contact> filteredContacts = new ArrayList<>();
                boolean haipo = false;
                for (int i = 0; i < StoreContacts.size(); i++) {
                    if (i == 0) {
                        filteredContacts.add(StoreContacts.get(i));
                    } else {
                        for (Contact contact : filteredContacts) {
                            if (StoreContacts.get(i).getName().equals(contact.getName()) && StoreContacts.get(i).getPhone().equals(contact.getPhone())) {
                                haipo = false;
                            } else {
                                haipo = true;
                            }
                        }
                        if (haipo) {
                            filteredContacts.add(StoreContacts.get(i));
                        }
                    }
                }

                int numberOfContacts = 0;
                for (Contact contact : filteredContacts) {
                    if (contact.getName().toLowerCase().replaceAll(" ", "").equals(contact_name.toLowerCase()) && numberOfContacts < 5) {
                        sms = sms.concat(contact.getName() + " " + contact.getPhone() + "\n");
                        numberOfContacts++;
                    }
                }

                Log.d(TAG, "onReceive: " + sms);

                if (sms.equals("")) {
                    sms = "No Contacts found by the name " + contact_name + "\n\n             @Get Contact";
                }

                String scAddress = null;
                // Set pending intents to broadcast
                // when message sent and when delivered, or set to null.
                PendingIntent sentIntent = null, deliveryIntent = null;
                // Use SmsManager.
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage
                        (smsNumber, scAddress, sms,
                                sentIntent, deliveryIntent);

            } else if (sms_parts[1].toLowerCase().contains("nitumie") && sms_parts[2].contains("/") && sms_parts[3].contains("7444")) {
                String sms = "";
                StoreContacts = new ArrayList<>();

                String name, phonenumber;

                cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

                while (cursor.moveToNext()) {

                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    StoreContacts.add(new Contact(name, phonenumber.replaceAll(" ", "")));
//                    Log.d(TAG, "onReceive: " + name+" "+phonenumber);
                }

                cursor.close();

                String contact_names = sms_parts[2].replaceAll(" ", "");
                String[] name_contacts = contact_names.split("/");
                if (name_contacts.length <= 3) {
                    for (int i = 0; i < name_contacts.length; i++) {
                        Log.d(TAG, "onReceive: " + name_contacts[i] + "\n");
                        Log.d(TAG, "length: " + name_contacts[i].length() + "\n");
                    }

                    int numberOfContacts = 0;
                    String[] unFoundContacts = {"", "", ""};
                    ArrayList<Contact> filteredContacts = new ArrayList<>();
                    boolean haipo = false;
                    for (int i = 0; i < StoreContacts.size(); i++) {
                        if (i == 0) {
                            filteredContacts.add(StoreContacts.get(i));
                        } else {
                            for (Contact contact : filteredContacts) {
                                if (StoreContacts.get(i).getName().equals(contact.getName()) && StoreContacts.get(i).getPhone().equals(contact.getPhone())) {
                                    haipo = false;
                                } else {
                                    haipo = true;
                                }
                            }
                            if (haipo) {
                                filteredContacts.add(StoreContacts.get(i));
                            }
                        }
                    }

//                    ArrayList<String> contactList = new ArrayList<>();
                    for (Contact contact : filteredContacts) {

                        for (int i = 0; i < name_contacts.length; i++) {
                            if (contact.getName().toLowerCase().replaceAll(" ", "").equals(name_contacts[i].toLowerCase()) && numberOfContacts < 7) {
                                sms = sms.concat(contact.getName() + " " + contact.getPhone() + "\n");
//                                contactList.add(contact.getName()+contact.getPhone());
                                numberOfContacts++;
                            }
                        }
                    }

//                    ArrayList<String> filtered = new ArrayList<>();
//                    for(int i=0;i<contactList.size();i++){
//                        if(i==0){
//                            filtered.add(contactList.get(i));
//                        }else{
//                            for(int j=contactList.size()-1;j>=i+1;j--){
//                                if(filtered){
//                                    haipo = false;
//                                }else{
//                                    haipo = true;
//                                }
//                            }
//                            if(haipo){
//                                filteredContacts.add(StoreContacts.get(i));
//                            }
//                        }
//                    }

                    int j = 0;
                    for (int i = 0; i < name_contacts.length; i++) {
                        if (!(sms.toLowerCase().replaceAll("","").contains(name_contacts[i].toLowerCase().replaceAll(" ","")))) {
                            unFoundContacts[j] = name_contacts[i];
                            j++;
                        }
                    }

                    Log.d(TAG, "onReceive: " + sms);

                    String[] sms_txt = {"", ""};
                    if (!(sms.equals(""))) {
                        if (!(unFoundContacts[0].equals(""))) {
                            sms = sms.concat("\nthe contacts with the name(s) ");
                            if (j > 1) {
                                for (int i = 0; i < 2; i++) {
                                    sms_txt[i] = sms_txt[i].concat(unFoundContacts[i]);
                                    Log.d(TAG, "onReceive: " + "this works");
                                }
                                sms = sms.concat(sms_txt[0] + " and " + sms_txt[1]);
                                sms = sms.concat(" were not found!\n\n            @Get Contact");
                                Log.d(TAG, "onReceive: " + "this works");
                            } else {
                                sms = sms.concat(unFoundContacts[0] + " was not found!\n\n            @Get Contact");

                            }

                        }
                    } else {
                        sms = "No Contacts found by the names requested \n\n            @Get Contact";
                    }
                } else {
                    sms = "sorry you can only request for a maximum of 3 contacts, please try again. \n\n            @Get Contact";
                }

                String scAddress = null;
                // Set pending intents to broadcast
                // when message sent and when delivered, or set to null.
                PendingIntent sentIntent = null, deliveryIntent = null;
                // Use SmsManager.
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage
                        (smsNumber, scAddress, sms,
                                sentIntent, deliveryIntent);

            }
        }
    }
}



