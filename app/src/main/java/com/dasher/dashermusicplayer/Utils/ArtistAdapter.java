package com.dasher.dashermusicplayer.Utils;

import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import com.dasher.dashermusicplayer.Models.*;
import java.util.*;
import android.widget.*;
import com.dasher.dashermusicplayer.*;
import com.dasher.dashermusicplayer.R;
import com.dasher.dashermusicplayer.Models.AlbumList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>
{
	
	private ArrayList<AlbumList> mArrayList;
	private Context context;
	private onRecyclerViewOnItemClickListener mListener;

	public interface onRecyclerViewOnItemClickListener{
		void onRecyclerViewOnItemClick(int position);
	}

	public void setOnRecyclerViewItemClickListener(onRecyclerViewOnItemClickListener listener){
		mListener = listener;
	}

	public static class ArtistViewHolder extends RecyclerView.ViewHolder{

		private ImageView mImageView;
		private TextView mTextView1;
		private TextView mTextView2;
		
		public ArtistViewHolder(View v,final onRecyclerViewOnItemClickListener listener){
			super(v);
			mImageView = v.findViewById(R.id.artistart);
			mTextView1 = v.findViewById(R.id.artistname);
			mTextView2 = v.findViewById(R.id.artistnumber);
			v.setOnClickListener( new View.OnClickListener( ){

					@Override
					public void onClick( View p1 )
					{
						// TODO: Implement this method
						if(listener != null){
							int position = getAdapterPosition();
							if(position != RecyclerView.NO_POSITION){
								listener.onRecyclerViewOnItemClick(position);
							}
						}
					}
				} );
		}
	}

	public ArtistAdapter(ArrayList<AlbumList> arraylist,Context contextin){
		this.mArrayList = arraylist;
		this.context = contextin;
	}

	@Override
	public ArtistViewHolder onCreateViewHolder( ViewGroup p1, int p2 )
	{
		// TODO: Implement this method
		Context context = p1.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		View trackView = inflater.inflate(R.layout.artist_item_layout,p1,false);
		
		return new ArtistViewHolder(trackView,mListener);
	}

	@Override
	public void onBindViewHolder( ArtistViewHolder p1, int p2 )
	{
		// TODO: Implement this method
		AlbumList list = mArrayList.get(p2);
		p1.mTextView1.setText(list.getArtistName());
		p1.mTextView2.setText(String.valueOf(list.getnumber_of_songs()));
		p1.mImageView.setImageResource(R.drawable.ic_launcher_small_256);
		p1.mImageView.setClipToOutline(true);
	}

	@Override
	public int getItemCount( )
	{
		// TODO: Implement this method
		return mArrayList.size();
	}
	
}
