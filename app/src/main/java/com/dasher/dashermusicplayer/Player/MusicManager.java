package com.dasher.dashermusicplayer.Player;

import java.util.ArrayList;
import com.dasher.dashermusicplayer.Models.SongList;
import android.content.Intent;
import android.content.Context;
import com.dasher.dashermusicplayer.Service.MusicService;
import com.dasher.dashermusicplayer.Utils.Constants;
import com.dasher.dashermusicplayer.Utils.StorageUtils;
import com.dasher.dashermusicplayer.Utils.LoadMusic;
import com.dasher.dashermusicplayer.MainActivity;

public class MusicManager
{
	private static ArrayList<SongList> mArrayList;
	private static int trackPos;
	private static String path;
	private static String title;
	private static String artist;
	private static Context mContext = MainActivity.getContextFromMainActivity();
	private static Thread thread;
	
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

	private static void createSong(){
		createFromLastPlayedData();
		
		Intent intent = new Intent(mContext,MusicService.class);
		intent.setAction(Constants.ACTION_CREATE);
		intent.putExtra(Constants.PATH_REFERENCE,path);
		intent.putExtra(Constants.TITLE_REFERENCE,title);
		mContext.startService(intent);
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
			saveData();
		}
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
	
}
