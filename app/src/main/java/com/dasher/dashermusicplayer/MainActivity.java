package com.dasher.dashermusicplayer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.dasher.dashermusicplayer.Player.MusicManager;
import com.dasher.dashermusicplayer.R;
import com.dasher.dashermusicplayer.Service.MusicService;
import com.dasher.dashermusicplayer.Utils.CustomPagerAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	private static TabLayout tabLayout;
	private static ViewPager viewPager;
	private static SlidingUpPanelLayout layout;
	private static boolean isExpanded;
	private static Context mContext;

	private static final int STORAGE_REQUEST_CODE = 100;

	private static ImageView play;
	private static ImageView albumArtSmall;
	private static TextView currentPlayingSongName;
	
	private float slideOffset;
	private static Toolbar toolBar;

	public static void hideOrShowActionBar(boolean show){
		if(show){
			tabLayout.setVisibility(View.VISIBLE);
			toolBar.setVisibility(View.VISIBLE);
		}else{
			tabLayout.setVisibility(View.GONE);
			toolBar.setVisibility(View.GONE);
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setTheme(R.style.AppThemeDark);
        setContentView(R.layout.main);
	
		this.mContext = getApplicationContext();
		
		toolBar = (Toolbar) findViewById(R.id.appbarlayout_tool_bar);
		tabLayout = (TabLayout) findViewById(R.id.tab_layout);
		play = (ImageView) findViewById(R.id.mainImageView1);
		albumArtSmall = (ImageView) findViewById(R.id.botombarxmlImageView1);
		currentPlayingSongName = (TextView) findViewById(R.id.botombar_xmlTextView);
		layout= (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
		viewPager = (ViewPager) findViewById(R.id.mainViewPager1);
		
		tabLayout.addTab(tabLayout.newTab().setText("Tracks"));
		tabLayout.addTab(tabLayout.newTab().setText("Artists"));
	
		if(ContextCompat.checkSelfPermission(mContext,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
		}else{
			init();
		}

		setSupportActionBar(toolBar);
		getSupportActionBar().show();
	
		play.setOnClickListener(this);
		
	}

	private void init()
	{
		CustomPagerAdapter cpafvp = new CustomPagerAdapter(getSupportFragmentManager(), 2);
		viewPager.setAdapter(cpafvp);
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); 

		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
			@Override
			public void onTabSelected(TabLayout.Tab p1) { viewPager.setCurrentItem(p1.getPosition()); }
			@Override
			public void onTabUnselected(TabLayout.Tab p1) {}
			@Override
			public void onTabReselected(TabLayout.Tab p1) {}
		});
		
		layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener(){
			@Override
			public void onPanelSlide(View p1, float p2)
			{
				slideOffset = p2;
				// TODO: Implement this method
				if (layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) { play.setVisibility(View.GONE); isExpanded = true; }
				else if (layout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) { play.setVisibility(View.VISIBLE); isExpanded = false; }
				else if (p2 > 0.0f && p2 < 1.0f) {
					if (isExpanded) { play.setAlpha(0.f + p2); }
					else { play.setAlpha(1.f - p2); }
				}
			}
			
			@Override
			public void onPanelStateChanged(View p1, SlidingUpPanelLayout.PanelState p2, SlidingUpPanelLayout.PanelState p3)
			{
				// TODO: Implement this method
				if (slideOffset < 0.2 && p2 == SlidingUpPanelLayout.PanelState.DRAGGING) { layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED); }
				else if (slideOffset == 1 && p2 == SlidingUpPanelLayout.PanelState.DRAGGING) { layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED); }
			}
		});

		if (!MusicService.isPlaying()) { MusicManager.createSong(); }
		if (MusicService.isPlaying()) { this.setPlayPauseView(true); }
		else { this.setPlayPauseView(false); }
		this.setCurrentPlayingSongData();
	}

	@Override
    public void onBackPressed() {
        if (layout != null &&
			(layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		switch(p1.getId()){
			case R.id.botombarxmlImageView1:
				layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
				break;
			case R.id.mainImageView1:
				if(MusicService.isPlaying()){
					this.setPlayPauseView(false);
					MusicManager.pauseSong();
				}else{
					this.setPlayPauseView(true);
					MusicManager.playSong();
				}
				break;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		// TODO: Implement this method
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(requestCode == STORAGE_REQUEST_CODE && grantResults.length > 0){
			if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
				Toast.makeText(getApplicationContext(),"Permission Granted, Thank you!",2000).show();
				init();
			}else{
				Toast.makeText(getApplicationContext(),"Permission Denied, We need storage permission to access songs from storage",2000).show();
				ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
			}
		}
	}

	public static void setPlayPauseView(boolean isPlaying){
		if(isPlaying){
			play.setImageResource(R.drawable.pause_button);
		}else{
			play.setImageResource(R.drawable.play_button);
		}
	}

	public static Context getContextFromMainActivity(){
		return mContext;
	}

	public static void setCurrentPlayingSongData(){
		if(MusicManager.getImage() != null) 
			albumArtSmall.setImageBitmap(BitmapFactory.decodeByteArray(MusicManager.getImage(),0,MusicManager.getImage().length));
		else
			albumArtSmall.setImageResource(R.drawable.ic_launcher_small_256);
		currentPlayingSongName.setText(MusicManager.getSongTitle());
	}

}
