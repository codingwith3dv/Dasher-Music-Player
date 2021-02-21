package com.dasher.dashermusicplayer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.dasher.dashermusicplayer.R;
import com.dasher.dashermusicplayer.MainActivity;

public class ArtistDetailsFragment extends Fragment
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v = inflater.inflate(R.layout.artist_details,container,false);
		
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		MainActivity.hideOrShowActionBar(false);
	}

	@Override
	public void onDestroyView()
	{
		// TODO: Implement this method
		super.onDestroyView();
		MainActivity.hideOrShowActionBar(true);
	}
	
}
