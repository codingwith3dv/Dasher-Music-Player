package com.dasher.dashermusicplayer.Utils;
import android.content.*;
import android.database.*;
import android.net.*;
import android.provider.*;
import java.util.*;
import com.dasher.dashermusicplayer.Models.SongList;
import com.dasher.dashermusicplayer.Models.*;

public class LoadMusic {

	public static ArrayList<SongList> getAllData(final Context context){
	
		final ArrayList<SongList> mArrayList = new ArrayList<>();
	
		Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(contentUri,null,null,null,null);
		ImageRetriever ir = new ImageRetriever();

		if(cursor != null && cursor.moveToFirst()) {
			try{
				int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
				int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
				int path = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

				do{
					String titlem = cursor.getString(title);
					String artistm = cursor.getString(artist);
					String pathm = cursor.getString(path);

					if(artistm.contains("<")){
						artistm = "Unknown";
					}
					byte[] image = ir.retrieveImageBytesFromPath(pathm);
					mArrayList.add(new SongList(titlem,artistm,pathm,image));
				}while(cursor.moveToNext());

			}catch(Exception e){}

			cursor.close();
		}
		
		return mArrayList;
	}

	public ArrayList<AlbumList> getAlbumList(final Context context){
		final ArrayList<AlbumList> artists = new ArrayList<>();

		Uri contentUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
		String id = MediaStore.Audio.Artists._ID;
		String artistname = MediaStore.Audio.Artists.ARTIST;
		String num = MediaStore.Audio.Artists.NUMBER_OF_TRACKS;
	
		String columns[] = { id, artistname, num };
		Cursor cursor = context.getContentResolver().query(contentUri,columns,null,null,null);
	
		if(cursor != null && cursor.moveToFirst()){
			do{
				long idm = cursor.getLong(cursor.getColumnIndex(id));
				String artistnamem = cursor.getString(cursor.getColumnIndex(artistname));
				int numm = cursor.getInt(cursor.getColumnIndex(num));
				if(artistnamem.contains("<unknown>")){
					artistnamem = "Unknown";
				}
				artists.add(new AlbumList(idm,artistnamem,numm));
				
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		return artists;
	}

	public ArrayList<SongList> getSongsFromArtistId(Context context,long id){
		ArrayList<SongList> songsWithArtist = new ArrayList<SongList>();
		String selection = MediaStore.Audio.Media.ARTIST_ID + "=" + id + " AND " + 
			MediaStore.Audio.Media.IS_MUSIC + "=1";
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,selection,null,null);
		ImageRetriever ir = new ImageRetriever();

		if(cursor != null && cursor.moveToFirst()) {
			try{
				int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
				int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
				int path = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);

				do{
					String titlem = cursor.getString(title);
					String artistm = cursor.getString(artist);
					String pathm = cursor.getString(path);

					if(artistm.contains("<")){
						artistm = "Unknown";
					}
					byte[] image = ir.retrieveImageBytesFromPath(pathm);
					songsWithArtist.add(new SongList(titlem,artistm,pathm,image));
				}while(cursor.moveToNext());

			}catch(Exception e){}

			cursor.close();
		}
		return songsWithArtist;
	}
}
