package com.example.milionerfrag;

import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class NotificationServices extends Service
{
	//notification service
	NotificationManager nm;
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		//service with some reclams in Game
		nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		try{			
			new Thread(new Runnable() {				
				@Override
				public void run() {
					try
					{
						//waiting 5 min and send the reclams
						TimeUnit.SECONDS.sleep(300);
						sendNotification();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}					
				}
			}).start();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}				
		return super.onStartCommand(intent, flags, startId);
	}
	//create the notification
	private void sendNotification()
	{
		Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://rozetka.com.ua"));
		intent.putExtra(MilionActivity.NOTIFICATION_SERVICE, "555");
		PendingIntent pi=PendingIntent.getActivity(this, 0, intent, 0);		
		
		Notification notif=new Notification.Builder(getBaseContext())
		.setContentText("Спешите купить по выгодным ценам")
		.setSmallIcon(R.drawable.roz_reklama)
		.setWhen(System.currentTimeMillis())
		.setContentTitle("Salary")
		.setTicker("Спешите непропустите")
		.setContentIntent(pi)
		.build();
		notif.flags|=Notification.FLAG_AUTO_CANCEL;
		notif.defaults=Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;
		nm.notify(1,notif);	
	}
	
	@Override
	public IBinder onBind(Intent intent) 
	{		
		return null;
	}

}
