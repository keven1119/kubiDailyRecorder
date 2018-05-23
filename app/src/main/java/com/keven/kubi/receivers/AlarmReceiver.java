package com.keven.kubi.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.keven.kubi.activity.WriteStatisActivity;

public class AlarmReceiver extends BroadcastReceiver {
  public AlarmReceiver() {
  }
  @Override
  public void onReceive(Context context, Intent intent) {
    if("com.example.g150825_android28.RING".equals(intent.getAction())){
      //跳转到Activity
      Intent intent1=new Intent(context,WriteStatisActivity.class);
      //设置标志位(Flag)
      intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent1);
    }
  }
}