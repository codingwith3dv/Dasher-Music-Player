package com.dasher.dashermusicplayer.Player;

import java.util.ArrayList;
import com.dasher.dashermusicplayer.Models.SongList;
import android.content.Intent;
import android.content.Context;
import com.dasher.dashermusicplayer.Service.MusicService;
import com.dasher.dashermusicplayer.Utils.Constants;

public class MusicManager
{
	private static ArrayList<SongList> mArrayList;
	private static int trackPos;
	private static String path;
	private static String title;
	private static String artist;
	private static Context mContext;
	
	public static void setArrayList(Context context,ArrayList<SongList> arrayList,int pos){
		mArrayList = arrayList;
		trackPos = pos;
		mContext = context;
		path = mArrayList.get(trackPos).getPath();
		title = mArrayList.get(trackPos).getTitle();
		artist = mArrayList.get(trackPos).getArtist();
		createSong();
		playSong();
	}

	private static void createSong(){
		Intent intent = new Intent(mContext,MusicService.class);
		intent.setAction(Constants.ACTION_CREATE);
		intent.putExtra(Constants.PATH_REFERENCE,path);
		intent.putExtra(Constants.TITLE_REFERENCE,title);
		mContext.startService(intent);
	}

	private static void playSong(){
		Intent intent = new Intent(mContext,MusicService.class);
		intent.setAction(Constants.ACTION_PLAY);
		mContext.startService(intent);
	}

	public static void playNext()
	{
		// TODO: Implement this method
		trackPos++;
		if(trackPos >= mArrayList.size()){
			trackPos = 0;
		}
		path = mArrayList.get(trackPos).getPath();
		title = mArrayList.get(trackPos).getTitle();
		artist = mArrayList.get(trackPos).getArtist();
		createSong();
		playSong();
	}
	
}
