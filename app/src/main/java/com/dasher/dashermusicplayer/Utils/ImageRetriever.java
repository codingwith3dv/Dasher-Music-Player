package com.dasher.dashermusicplayer.Utils;

import android.media.*;

public class ImageRetriever
 {

	public byte[] retrieveImageBytesFromPath(String contentPath){
		byte[] imageBytes = null;
	
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(contentPath);
		try{
			imageBytes = mmr.getEmbeddedPicture();
			mmr.release();
		}catch(Exception e){
			return null;
		}
		
		return imageBytes;
	}
}
