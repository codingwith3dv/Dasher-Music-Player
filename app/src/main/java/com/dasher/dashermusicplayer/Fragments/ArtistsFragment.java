package com.dasher.dashermusicplayer.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dasher.dashermusicplayer.Models.AlbumList;
import com.dasher.dashermusicplayer.R;
import com.dasher.dashermusicplayer.Utils.ArtistAdapter;
import com.dasher.dashermusicplayer.Utils.LoadMusic;
import java.util.ArrayList;

public class ArtistsFragment extends Fragment
{

	RecyclerView mRecyclerView;
	ArtistAdapter mAdapter;
	ArrayList<AlbumList> mArrayList;
	
	public Fragment getNewInstance( )
	{
		// TODO: Implement this method
		return this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		mArrayList = LoadMusic.getAlbumList(getActivity());
		LoadMusic.clearCachedDataArtists();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState )
	{
		// TODO: Implement this method
		View v = inflater.inflate(R.layout.artsits_xml,container,false);
		
		mRecyclerView = v.findViewById(R.id.artist_item_layout);
		showRecyclerView();
	
		return v;
		
	}

	public void showRecyclerView(){
		mAdapter = new ArtistAdapter(mArrayList,getActivity());
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
		mAdapter.setOnRecyclerViewItemClickListener(new ArtistAdapter.onRecyclerViewOnItemClickListener(){
			@Override
			public void onRecyclerViewOnItemClick(int position)
			{
				// TODO: Implement this method
				getActivity().
					getSupportFragmentManager().
					beginTransaction().
					replace(R.id.mainRelativeLayoutfordifferentfunctions,
					new ArtistDetailsFragment(
						LoadMusic.getSongsFromArtistId(
							getActivity(),mArrayList.get(
								position
								).getArtistId()
							)
						)
					).addToBackStack(null).
					commit();
				LoadMusic.clearCachedDataArtistDetails();
			}
		});
	}

	@Override
	public void onDestroyView()
	{
		// TODO: Implement this method
		super.onDestroyView();
		mArrayList = null;
		mAdapter = null;
		mRecyclerView = null;
	}

}
