package com.dasher.dashermusicplayer.Utils;
import android.support.v4.app.*;
import com.dasher.dashermusicplayer.Fragments.*;

public  class CustomPagerAdapter extends FragmentPagerAdapter
{
	Fragment fragment;

	private int num;
	
	public CustomPagerAdapter(FragmentManager fm,int n){
		super(fm);
		this.num = n;
	}

	@Override
	public int getCount( )
	{
		// TODO: Implement this method
		return num;
	}

	@Override
	public Fragment getItem( int p1 )
	{
		// TODO: Implement this method
		switch(p1){
			case 0:
				return new TrackFragment().getNewInstance();
			case 1:
				return new ArtistsFragment().getNewInstance();
			default:
				return null;
		}
	}

}
