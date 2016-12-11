package com.example.milionerfrag;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer 
{
	private MediaPlayer mPlayer;
	private int length=0;
	private boolean isPaused;
	
	public void prepareMediaPlayer()
	{
		if(mPlayer!=null)
		{
			mPlayer.release();
			mPlayer=null;
		}
	}
	/**
	 * Play sound
	 * @param c
	 */
	public void play(Context c)
	{		
		prepareMediaPlayer();
		mPlayer=MediaPlayer.create(c, R.raw.millioner);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{			
			@Override
			public void onCompletion(MediaPlayer mp)
			{
			prepareMediaPlayer();				
			}
		});
		mPlayer.start();
		mPlayer.setLooping(true);//повтор на песни
		}	
	/**
	 * Pause
	 */
	public void mPause()
	{		
		if(!isPaused)
		{
			mPlayer.pause();
			length=mPlayer.getCurrentPosition();
			isPaused=true;
			
		}else
			{
			mPlayer.seekTo(length);
			mPlayer.start();
			isPaused=false;
			}		
	}

}
