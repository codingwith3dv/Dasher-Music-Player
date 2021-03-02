package com.dasher.dashermusicplayer.Player;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;
import com.dasher.dashermusicplayer.MainActivity;
import com.dasher.dashermusicplayer.Models.SongList;
import com.dasher.dashermusicplayer.Service.MusicService;
import com.dasher.dashermusicplayer.Utils.Constants;
import com.dasher.dashermusicplayer.Utils.StorageUtils;
import java.util.ArrayList;
import android.media.AudioAttributes;

public class MusicManager
{
	private static ArrayList<SongList> mArrayList;
	private static int trackPos;
	private static String path;
	private static String title;
	private static String artist;
	public static Context mContext = MainActivity.getContextFromMainActivity();
	
	public static void setArrayList(Context context,ArrayList<SongList> arrayList,int pos){
		mArrayList = arrayList;
		trackPos = pos;
		path = mArrayList.get(trackPos).getPath();
		title = mArrayList.get(trackPos).getTitle();
		artist = mArrayList.get(trackPos).getArtist();
		createSong();
		playSong();
		saveData();
	}

	public static void setContext(Context context){
		
	}

	public static void createSong(){
		if(mArrayList == null){
			createFromLastPlayedData();
			return;
		}
		
		Intent intent = new Intent(mContext,MusicService.class);
		intent.setAction(Constants.ACTION_CREATE);
		intent.putExtra(Constants.PATH_REFERENCE,path);
		intent.putExtra(Constants.TITLE_REFERENCE,title);
		Toast.makeText(mContext,"Created song " + title + "\n" + path,2000).show();
		mContext.startService(intent);
		MainActivity.setCurrentPlayingSongData();
	}

	public static void seekSongTo(int pos){
		Intent intent = new Intent(mContext,MusicService.class);
		intent.setAction(Constants.ACTION_SEEK);
		intent.putExtra(Constants.SEEK_REFERENCE,pos);
		mContext.startService(intent);
		StorageUtils.setLastPlayedSongPos(mContext,pos);
	}
	
	public static void playSong(){
		createFromLastPlayedData();
		
		Intent intent = new Intent(mContext,MusicService.class);
		intent.setAction(Constants.ACTION_PLAY);
		mContext.startService(intent);
	}

	public static void pauseSong(){
		Intent intent = new Intent(mContext,MusicService.class);
		intent.setAction(Constants.ACTION_PAUSE);
		mContext.startService(intent);
	}

	public static void playNext()
	{
		// TODO: Implement this method
		createFromLastPlayedData();
		trackPos++;
		if(trackPos >= mArrayList.size()){
			trackPos = 0;
		}
		saveData();
		path = mArrayList.get(trackPos).getPath();
		title = mArrayList.get(trackPos).getTitle();
		artist = mArrayList.get(trackPos).getArtist();
		createSong();
		playSong();
	
	}
	
	private static void createFromLastPlayedData(){
		if(mArrayList == null){ 
			mArrayList = StorageUtils.getLastPlayedArrayList(mContext);
			trackPos = StorageUtils.getLastPlayedTrackPos(mContext);
			path = mArrayList.get(trackPos).getPath();
			title = mArrayList.get(trackPos).getTitle();
			artist = mArrayList.get(trackPos).getArtist();
			createSong();
		}
	}

	private static void saveData(){
		if(mArrayList != null){
			new Thread(new Runnable(){
				@Override
				public void run()
				{
					// TODO: Implement this method
					StorageUtils.setLastPlayedArrayList(mContext,mArrayList);
					StorageUtils.setLastPlayedTrackPos(mContext,trackPos);
				}
			}).start();
		}
	}

	public static byte[] getImage(){
		return mArrayList.get(trackPos).getImage();
	}

	public static String getSongTitle(){
		return mArrayList.get(trackPos).getTitle();
	}

	
}
