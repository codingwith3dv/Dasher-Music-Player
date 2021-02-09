package com.dasher.dashermusicplayer.Models;
import java.io.*;

public class SongList implements Serializable{

	private String title;
	private String artist;
	private String path;
	private byte[] image;
	
	public SongList(String titlem,String artistm,String pathm,byte[] imagem) {
		this.title = titlem;
		this.artist = artistm;
		this.path = pathm;
		this.image = imagem;
	}

	public String getTitle( )
	{
		return title;
	}

	public String getArtist( )
	{
		return artist;
	}

	public String getPath( )
	{
		return path;
	}

	public byte[] getImage(){
		return image;
	}
}
