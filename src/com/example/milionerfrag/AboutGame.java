package com.example.milionerfrag;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

//activity with some info about author
public class AboutGame extends Activity 
{
	TextView textAboutAutor;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		textAboutAutor=(TextView) findViewById(R.id.text_about1);
		String data = "" +
		        "Немного о создателе даного проекта с помощью Linkify.\n" +
		        "\n" +
		        "URL: https://vk.com/bitaxa666 \n" +
		        "Email: barsik@yanesobaka.com \n" +
		        "Телефон: (095)-458-58-29 \n" +
		        "Адрес: Las-Vegas city, Tolking str.123 \n" +
		        "\n" ;
		 		//Create with Linkfi technology
		        if(textAboutAutor != null) {
		        	textAboutAutor.setText(data);
		            Linkify.addLinks(textAboutAutor, Linkify.ALL);
		        }
	}
	

}
