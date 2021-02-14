package com.dasher.dashermusicplayer.Utils;

import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.dasher.dashermusicplayer.*;
import java.util.*;
import com.dasher.dashermusicplayer.Models.*;
import android.graphics.*;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder>
{

	private ArrayList<SongList> mArrayList;
	private Context context;
	public static int lastPosition = -1;
	public onRecyclerViewOnItemClickListener mListener;

	public interface onRecyclerViewOnItemClickListener{
		void onRecyclerViewOnItemClick(int position);
	}

	public void setOnRecyclerViewItemClickListener(onRecyclerViewOnItemClickListener listener){
		mListener = listener;
	}
	
	public static class TrackViewHolder extends RecyclerView.ViewHolder{
		private TextView mTitle;
		private TextView mArtist;
		private ImageView mAlbumArt;
	
		public TrackViewHolder(View v,final onRecyclerViewOnItemClickListener listener){
			super(v);
			mTitle = v.findViewById(R.id.trackitemlayoutTextView1);
			mArtist = v.findViewById(R.id.trackitemlayoutTextView2);
			mAlbumArt = v.findViewById(R.id.trackitemlayoutImageView1);
		
			v.setOnClickListener( new View.OnClickListener( ){

				@Override
				public void onClick( View p1 )
				{
					if(listener != null){
						int position = getAdapterPosition();
						if(position != RecyclerView.NO_POSITION){
							listener.onRecyclerViewOnItemClick(position);
						}
					}
				}
			});
		}
	}
	
	public TrackAdapter(ArrayList<SongList> arraylist,Context contextin){
		mArrayList = arraylist;
		context = contextin;
	}

	@Override
	public TrackViewHolder onCreateViewHolder( ViewGroup p1, int p2 )
	{
		// TODO: Implement this method
		Context context = p1.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
	
		View trackView = inflater.inflate(R.layout.trackitemlayout,p1,false);
		
		return new TrackViewHolder(trackView,mListener);
	}

	@Override
	public void onBindViewHolder( TrackAdapter.TrackViewHolder p1, int p2 )
	{
		// TODO: Implement this method
		SongList trackList = mArrayList.get(p2);
		p1.mTitle.setText(trackList.getTitle());
		p1.mArtist.setText(trackList.getArtist());
	
		byte[] image = trackList.getImage();
		if(image == null){
			p1.mAlbumArt.setImageResource(R.drawable.ic_launcher_small_256);
		}else{
			p1.mAlbumArt.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
		}
		p1.mAlbumArt.setClipToOutline(true);
		setAnimation(p1.itemView,p2);
	}

	private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (1 > 0)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
			animation.setDuration(100);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

	@Override
	public int getItemCount( )
	{
		// TODO: Implement this method
		return mArrayList.size();
	}
	
}
