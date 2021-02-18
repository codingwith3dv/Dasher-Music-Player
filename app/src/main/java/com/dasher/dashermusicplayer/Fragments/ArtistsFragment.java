package com.dasher.dashermusicplayer.Fragments;

import android.support.v4.app.*;
import android.view.*;
import android.os.*;
import com.dasher.dashermusicplayer.*;
import android.support.v7.widget.RecyclerView;
import java.util.*;
import com.dasher.dashermusicplayer.Models.*;
import com.dasher.dashermusicplayer.Utils.*;
import android.widget.*;
import android.support.v7.widget.*;
import android.content.*;
import com.dasher.dashermusicplayer.R;

public class ArtistsFragment extends Fragment
{

	RecyclerView mRecyclerView;
	ArtistAdapter mAdapter;
	ArrayList<AlbumList> mArrayList;
	LoadMusic lm;

	public Fragment getNewInstance( )
	{
		// TODO: Implement this method
		return new ArtistsFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState )
	{
		// TODO: Implement this method
		View v = inflater.inflate(R.layout.artsits_xml,container,false);
		lm = new LoadMusic();
	
		mArrayList = new ArrayList<>(lm.getAlbumList(getActivity()));
		mRecyclerView = v.findViewById(R.id.artist_item_layout);
		showRecyclerView();
	
		long start = System.nanoTime();
		for(int j = 0; j < mArrayList.size(); j++){
			ArrayList<SongList> array = lm.getSongsFromArtistId(getActivity(),mArrayList.get(j).getArtistId());
		}
		long end = System.nanoTime();
		long time = (long) (end - start)/1000000000;//*Math.pow(10,-9);
		Toast.makeText(getActivity(),time + "\n" + (end - start) ,2000).show();
		return v;
		
	}

	public void showRecyclerView(){
		mAdapter = new ArtistAdapter(mArrayList,getActivity());
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mAdapter.setOnRecyclerViewItemClickListener(new ArtistAdapter.onRecyclerViewOnItemClickListener(){

				@Override
				public void onRecyclerViewOnItemClick(int position)
				{
					// TODO: Implement this method
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainRelativeLayoutfordifferentfunctions,new ArtistDetailsFragment()).addToBackStack(null).commit();
				}
			});
	}

}
