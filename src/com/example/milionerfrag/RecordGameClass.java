package com.example.milionerfrag;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RecordGameClass extends Activity
{	 
		  // имена атрибутов для Map
		  final String ATTRIBUTE_NAME_TEXT = "text";
		  final String ATTRIBUTE_NAME_TEXTDATE = "date";
		  final String ATTRIBUTE_NAME_IMAGE = "image";
		  
		  private ArrayList<YourRecordMil> mRecordList;
		  private MilJSONSerializer mSerializerList;
		  private Context mAppContextList;
		  private Date mDate;
		 
		  ListView lvSimple;		  
		
		 
		  /** Called when the activity is first created. */
		  public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.record_game_list);		    
		    
		   // Intent intentResult = getIntent();
		    //String putResult=intentResult.getStringExtra("RecordResult");/////??????????????????????????
		    //MilionActivity.RECORD_RES
		    mAppContextList=this;
		    mSerializerList=new MilJSONSerializer(mAppContextList, MilionActivity.FILENAME);
		    try
		    {
		    	mRecordList=mSerializerList.loadRecord();
		    	Log.d("TAG", " loading file is OK");
		    }catch (Exception ex)
		    {
		    	mRecordList=new ArrayList<YourRecordMil>();
		    	Log.d("TAG", "Error loading file", ex);
		    }
		    
		    
		     
		    String putResult="List of your new Record";
		    Log.d("TAG RES", putResult);
		    //YourRecordMil y1=new YourRecordMil(putResult,mDate=new Date());
		   // getActivity.addRecordMil(y1);
		    //ArrayList <String> listRes = new ArrayList<>();////////////////////////
		    //listRes.add(putResult);////////////////////////////////////////
		 
		    // массивы данных
		   // String[] texts = { "First record 123", "second record 98", "3-d 67"};
		  //  boolean[] checked = { true, false, false, true, false };
		    int img = R.drawable.ic_launcher;
		 
		    // упаковываем данные в понятную для адаптера структуру
		   // ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>( texts.length);
		    //ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>( listRes.size());//////
		    ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>( mRecordList.size());  
		    Map<String, Object> m;
		   // for (int i = 0; i < listRes.size(); i++) {
		    for (int i = 0; i < mRecordList.size(); i++) {
		      m = new HashMap<String, Object>();
		    // m.put(ATTRIBUTE_NAME_TEXT, listRes.get(i));		
		      m.put(ATTRIBUTE_NAME_TEXT, "You record is - " + mRecordList.get(i).getmTitle());
		      m.put(ATTRIBUTE_NAME_TEXTDATE, mRecordList.get(i).getmDate());
		      m.put(ATTRIBUTE_NAME_IMAGE, img);
		      data.add(m);
		    }
		 
		    // массив имен атрибутов, из которых будут читаться данные
		    String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_TEXTDATE, ATTRIBUTE_NAME_IMAGE };
		    // массив ID View-компонентов, в которые будут вставлять данные
		    int[] to = { R.id.list_record, R.id.list_date,  R.id.ivImg };
		 
		    // создаем адаптер
		    SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.list_item_record,
		        from, to);
		 
		    // определяем список и присваиваем ему адаптер
		    lvSimple = (ListView) findViewById(R.id.lvSimple);
		    lvSimple.setAdapter(sAdapter);
		  }
		  
		  public String showListRecord()
			{
			  String st = null;
				for(int i=0;i<mRecordList.size();i++)
				{
					Log.d("TAG", "RecordList " + mRecordList.get(i).toString());
					st= "RecordList " + mRecordList.get(i).toString();
					
				}
				return st;
			}
		
	
}






