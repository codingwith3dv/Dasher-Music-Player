package com.dasher.dashermusicplayer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dasher.dashermusicplayer.Models.SongList;
import com.dasher.dashermusicplayer.Player.MusicManager;
import com.dasher.dashermusicplayer.R;
import com.dasher.dashermusicplayer.Utils.LoadMusic;
import com.dasher.dashermusicplayer.Utils.TrackAdapter;
import java.util.ArrayList;

public class TrackFragment extends Fragment
{

	RecyclerView mRecyclerView;
	ArrayList<SongList> mArrayList;
	TrackAdapter mAdapter;
	View v;
	public Fragment getNewInstance(){
		return this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		mArrayList = LoadMusic.getAllData(getActivity());
		LoadMusic.clearCachedDataAllSongs();
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		// TODO: Implement this method
		v = inflater.inflate(R.layout.track_xml,container,false);
		
		mRecyclerView = v.findViewById(R.id.track_xmlRecyclerView1);
	
		showRecyclerView();
		
		return v;
	}

	

	public void showRecyclerView(){
		mAdapter = new TrackAdapter(mArrayList,getActivity());
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	
		mAdapter.setOnRecyclerViewItemClickListener( new TrackAdapter.onRecyclerViewOnItemClickListener( ){
			@Override
			public void onRecyclerViewOnItemClick( int position )
			{
				MusicManager.setArrayList(getActivity(),mArrayList,position);
			}
		});
	}

	@Override
	public void onDestroyView()
	{
		// TODO: Implement this method
		super.onDestroyView();
		mArrayList = null;
		mRecyclerView = null;
		mAdapter = null; 
	}

	@Override
	public void onDestroy( )
	{
		// TODO: Implement this method
		super.onDestroy( );
		
	}

}
