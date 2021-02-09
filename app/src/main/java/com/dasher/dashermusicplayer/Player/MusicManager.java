package com.dasher.dashermusicplayer.Player;

import android.content.*;
import android.widget.*;
import com.dasher.dashermusicplayer.Models.*;
import com.dasher.dashermusicplayer.Service.*;
import com.dasher.dashermusicplayer.Utils.*;
import java.util.*;

public class MusicManager
{
	private Context mContext;
	private ArrayList<SongList> mArrayList;
	private int track_pos = 0;
	
	public MusicManager(Context context){
		this.mContext = context;
	}

	public void setArrayList(ArrayList<SongList> songList,int pos){
		this.mArrayList = songList;
		this.track_pos = pos;
		playSong();
	}

	private void playSong()
	{
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				Intent intent = new Intent(mContext,MusicService.class);
				intent.setAction(Constants.ACTION_PLAY);
				intent.putExtra(Constants.PATH_REFERENCE,mArrayList.get(track_pos).getPath());
				intent.putExtra(Constants.TITLE_REFERENCE,mArrayList.get(track_pos).getTitle());
				mContext.startService(intent);
			}
		}).start();
	}
}
