package com.dasher.dashermusicplayer.Player;

import android.content.Context;
import android.content.Intent;
import com.dasher.dashermusicplayer.Models.SongList;
import com.dasher.dashermusicplayer.Service.MusicService;
import com.dasher.dashermusicplayer.Utils.Constants;
import java.util.ArrayList;

public class MusicManager
{
	private Context mContext;
	private ArrayList<SongList> mArrayList;
	private int track_pos = 0;
	private String path;
	private String title;
	
	public MusicManager(Context context){
		this.mContext = context;
	}

	public void setArrayList(ArrayList<SongList> songList,int pos){
		this.mArrayList = songList;
		this.track_pos = pos;
		this.path = this.mArrayList.get(pos).getPath();
		this.title = this.mArrayList.get(pos).getTitle();
		setupSong();
	}

	private void setupSong()
	{
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				Intent intent = new Intent(mContext,MusicService.class);
				intent.setAction(Constants.ACTION_CREATE);
				intent.putExtra(Constants.PATH_REFERENCE,path);
				intent.putExtra(Constants.TITLE_REFERENCE,title);
				mContext.startService(intent);
			}
		}).start();
	}

	private void playSong(){
		new Thread(new Runnable(){
			@Override
			public void run(){
				Intent intent = new Intent(mContext,MusicService.class);
				intent.setAction(Constants.ACTION_PLAY);
				mContext.startService(intent);
			}
		}).start();
	}

}
