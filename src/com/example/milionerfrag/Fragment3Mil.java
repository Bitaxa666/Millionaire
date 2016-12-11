package com.example.milionerfrag;

import java.util.List;
import java.util.Random;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Fragment3Mil extends Fragment 
{
	//constants
	private static final int REQUEST_CONTACT=2;
	
	Button btn1,btn2,btn3_sound;
	TextView tv_f3,tv_s1,tv_s2,tv_s3,tv_s4,tv_s5,tv_s6,tv_s7;
	AudioPlayer audio=new AudioPlayer();
	int k=1;	
	private QuestionList qList;
	private MilionActivity mil;
	//array for btn
	private Button[] button50_50;
	//random values
	private int value50_50;
	private boolean one50_50;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View v=inflater.inflate(R.layout.fragment3_info, container, false);
		
		qList=new QuestionList();
		mil = (MilionActivity)getActivity();
		Random rnd=new Random();
		value50_50=rnd.nextInt(4);	
		one50_50=false;		
		
		btn1 =  (Button) v.findViewById(R.id.btnf3_50);		
		btn2= (Button) v.findViewById(R.id.btnf3_call);	
		btn3_sound=(Button) v.findViewById(R.id.btnf3_sound);
		
		btn2.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				//intent for a new activity with contacts in a telephone
				Intent i=new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
				PackageManager pm=getActivity().getPackageManager();
				List<ResolveInfo> activities=pm.queryIntentActivities(i, 0);				
				if(activities.size()>0)
				startActivityForResult(i, REQUEST_CONTACT);				
			}
		});
		
		btn3_sound.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{				
				k++;
				if( k % 2 == 0)
				{
				   audio.play(getActivity());
				   btn3_sound.setBackgroundResource(R.drawable.sound);
				} else
				{
				  audio.prepareMediaPlayer();
				  btn3_sound.setBackgroundResource(R.drawable.no_sound);
				}
				
			}
		});
		
		btn1.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v) 
			{
				
				if(!one50_50)
				{
					fiftyFifty();
					one50_50=true;
				}
				
			}
		});
		
		tv_s1=(TextView) v.findViewById(R.id.tv_f3_sum1);
		tv_s2=(TextView) v.findViewById(R.id.tv_f3_sum2);
		tv_s3=(TextView) v.findViewById(R.id.tv_f3_sum3);
		tv_s4=(TextView) v.findViewById(R.id.tv_f3_sum4);
		tv_s5=(TextView) v.findViewById(R.id.tv_f3_sum5);
		tv_s6=(TextView) v.findViewById(R.id.tv_f3_sum6);
		tv_s7=(TextView) v.findViewById(R.id.tv_f3_sum7);
		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{	
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void onDestroy()
	{		
		super.onDestroy();
		audio.prepareMediaPlayer();
	}
	public void fiftyFifty()
	{
		
		Fragment fragmentAnswerQuest=getFragmentManager().findFragmentById(R.id.frag2_answer);
		View btn1=fragmentAnswerQuest.getView().findViewById(R.id.btn1_frag2);
		View btn2=fragmentAnswerQuest.getView().findViewById(R.id.btn2_frag2);
		View btn3=fragmentAnswerQuest.getView().findViewById(R.id.btn3_frag2);
		View btn4=fragmentAnswerQuest.getView().findViewById(R.id.btn4_frag2);
		
		button50_50 = new Button[4];
		button50_50[0]=((Button)btn1);
		button50_50[1]=((Button)btn2);
		button50_50[2]=((Button)btn3);
		button50_50[3]=((Button)btn4);
		
		for(int i=0;i<button50_50.length;i++)
		{
			button50_50[value50_50].setTextColor(Color.GREEN);
		}
		
		if((qList.getmQuestion()[mil.getIndexOfQuest()].getStringTrueQuestion())==((Button)btn1).getText().toString())
			((Button)btn1).setTextColor(Color.GREEN);
		else if((qList.getmQuestion()[mil.getIndexOfQuest()].getStringTrueQuestion())==((Button)btn2).getText().toString())
			((Button)btn2).setTextColor(Color.GREEN);
		else if((qList.getmQuestion()[mil.getIndexOfQuest()].getStringTrueQuestion())==((Button)btn3).getText().toString())
			((Button)btn3).setTextColor(Color.GREEN);
		else 
			((Button)btn4).setTextColor(Color.GREEN);
	}	
}
