package com.xbpsolutions.xbpdialer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import bolts.Continuation;
import bolts.Task;
import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.PhoneNumber;
import com.github.tamir7.contacts.Query;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class DialerMainActivity extends AppCompatActivity {

  private static final String TAG = "XBP Dialer";

  private static final int READ_CONTACT_PERMISSION_REQUEST_CODE = 76;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dialer_main);

    checkPermission();

  }

  private void queryContacts() {
    Task.callInBackground(new Callable<Void>() {
      @Override
      public Void call() throws Exception {


//        Query q = Contacts.getQuery();
//        q.include(Contact.Field.DisplayName, Contact.Field.PhoneNumber, Contact.Field.PhoneNormalizedNumber, Contact.Field.Email);
//        Query q1 = Contacts.getQuery();
//        q1.whereEqualTo(Contact.Field.DisplayName, "Tamir Shomer");
//        q1.hasPhoneNumber();
//
//        Query q2 = Contacts.getQuery();
//        q2.whereStartsWith(Contact.Field.DisplayName, "Guy");
//        q2.hasPhoneNumber();
//        List<Query> queries = new ArrayList<>();
//        queries.add(q1);
//        queries.add(q2);
//        q.or(queries);
//
//        List<Contact> contacts = q.find();

        List<Contact> contacts = Contacts.getQuery().find();

        for (Contact contact : contacts) {

          Log.e("________Name_______",""+contact.getDisplayName());

         if(contact.getPhoneNumbers().size()>0){
           List<PhoneNumber> numbers = contact.getPhoneNumbers();
           for (PhoneNumber number : numbers) {
             Log.e("Number ",""+number.getNumber());
           }
         }

          Log.e("___________________","");


        }

        return null;
      }
    }).continueWith(new Continuation<Void, Void>() {
      @Override
      public Void then(Task<Void> task) throws Exception {
        if (task.isFaulted()) {
          Log.e(TAG, "find failed", task.getError());
        }
        return null;
      }
    });
  }


  private void checkPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
        PackageManager.PERMISSION_GRANTED) {

      queryContacts();
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
            READ_CONTACT_PERMISSION_REQUEST_CODE);
      }
    }
  }

  public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    if (requestCode == READ_CONTACT_PERMISSION_REQUEST_CODE
        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      queryContacts();
    }
  }

}
