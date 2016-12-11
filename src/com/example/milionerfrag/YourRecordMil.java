package com.example.milionerfrag;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class YourRecordMil 
{
	private static final String JSON_ID="id";
	private static final String JSON_TITLE="title";
	private static final String JSON_DATE="date";
	/*
	 * переменные для значения рекордов:mId,mTitle,mDate
	 */
	private UUID mId;
	private String mTitle;
	private Date mDate;
	
	public YourRecordMil(String mTitle, Date mDate) 
	{
		this.mId=UUID.randomUUID();
		this.mTitle = mTitle;
		this.mDate = mDate;
	}	
	/**
	 * метод для востановления данных с json файла
	 * @param json
	 * @throws JSONException
	 */
	public YourRecordMil(JSONObject json) throws JSONException
	{
		mId=UUID.fromString(json.getString(JSON_ID));
		mTitle=json.getString(JSON_TITLE);		
		mDate=new Date(json.getLong(JSON_DATE));
	}
	/**
	 * метод для метод для добавление сохраняемых значений
	 * @return массив сохраненных параметров
	 * @throws JSONException
	 */
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json=new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);		
		json.put(JSON_DATE,mDate.getTime());
		return json;		
	}
	
	public String getmTitle()
	{
		return mTitle;
	}
	
	public Date getmDate()
	{
		return mDate;
	}
	/**
	 * Строка отображения данных результатов
	 */
	@Override
	public String toString() {
		return "Score: " + mTitle + ", Date="
				+ mDate;
	}
	
	
	

}
