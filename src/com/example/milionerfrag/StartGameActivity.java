package com.example.milionerfrag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartGameActivity  extends Activity
{
	Button btn1,btn2,btn3;
	Intent intent;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_start);
	        
	        btn1=(Button) findViewById(R.id.btn_start);
	        btn2=(Button) findViewById(R.id.btn_record);
	        btn3=(Button) findViewById(R.id.btn_finish);
	        btn1.setOnClickListener(new OnClickListener() 
	        {				
				@Override
				public void onClick(View v)
				{
					//start activity-new game
					intent=new Intent(StartGameActivity.this,MilionActivity.class);
		        	startActivity(intent);
					
				}
			});
	        btn2.setOnClickListener(new OnClickListener()
	        {				
				@Override
				public void onClick(View v) 
				{
					//start activity-recordGame
					intent=new Intent(StartGameActivity.this,RecordGameClass.class);
		        	startActivity(intent);					
				}
			});
	        
	        btn3.setOnClickListener(new OnClickListener()
	        {				
				@Override
				public void onClick(View v)
				{
					//exit!
					finish();
					
				}
			});
	 }	 

}
