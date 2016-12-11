package com.example.milionerfrag;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Fragment2Mil extends Fragment implements OnClickListener
{
	public static final int COUNT_QUESTION_MIL=7;
	/*
	 * constants for Handler
	 */
	public static final int NOTIF_CODE=1111;
	public static final int CORRECT_CODE=2222;
	public static final int INCORRECT_CODE=3333;
	public static final int VALUE_OF_QUESTION=8;
	private static final String TAG = "MilionQuest";
	/*
	 * qList - list of question
	 * mQuest-random number
	 */
	private QuestionList qList;
	private int mQuest;
	//Handler for a pause time
	public static MyHandler myHandler;	
		
	 Button btn1;
	 Button btn2;
	 Button btn3;
	 Button btn4;
	public Button[] buttons;
	 	
//	MilionActivity activity = (MilionActivity) getActivity();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment2_question, container, false);
		
		btn1 = (Button) v.findViewById(R.id.btn1_frag2);
		btn2 = (Button) v.findViewById(R.id.btn2_frag2);
		btn3 = (Button) v.findViewById(R.id.btn3_frag2);
		btn4 = (Button) v.findViewById(R.id.btn4_frag2);
			
		buttons = new Button[4];
		buttons[0]=btn1;
		buttons[1]=btn2;
		buttons[2]=btn3;
		buttons[3]=btn4;
		for(int i=0;i<buttons.length;i++)
		{
			buttons[i].setOnClickListener(this);
		}
		
		chooseAnswer();
		return v;
	}
	
	public void chooseAnswer()
	{
		ArrayList<String> q = new ArrayList<String>(4);		
		for(int i=0;i<4;i++)
		{
			q.add( ((MilionActivity) getActivity() ).answerTrueQuestion(i+1));
		}
		//shuffle values in collections
		Collections.shuffle(q);		
		for(int i=0;i<4;i++)
		{
			buttons[i].setText(q.get(i));
		}			
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		qList=new QuestionList();
		Random rnd=new Random();
		mQuest=rnd.nextInt(5);
		myHandler = new MyHandler(this);
	}
	
	@Override
	public void onClick(View v) 
	{			
			((Button)v).setBackgroundResource(R.drawable.milioner_button_ans_yelow);
			((Button)v).setTextColor(Color.BLACK);
			int btnNum=0;
			if(((Button)v).equals(buttons[1])) btnNum=1;
			else if(((Button)v).equals(buttons[2])) btnNum=2;
			else if(((Button)v).equals(buttons[3])) btnNum=3;
			
			MyThreadMil mt=new MyThreadMil(v,btnNum);
			new Thread(mt).start();
			}
		
	}
	/**
	 * new thread for a Handler
	 * @author User
	 *
	 */
	class MyThreadMil implements Runnable
	{	
		Button btn;
		int btnNum;
		public MyThreadMil(View v, int btnNum) 
		{
			btn = (Button) v;
			this.btnNum=btnNum;			
			MilionActivity.setTrueTime(true) ;
		}

		@Override
		public void run()
		{
			
			try {				
				Message msg=Fragment2Mil.myHandler.obtainMessage();
				msg.what=Fragment2Mil.NOTIF_CODE;				
				msg.obj=btn.getText().toString();	
				msg.arg1=btnNum;//номер нажатой кнопки			
				Fragment2Mil.myHandler.sendMessageDelayed(msg, 3000);				
			} catch (Exception e)
			{				
				e.printStackTrace();
			}
		}

	}
	
	class MyHandler extends Handler
    {		
		private QuestionList qList;
    	WeakReference<Fragment2Mil> wr;
    	
		public MyHandler(Fragment2Mil wr)
		{
			this.wr = new WeakReference<Fragment2Mil>(wr);
			qList=new QuestionList();
		}
		/**
		 * accepted message
		 */
		@Override
		public void handleMessage(Message msg)
		{		
			MilionActivity mil = (MilionActivity) wr.get().getActivity();			
			int index=mil.getIndexOfQuest();			
			Fragment2Mil ma = wr.get();//вытаскиваем из слабой ссылки полную
			if(ma==null) return;
			int questionCounter=1;
			int btnNum=msg.arg1;//ћассив кнопок на какую воздействовать buttons[btnNum].setBack
			
			switch(msg.what)//источник сообщени€ (поток который отправил)
			{
			case Fragment2Mil.NOTIF_CODE:				
					Context c = mil.getBaseContext();				
					if(msg.obj.equals(qList.getmQuestion()[index].getStringTrueQuestion()))//getCorrectAnswer()
					{//правильный ответ										
						mil.setIndexOfQuest(++index);//сначала прибавили через сет а потом проверили						
						ma.buttons[btnNum].setBackgroundResource(R.drawable.milioner_button_ans_true);					
						//формирование нового ’ендлерра дл€ выдержки интриги при смене вопроса
						Message msgNext = Fragment2Mil.myHandler.obtainMessage();
						msgNext.what = Fragment2Mil.CORRECT_CODE;
						Fragment2Mil.myHandler.sendMessageDelayed(msgNext, 1500);
					}
					else
					{							
						ma.buttons[btnNum].setBackgroundResource(R.drawable.milioner_button_ans_false);
						Message msgNext = Fragment2Mil.myHandler.obtainMessage();
						msgNext.what = Fragment2Mil.INCORRECT_CODE;
						Fragment2Mil.myHandler.sendMessageDelayed(msgNext, 1500);						
					}
				
					break;
				case Fragment2Mil.CORRECT_CODE:
					if(mil.getIndexOfQuest()<Fragment2Mil.VALUE_OF_QUESTION)//проверка на нужное количество правельных ответов!
					{
					mil.trueTextChanged();
					mil.trueAnswerChanged();
					mil.ChengedResultInGame();					
					new Thread(new MyThreadCount()).start();
					}
					else
					{
						mil.dialogForWinner();
					}
					
					break;
				case Fragment2Mil.INCORRECT_CODE:
					mil.dialogForTheLoser();
					break;				
				}
			
		}  

}
