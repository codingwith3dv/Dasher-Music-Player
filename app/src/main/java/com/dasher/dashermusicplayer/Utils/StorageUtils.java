package com.dasher.dashermusicplayer.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.dasher.dashermusicplayer.Models.SongList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StorageUtils
{
	private static SharedPreferences sharedPrefs;
	private static SharedPreferences.Editor sharedPrefsEditor;
	
	public static void setLastPlayedArrayList(Context context,ArrayList<SongList> arrayList){
		sharedPrefs = context.getSharedPreferences("LAST_PLAYED_SONG_DATA", context.MODE_APPEND);
		sharedPrefsEditor = sharedPrefs.edit();
		Gson gson = new Gson();
		String data = gson.toJson(arrayList);
		sharedPrefsEditor.putString(Constants.LAST_PLAYED_SONG_LIST,data).commit();
	}

	public static ArrayList<SongList> getLastPlayedArrayList(Context context){
		sharedPrefs = context.getSharedPreferences("LAST_PLAYED_SONG_DATA",context.MODE_PRIVATE);
		Gson gson = new Gson();
		String data = sharedPrefs.getString(Constants.LAST_PLAYED_SONG_LIST,null);
		Type type = new TypeToken<ArrayList<SongList>>() {}.getType();
		ArrayList<SongList> returnList = gson.fromJson(data,type);
		if(returnList == null){
			returnList = LoadMusic.getAllData(context);
		}
		return returnList;
	}

	public static void setLastPlayedTrackPos(Context context, int trackPos){
		sharedPrefs = context.getSharedPreferences("LAST_PLAYED_SONG_DATA", context.MODE_APPEND);
		sharedPrefsEditor = sharedPrefs.edit();
		sharedPrefsEditor.putInt(Constants.LAST_PLAYED_TRACK_POS,trackPos).commit();
	}

	public static int getLastPlayedTrackPos(Context context){
		return context.getSharedPreferences("LAST_PLAYED_SONG_DATA",context.MODE_PRIVATE).getInt(Constants.LAST_PLAYED_TRACK_POS,0);
	}

	public static void setLastPlayedSongPos(Context context,int pos){
		sharedPrefs = context.getSharedPreferences("LAST_PLAYED_SONG_DATA", context.MODE_APPEND);
		sharedPrefsEditor = sharedPrefs.edit();
		sharedPrefsEditor.putInt(Constants.LAST_PLAYED_SONG_POS,pos).commit();
	}

	public static int getLastPlayedSongPos(Context context){
		return context.getSharedPreferences("LAST_PLAYED_SONG_DATA",context.MODE_PRIVATE).getInt(Constants.LAST_PLAYED_SONG_POS,0);
	}
}
