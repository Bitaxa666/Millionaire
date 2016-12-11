package com.example.milionerfrag;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MilionActivity extends Activity 
{	
	/*
	 * константы для создание диалогов, TAG, имя файла для сохранения 
	 */
	private final int DIALOG_LOSER_EXIT=1;
	private final int DIALOG_WINNER_EXIT=64927;
	public static final String RECORD_RES="RecordResult";		
	public static final String FILENAME = "recordes.json";	
	
	/*
	 * переменные использованые в проэкте:
	 * mRecord- ArrayList for the record's value
	 * mSerializer - class for saving - mRecord
	 * mAppContext - context on rhis acrivity
	 * frag1Question, frag2Answer,frag3Info  - object reference	
	 * indexOfQuest - number of the question 
	 * cashInGame - total cash in game
	 * trueTime - one responce time
	 */
	private ArrayList<YourRecordMil> mRecord;
	private MilJSONSerializer mSerializer;
	private Context mAppContext;
	private Fragment1Mil frag1Question;
	private Fragment2Mil frag2Answer;
	private Fragment3Mil frag3Info;
	private FragmentTransaction ft;
	
	private int indexOfQuest;
	private int cashInGame;
	private static QuestionList qList;
	private String curQyes;
	private String answerQues;
	private boolean listAdd;
	private static boolean trueTime;
	
	private Date mDate;
	AudioPlayer audio;
	
	public static boolean isTrueTime() 
	{
		return trueTime;
	}
	public static void setTrueTime(boolean trueTime) 
	{
		MilionActivity.trueTime = trueTime;
	}	
	
	private int resultInGameBal;		
	public int getResultInGameBal()
	{
		return resultInGameBal;
	}
	
	public void dialogForTheLoser()
	{		
		Dialog dlg=null;
		dlg=onCreateDialog(DIALOG_LOSER_EXIT);
		if(dlg!=null) dlg.show();
	}
	
	public void dialogForWinner()
	{		
		Dialog dlg=null;
		dlg=onCreateDialog(DIALOG_WINNER_EXIT);
		if(dlg!=null) dlg.show();
	}
	/**
	 * save records in game
	 * @return
	 */
	public boolean saveRecord()
	{
		try{
			mSerializer.saveYourRecord(mRecord);			
			Toast.makeText(mAppContext, "Record saving in fileSystem", Toast.LENGTH_SHORT).show();
			return true;
		} catch (Exception ex)
		{
			Toast.makeText(mAppContext, "Record not saving in fileSystem", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	/**
	 * add records in game result
	 * @return
	 */
	public void addRecordMil(YourRecordMil yRM)
	{
		 mRecord.add(yRM);
	}	
	
	/**
	 * Create dialog for looser and Winner!
	 */
	public Dialog onCreateDialog(int id)
	{
		MilionActivity.setTrueTime(true) ;
		resultInGameBal=indexOfQuest*cashInGame;
		YourRecordMil y1=new YourRecordMil(""+resultInGameBal, mDate);
    	addRecordMil(y1);
    	//showList();
    	saveRecord();				
		if(id==DIALOG_LOSER_EXIT)
		{   //создаем диалог
			AlertDialog.Builder adB=new AlertDialog.Builder(MilionActivity.this);
			//заголовок
			adB.setTitle(R.string.dialog_title);
			//сообщение
			adB.setMessage(getString(R.string.results_game, indexOfQuest, resultInGameBal));
			//иконка
			adB.setIcon(android.R.drawable.ic_dialog_info);
			adB.setCancelable(false);
			//назначаем слушателя и действия для кнопок в диалоге			
			adB.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener()
			{				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					//выйти из игры
					finish();
				}
			});			
			return adB.create();
		}
		if(id==DIALOG_WINNER_EXIT)
		{
			AlertDialog.Builder winDialog=new AlertDialog.Builder(MilionActivity.this);
			//заголовок
			winDialog.setTitle(R.string.dialog_winner);
			//сообщение
			winDialog.setMessage(getString(R.string.winner_game)+frag1Question.getCurrentDate());
			//иконка
			winDialog.setIcon(android.R.drawable.ic_dialog_info);
			winDialog.setCancelable(false);
			//кнопка реакции на диалог
			winDialog.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener()
			{				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					//выйти из игры
					finish();
				}
			});
			
			return winDialog.create();
		}
		
		return null;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milion);
        //////////////////////////////////////////////////////////////        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        indexOfQuest=0;
    	cashInGame=100;    	
    	///////////////////////////////////////////////////////////////
    	mAppContext= getBaseContext();
    	mAppContext=this;
    	mSerializer=new MilJSONSerializer(mAppContext, FILENAME);
    	mDate=new Date();
    	mRecord=new ArrayList<YourRecordMil>();   	
    	
    	qList=new QuestionList();                
        frag1Question=new Fragment1Mil();    
        frag2Answer=new Fragment2Mil();
        frag3Info=new Fragment3Mil();
            
        ft = getFragmentManager().beginTransaction();
        try
        {
        	ft.replace(R.id.frag1_question, frag1Question);
        	ft.replace(R.id.frag2_answer, frag2Answer);
        	ft.replace(R.id.frag3_info, frag3Info);        	
        	ft.commit();
        }
        catch(Exception ex) {}
       startService(new Intent(this,NotificationServices.class));           
    }
    
	@Override
	protected void onResume() 
	{
		super.onResume();		
	}
	@Override
	protected void onDestroy()
	{		
		super.onDestroy();
		stopService(new Intent(getBaseContext(),NotificationServices.class));
		MilionActivity.setTrueTime(true) ;
	}
	
	@Override
	protected void onStop()
	{	
		super.onStop();
		listAdd=true;
	}
	
	public int getCashInGame() 
    {
		return cashInGame;
	}

	public void setCashInGame(int cashInGame) 
	{
		this.cashInGame = cashInGame;
	}

	public String getTextQuestion()
    {
    	curQyes=qList.getmQuestion()[indexOfQuest].getQuestion();
    	return curQyes;
    }
    
    public int getIndexOfQuest()
    {
		return indexOfQuest;
	}

	public void setIndexOfQuest(int indexOfQuest)
	{
		this.indexOfQuest = indexOfQuest;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {               
        getMenuInflater().inflate(R.menu.milion, menu);
        return true;
    }
	/**
	 * menu items
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {     	
    	Intent inten;
    	int id = item.getItemId();
        switch(id)
        {
        case R.id.meinitem1:        	
        	inten=new Intent(this,AboutGame.class);
        	startActivity(inten);
        	break;
        case R.id.mainitem2:
        	finish();        	
        	break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 
     * @param numberAnswer
     * @return true answer to a question
     */
    public String answerTrueQuestion(int numberAnswer)
    {
    	String answerTrue=null;
    	if(numberAnswer==1)
    	{
    		answerTrue=qList.getmQuestion()[getIndexOfQuest()].getAnswerQuestion1();
    	} else if(numberAnswer==2)
    	{
    		answerTrue=qList.getmQuestion()[getIndexOfQuest()].getAnswerQuestion2();
    	} else if(numberAnswer==3)
    	{
    		answerTrue=qList.getmQuestion()[getIndexOfQuest()].getAnswerQuestion3();
    	} else 
    	{
    		answerTrue=qList.getmQuestion()[getIndexOfQuest()].getAnswerQuestion4();
    	}     				
    	return answerTrue;
    }
    
	public String getCurQyes()
	{
		return curQyes;
	}
	
	public void setCurQyes(String curQyes) {
		this.curQyes = curQyes;
	}
	/**
	 * Changing question in a fragment 2
	 */
	public void trueTextChanged()
	{
		Fragment frag2 = getFragmentManager().findFragmentById(R.id.frag1_question);
		View tv = frag2.getView().findViewById(R.id.tv_f1);
		((TextView)tv).setText(getTextQuestion());		
		
	}
	/**
	 * function when the answer is true
	 */
	public void trueAnswerChanged()
	{
		Fragment fragAnswButton1=getFragmentManager().findFragmentById(R.id.frag2_answer);
		View btnAns1=fragAnswButton1.getView().findViewById(R.id.btn1_frag2);
		((Button)btnAns1).setText(qList.getmQuestion()[getIndexOfQuest()].getAnswerQuestion1());
		((Button)btnAns1).setBackgroundResource(R.drawable.mil_answer);
		((Button)btnAns1).setTextColor(Color.YELLOW);
		View btnAns2=fragAnswButton1.getView().findViewById(R.id.btn2_frag2);
		((Button)btnAns2).setText(qList.getmQuestion()[getIndexOfQuest()].getAnswerQuestion2());
		((Button)btnAns2).setBackgroundResource(R.drawable.mil_answer);
		((Button)btnAns2).setTextColor(Color.YELLOW);
		View btnAns3=fragAnswButton1.getView().findViewById(R.id.btn3_frag2);
		((Button)btnAns3).setText(qList.getmQuestion()[getIndexOfQuest()].getAnswerQuestion3());
		((Button)btnAns3).setBackgroundResource(R.drawable.mil_answer);
		((Button)btnAns3).setTextColor(Color.YELLOW);
		View btnAns4=fragAnswButton1.getView().findViewById(R.id.btn4_frag2);
		((Button)btnAns4).setText(qList.getmQuestion()[getIndexOfQuest()].getAnswerQuestion4());
		((Button)btnAns4).setBackgroundResource(R.drawable.mil_answer);
		((Button)btnAns4).setTextColor(Color.YELLOW);
	}	
	/**
	 * change color in TextView(fragment3)
	 */
	public void ChengedResultInGame()
	{
		Fragment fragResultInGame=getFragmentManager().findFragmentById(R.id.frag3_info);
		View tv_sum1=fragResultInGame.getView().findViewById(R.id.tv_f3_sum1);
		View tv_sum2=fragResultInGame.getView().findViewById(R.id.tv_f3_sum2);
		View tv_sum3=fragResultInGame.getView().findViewById(R.id.tv_f3_sum3);
		View tv_sum4=fragResultInGame.getView().findViewById(R.id.tv_f3_sum4);
		View tv_sum5=fragResultInGame.getView().findViewById(R.id.tv_f3_sum5);
		View tv_sum6=fragResultInGame.getView().findViewById(R.id.tv_f3_sum6);
		View tv_sum7=fragResultInGame.getView().findViewById(R.id.tv_f3_sum7);
		if(indexOfQuest==1)		
			((TextView)tv_sum1).setBackgroundColor(Color.GREEN);		
		else if(indexOfQuest==2)
			((TextView)tv_sum2).setBackgroundColor(Color.GREEN);			
		else if(indexOfQuest==3)
			((TextView)tv_sum3).setBackgroundColor(Color.GREEN);
		else if(indexOfQuest==4)
			((TextView)tv_sum4).setBackgroundColor(Color.GREEN);
		else if(indexOfQuest==5)
			((TextView)tv_sum5).setBackgroundColor(Color.GREEN);	
		else if(indexOfQuest==6)
			((TextView)tv_sum6).setBackgroundColor(Color.GREEN);
		else if(indexOfQuest==7)
			((TextView)tv_sum7).setBackgroundColor(Color.RED);	
		else{
			((TextView)tv_sum1).setBackgroundColor(Color.YELLOW);
			((TextView)tv_sum2).setBackgroundColor(Color.YELLOW);
			((TextView)tv_sum3).setBackgroundColor(Color.YELLOW);
			((TextView)tv_sum4).setBackgroundColor(Color.YELLOW);
			((TextView)tv_sum5).setBackgroundColor(Color.YELLOW);
			((TextView)tv_sum6).setBackgroundColor(Color.YELLOW);
			((TextView)tv_sum7).setBackgroundColor(Color.YELLOW);
		}	
	}
		
}
