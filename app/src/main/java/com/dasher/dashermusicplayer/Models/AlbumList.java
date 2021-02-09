package com.dasher.dashermusicplayer.Models;

public class AlbumList {

	private long artistId;
	private String artistName;
	private int number_of_songs;
	
	public AlbumList(long artistidm,String artistNamem,int n){
		this.artistId = artistidm;
		this.artistName = artistNamem;
		this.number_of_songs = n;
	}

	public long getArtistId(){
		return artistId;
	}

	public String getArtistName(){
		return artistName;
	}

	public int getnumber_of_songs(){
		return number_of_songs;
	}
}
