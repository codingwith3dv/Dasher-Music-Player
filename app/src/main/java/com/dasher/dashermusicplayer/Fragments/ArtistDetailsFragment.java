package com.dasher.dashermusicplayer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dasher.dashermusicplayer.MainActivity;
import com.dasher.dashermusicplayer.Models.SongList;
import com.dasher.dashermusicplayer.Player.MusicManager;
import com.dasher.dashermusicplayer.R;
import com.dasher.dashermusicplayer.Utils.TrackAdapter;
import java.util.ArrayList;

public class ArtistDetailsFragment extends Fragment
{
	private ArrayList<SongList> mArrayList;

	private RecyclerView mRecyclerView;

	private TrackAdapter mAdapter;

	public ArtistDetailsFragment(ArrayList<SongList> arrayList){
		this.mArrayList = arrayList;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v = inflater.inflate(R.layout.artist_details,container,false);
		mRecyclerView = v.findViewById(R.id.artistdetails_xmlRecyclerView1);
		showRecyclerView();
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
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		ArtistsFragment.method(true);
	}
	
}
