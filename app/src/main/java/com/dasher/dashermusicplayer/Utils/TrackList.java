package com.dasher.dashermusicplayer.Utils;

public class TrackList {

	private String title;
	private String artist;
	private int duration;
	private byte[] imageByte;
	
	public TrackList(String titlein,String artistin,int durationin){
		this.title = titlein;
		this.artist = artistin;
		this.duration = durationin;
	}

	public String getTitle( )
	{
		return title;
	}

	public String getArtist( )
	{
		return artist;
	}

	public int getDuration( )
	{
		return duration;
	}
}
