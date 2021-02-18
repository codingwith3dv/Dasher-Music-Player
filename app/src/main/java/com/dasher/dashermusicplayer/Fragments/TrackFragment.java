package com.dasher.dashermusicplayer.Fragments;
import android.*;
import android.content.pm.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.dasher.dashermusicplayer.*;
import com.dasher.dashermusicplayer.Utils.*;
import java.util.*;

import com.dasher.dashermusicplayer.R;
import com.dasher.dashermusicplayer.Models.*;
import android.content.*;
import com.dasher.dashermusicplayer.Service.*;
import com.dasher.dashermusicplayer.Player.MusicManager;

public class TrackFragment extends Fragment
{

	RecyclerView mRecyclerView;
	ArrayList<SongList> mArrayList;
	TrackAdapter mAdapter;
	View v;
	public static final int STORAGE_REQUEST_CODE = 100;

	private LoadMusic lm;
	MusicService player;
	private boolean serviceBound = false;
	
	public Fragment getNewInstance(){
		return new TrackFragment();
	}

	public void checkPermission(){
		if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
		}else{
			showRecyclerView();
			
		}
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		// TODO: Implement this method
		v = inflater.inflate(R.layout.track_xml,container,false);
	
		lm = new LoadMusic();
		player = new MusicService();
		
		mArrayList = new ArrayList<SongList>(lm.getAllData(getActivity()));
		
		//getActivity().getFragmentManager().beginTransaction().replace(R.id.mainRelativeLayout,bbp.getInstance()).commit();
		
		if(mArrayList != null){
			
		}
	
		mRecyclerView = v.findViewById(R.id.track_xmlRecyclerView1);
	
		checkPermission();
		
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
	public void onDestroy( )
	{
		// TODO: Implement this method
		super.onDestroy( );
		
	}

}
