package com.xbpsolutions.xbpdialer;

import android.app.Application;
import com.github.tamir7.contacts.Contacts;

public class DialerApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    Contacts.initialize(this);


  }
}
