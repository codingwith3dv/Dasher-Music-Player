package com.dasher.dashermusicplayer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import com.dasher.dashermusicplayer.Models.SongList;
import java.io.IOException;
import java.util.ArrayList;
import com.dasher.dashermusicplayer.Player.MusicManager;
import com.dasher.dashermusicplayer.Utils.Constants;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener
{

	private MediaPlayer player;
	
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
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
		String action = intent.getAction();
		switch(action){
			case Constants.ACTION_CREATE:
				String path = intent.getStringExtra(Constants.PATH_REFERENCE);
				String title = intent.getStringExtra(Constants.TITLE_REFERENCE);
				playSongInService(path);
				break;
			case Constants.ACTION_PLAY:
				startSong();
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
		player.setOnCompletionListener(this);
		
		try
		{
			player.reset();
			player.setDataSource(path);
			player.prepare();
		}catch (IllegalStateException e){}
		catch (IOException e){}
		catch (IllegalArgumentException e){}
		catch (SecurityException e){}
	}

	private void startSong(){
		if(player == null)return;
		player.start();
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
	public void onPrepared(MediaPlayer p1)
	{
		// TODO: Implement this method
		
	}

	@Override
	public void onCompletion(MediaPlayer p1)
	{
		// TODO: Implement this method	
		MusicManager.playNext();
	}

}
