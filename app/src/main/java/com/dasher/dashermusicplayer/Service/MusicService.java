package com.dasher.dashermusicplayer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import com.dasher.dashermusicplayer.MainActivity;
import com.dasher.dashermusicplayer.Player.MusicManager;
import com.dasher.dashermusicplayer.Utils.Constants;
import java.io.IOException;
import android.widget.Toast;
import com.dasher.dashermusicplayer.Utils.StorageUtils;

public class MusicService extends Service implements
				MediaPlayer.OnPreparedListener,
				MediaPlayer.OnCompletionListener,
				MediaPlayer.OnErrorListener{

	private static MediaPlayer player;
	private static AudioManager audioManager;
	
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
		if(audioManager == null){
			//audioManager = (AudioManager) MusicManager.mContext.getSystemService(MusicManager.mContext.AUDIO_SERVICE);
		}
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
			case Constants.ACTION_PAUSE:
				pause();
				break;
			case Constants.ACTION_SEEK:
				seekTo(intent.getIntExtra(Constants.SEEK_REFERENCE,0));
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
		player.setOnErrorListener(this);
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		try
		{
			player.reset();
			player.setDataSource(path);
			player.prepare();
		}catch (IllegalStateException e){}
		catch (IOException e){}
		catch (IllegalArgumentException e){}
		catch (SecurityException e){}
	
		MainActivity.musicInfo.getDur(player.getDuration());
		
		
	}

	private void startSong(){
		if(player == null)return;
		//if(requestAudioFocus())
		player.start();
		MainActivity.musicInfo.isPlaying(player.isPlaying());
	}

	public static boolean isPlaying()
	{
		// TODO: Implement this method
		if(player != null){
			return player.isPlaying();
		}else{
			return false;
		}
	}

	private void pause(){
		if(player != null){
			player.pause();
			MainActivity.musicInfo.isPlaying(player.isPlaying());
			StorageUtils.setLastPlayedSongPos(MainActivity.getContextFromMainActivity(), player.getCurrentPosition());
		}
	}
	
	public static void seekTo(int seekPos){
		if(player != null) 
			player.seekTo(seekPos);
		
		StorageUtils.setLastPlayedSongPos(MainActivity.getContextFromMainActivity(),seekPos);
	}

	public static int getCurrentPos()
	{
		if(player != null) return player.getCurrentPosition();
		return 0;
	}

	public static int getMax(){
		if(player != null) return player.getDuration();
		return 0;
	}
	
	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		if(player != null){
			StorageUtils.setLastPlayedSongPos(MainActivity.getContextFromMainActivity(), player.getCurrentPosition());
			player.release();
			player = null;
		}
	}

	@Override
	public void onTaskRemoved(Intent rootIntent)
	{
		// TODO: Implement this method
		super.onTaskRemoved(rootIntent);
		StorageUtils.setLastPlayedSongPos(MainActivity.getContextFromMainActivity(), player.getCurrentPosition());
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

	@Override
	public boolean onError(MediaPlayer p1, int p2, int p3)
	{
		// TODO: Implement this method
		if(player != null){
			player.reset();
			MusicManager.playNext();
		}
		return false;
	}

	private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
	{
		@Override
		public void onAudioFocusChange(int p1)
		{
			switch(p1){
				case AudioManager.AUDIOFOCUS_GAIN:
					startSong();
					setVolume(1.0f,1.0f);
					break;
				case AudioManager.AUDIOFOCUS_LOSS:
					Toast.makeText(getApplicationContext(),"AudioFocus Lost", 2000).show();
					pause();
					break;
			}
		}
	};

	@Deprecated
	private boolean requestAudioFocus(){
		/*if(audioManager.
			requestAudioFocus(
				audioFocusChangeListener,
				AudioManager.STREAM_MUSIC,
				AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
			) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
		{
			return true;
		}*/
		return false;
	}

	private static void setVolume(float left, float right){
		player.setVolume(left,right);
	}
}
