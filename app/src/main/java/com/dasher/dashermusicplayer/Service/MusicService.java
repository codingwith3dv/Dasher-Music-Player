package com.dasher.dashermusicplayer.Service;

import android.app.*;
import android.content.*;
import android.os.*;
import android.media.*;
import com.dasher.dashermusicplayer.Utils.*;
import android.widget.*;
import com.dasher.dashermusicplayer.Utils.Constants;
import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener
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
				playSongInService(path);
				break;
			default:
				break;
		}
		return START_NOT_STICKY;
	}

	private void playSongInService(String path)
	{
		// TODO: Implement this method
		if(player == null){
			player = new MediaPlayer();
		}

		player.setOnPreparedListener(this);
		
		try
		{
			player.reset();
			player.setDataSource(path);
		}catch (IllegalStateException e){}
		catch (IOException e){}
		catch (IllegalArgumentException e){}
		catch (SecurityException e){}
	
		player.prepareAsync();
	}

	
	
	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		if(player != null){
			player.release();
			player = null;
		}
	}

	@Override
	public void onTaskRemoved(Intent rootIntent)
	{
		// TODO: Implement this method
		super.onTaskRemoved(rootIntent);
	}

	@Override
	public void onPrepared(MediaPlayer p1)
	{
		// TODO: Implement this method
		player.start();
	}

}
