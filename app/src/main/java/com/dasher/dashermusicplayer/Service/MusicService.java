package com.dasher.dashermusicplayer.Service;

import android.app.*;
import android.content.*;
import android.os.*;
import android.media.*;
import com.dasher.dashermusicplayer.Utils.*;
import android.widget.*;
import com.dasher.dashermusicplayer.Utils.Constants;

public class MusicService extends Service
{

	MediaPlayer player;
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		//setupMusicPlayer1();
		return null;
	}

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		player = new MediaPlayer();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		String intentAction = intent.getAction();
		switch(intentAction){
			case Constants.ACTION_PLAY:
				String path = intent.getStringExtra(Constants.PATH_REFERENCE);
				String title = intent.getStringExtra(Constants.TITLE_REFERENCE);
				Toast.makeText(getApplicationContext(),title + "\n" + path,2000).show();
				break;
			default:
				break;
		}
		return START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
	}

	@Override
	public void onTaskRemoved(Intent rootIntent)
	{
		// TODO: Implement this method
		super.onTaskRemoved(rootIntent);
	}

}
