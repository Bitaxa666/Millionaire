package com.example.milionerfrag;


import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class Fragment1Mil extends Fragment 
{
	Button btn_time;
	TextView tv;
	TextView tv2;
	TextView tv3;	
	private int Y, Mon,D;
	private int index;
    private Calendar calendar;
	public static final int TICK_CODE=4444;
	public static final int TIMEOUT_CODE=5555;
	public static final int FEW_TIME_CODE=77945;
		
	public static MyHandlerCount myHandlerCount;		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v=inflater.inflate(R.layout.fragment_milion1, container, false);				
		///////////////////////////////////////////////////////////////////
		myHandlerCount=new MyHandlerCount(this);
		btn_time = (Button) v.findViewById(R.id.btnDatef1);		
		tv=(TextView) v.findViewById(R.id.tv_f1);
		tv2=(TextView) v.findViewById(R.id.tv2f1);
		tv3=(TextView) v.findViewById(R.id.textf1);		
		tv2.setText("API version: "+String.valueOf(Build.VERSION.SDK_INT));
		/////////////////////////////////////////////////////////////////////
		return v;
	}
	/**
	 * get the Date
	 * @return String with Date in our formate
	 */
	public String getCurrentDate()
	{
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int years=calendar.get(Calendar.YEAR);
        return String.format("%02d.%02d.%02d", day, month+1,years);
    }	
	
	@Override
	public void onResume()
	{	
		super.onResume();		
		MilionActivity mil = (MilionActivity)getActivity();
		tv.setText(mil.getTextQuestion());				
		Thread mtf1=new MyThreadCount();
		mtf1.start();				
		}

	@Override
	public void onDestroy() 
	{		
		super.onDestroy();
	}
		
}
	
class MyThreadCount extends Thread implements Runnable
{	
	private static int timeLeft=30;
	
	public static int getTimeLeft()
	{
		return timeLeft;
	}

	public static void setTimeLeft(int timeLeft)
	{
		MyThreadCount.timeLeft = timeLeft;
	}

	public MyThreadCount() 
	{
		MilionActivity.setTrueTime(false);
		this.timeLeft=30;		
	}

	@Override
	public void run()
	{			
		try {			
			while(timeLeft>0)
			{
			if(MilionActivity.isTrueTime()==true) return;				
			else {	
			//Log.d("TAG", ""+MilionActivity.isTrueTime());			
			Thread.sleep(1000);		
			Message msg=Fragment1Mil.myHandlerCount.obtainMessage();
			msg.what=Fragment1Mil.TICK_CODE;			
			msg.arg1=--timeLeft;			
			Log.d("TAG", ""+timeLeft);
			Fragment1Mil.myHandlerCount.sendMessage(msg);
			if(timeLeft<10)
				{
				msg=Fragment1Mil.myHandlerCount.obtainMessage();
				msg.what=Fragment1Mil.FEW_TIME_CODE;
				msg.arg1=timeLeft;
				Fragment1Mil.myHandlerCount.sendMessage(msg);
				}
			 if(timeLeft==0)
			 	{
				msg=Fragment1Mil.myHandlerCount.obtainMessage();
				msg.what=Fragment1Mil.TIMEOUT_CODE;
				msg.arg1=0;
				Fragment1Mil.myHandlerCount.sendMessage(msg);				
			 	}
			  }
			}		
		} catch (Exception e) 
		{			
			e.printStackTrace();
		}
		
	}
}
/**
 * nested class extends Handler
 * @author User
 *
 */
class MyHandlerCount extends Handler
{	
	WeakReference<Fragment1Mil> wr;	
	public MyHandlerCount(Fragment1Mil wr)
	{		
		this.wr = new WeakReference<Fragment1Mil>(wr);	
	}

	@Override
	public void handleMessage(Message msg)
	{	
		Fragment1Mil ma = wr.get();	
		MilionActivity mil = (MilionActivity) wr.get().getActivity();	
		switch(msg.what)//источник сообщения (поток который отправил)
		{
		case Fragment1Mil.TICK_CODE:
			ma.tv3.setText("You have " + msg.arg1 + "sec");			
			//((MyThreadCount)msg.obj).
			break;
		case Fragment1Mil.FEW_TIME_CODE:
			ma.tv3.setTextColor(Color.RED);
			ma.tv3.setText("Few time " + msg.arg1 + "sec");
			break;
		case Fragment1Mil.TIMEOUT_CODE:		
			ma.tv3.setText("Game over!!! " + msg.arg1+ "sec");
			mil.dialogForTheLoser();
			break;			
			}
		
		}


}
