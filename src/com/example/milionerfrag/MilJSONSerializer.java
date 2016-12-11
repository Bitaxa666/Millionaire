package com.example.milionerfrag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class MilJSONSerializer
{
	private Context mContext;
	private String mFilename;
	
	public MilJSONSerializer(Context mContext, String mFilename)
	{
		this.mContext = mContext;
		this.mFilename = mFilename;
	}
	/**
	 * Save Record in json formate
	 * @param recordG
	 * @throws JSONException
	 * @throws IOException
	 */
	public void saveYourRecord(ArrayList<YourRecordMil> recordG) throws JSONException,IOException
	{
		JSONArray array = new JSONArray();		
		for(YourRecordMil yRM : recordG)
			array.put(yRM.toJSON());		
		//запись в фаил
		Writer writer =null;		
		try{			
			OutputStream out=mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer=new OutputStreamWriter(out);
			writer.write(array.toString());
			writer.flush();			
			}
		finally{
				if(writer!=null)
					writer.close();					
				}		
	}
	/**
	 * Load params
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public ArrayList<YourRecordMil> loadRecord() throws IOException,JSONException
	{
		ArrayList<YourRecordMil> records =new ArrayList<YourRecordMil>();
		BufferedReader reader=null;
		try{
			InputStream in=mContext.openFileInput(mFilename);
			reader=new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString=new StringBuilder();
			String line=null;
			while((line=reader.readLine())!=null)
			{
				jsonString.append(line);
			}
			JSONArray array=(JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			for(int i=0;i<array.length();i++)
			{
				records.add(new YourRecordMil(array.getJSONObject(i)));
			}
			
		} catch(FileNotFoundException e)
		{
			
		} finally{
			if(reader!=null)
				reader.close();
		}
		return records;
		
	}
	
}
