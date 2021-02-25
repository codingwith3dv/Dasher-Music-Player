package com.dasher.dashermusicplayer.Utils;
import android.content.*;
import android.database.*;
import android.net.*;
import android.provider.*;
import java.util.*;
import com.dasher.dashermusicplayer.Models.SongList;
import com.dasher.dashermusicplayer.Models.*;

public class LoadMusic {

	
	private static String titlem;
	private static String artistm;
	private static String pathm;

	private static long idm;
	private static String artistnamem;
	private static int numm;

	private static int i;

	private static Cursor cursor;

	private static ArrayList<SongList> mArrayList = new ArrayList<SongList>();
	private static ArrayList<AlbumList> artists = new ArrayList<AlbumList>();
	private static ArrayList<SongList> songsWithArtist = new ArrayList<SongList>();
	
	public static ArrayList<SongList> getAllData(final Context context){
		cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);

		if(cursor != null && cursor.moveToFirst()) {
			try{
				do{
					titlem = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
					artistm = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
					pathm = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

					if(artistm.contains("<")){
						artistm = "Unknown";
					}
					byte[] image = ImageRetriever.retrieveImageBytesFromPath(pathm);
					mArrayList.add(new SongList(titlem,artistm,pathm,image));
				}while(cursor.moveToNext());

			}catch(Exception e){}

			cursor.close();
		}
		
		return mArrayList;
	}

	public static ArrayList<AlbumList> getAlbumList(final Context context){
		String columns[] = { MediaStore.Audio.Artists._ID, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Artists.NUMBER_OF_TRACKS };
		
		cursor = context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,columns,null,null,null);
	
		if(cursor != null && cursor.moveToFirst()){
			do{
				idm = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
				artistnamem = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
				numm = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
				if(artistnamem.contains("<unknown>")){
					artistnamem = "Unknown";
				}
				artists.add(new AlbumList(idm,artistnamem,numm));
				
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		return artists;
	}

	public static ArrayList<SongList> getSongsFromArtistId(Context context,long id){
		String selection = MediaStore.Audio.Media.ARTIST_ID + "=" + id + " AND " + 
			MediaStore.Audio.Media.IS_MUSIC + "=1";
		cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,selection,null,null);
		
		if(cursor != null && cursor.moveToFirst()) {
			try{
				
				do{
					titlem = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
					artistm = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
					pathm = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

					if(artistm.contains("<")){
						artistm = "Unknown";
					}
					byte[] image = ImageRetriever.retrieveImageBytesFromPath(pathm);
					songsWithArtist.add(new SongList(titlem,artistm,pathm,image));
				}while(cursor.moveToNext());

			}catch(Exception e){}

			cursor.close();
		}
		return songsWithArtist;
	}
	
	public static void clearCachedDataAllSongs(){
		mArrayList = null;
		mArrayList = new ArrayList<SongList>();
	}

	public static void clearCachedDataArtists(){
		artists = null;
		artists = new ArrayList<AlbumList>();
	}
	
	public static void clearCachedDataArtistDetails(){
		songsWithArtist = null;
		songsWithArtist = new ArrayList<SongList>();
	}
}
