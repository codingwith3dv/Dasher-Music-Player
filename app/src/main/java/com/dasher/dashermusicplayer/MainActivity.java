package com.dasher.dashermusicplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.dasher.dashermusicplayer.R;
import com.dasher.dashermusicplayer.Utils.CustomPagerAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	TabLayout tabLayout;
	ViewPager viewPager;
	SlidingUpPanelLayout layout;
	private static boolean isExpanded;

	private static final int STORAGE_REQUEST_CODE = 100;

	private ImageView playOrPause;
	private ImageView slideUp;
	private ImageView slideDown;

	private float slideOffset;

	public void checkPermission(){
		if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
			ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
		}else{
			
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	
		Toolbar toolBar = (Toolbar) findViewById(R.id.appbarlayout_tool_bar);
		setSupportActionBar(toolBar);
		getSupportActionBar().hide();
		
		checkPermission();
		
		playOrPause = (ImageView) findViewById(R.id.mainImageView1);
		playOrPause.setOnClickListener(this);

		layout= (SlidingUpPanelLayout)
			findViewById(R.id.sliding_layout);
		
		
		tabLayout = (TabLayout) findViewById(R.id.tab_layout);
		tabLayout.addTab(tabLayout.newTab().setText("Tracks"));
		tabLayout.addTab(tabLayout.newTab().setText("Artists"));

		viewPager = (ViewPager) findViewById(R.id.mainViewPager1);
		CustomPagerAdapter cpafvp = new CustomPagerAdapter(getSupportFragmentManager(),2);
		viewPager.setAdapter(cpafvp);
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)); 

		tabLayout.setOnTabSelectedListener( new TabLayout.OnTabSelectedListener( ){
				@Override
				public void onTabSelected( TabLayout.Tab p1 )
				{
					viewPager.setCurrentItem(p1.getPosition()); 
				}
				@Override
				public void onTabUnselected( TabLayout.Tab p1 )
				{
					// TODO: Implement this method
				}
				@Override
				public void onTabReselected( TabLayout.Tab p1 )
				{
					// TODO: Implement this method
				}
			});
		
		layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener(){
			@Override
			public void onPanelSlide(View p1, float p2)
			{
				slideOffset = p2;
				// TODO: Implement this method
				if(layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
					playOrPause.setVisibility(View.INVISIBLE);
					playOrPause.setEnabled(false);
					isExpanded = true;
				}else if(layout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
					playOrPause.setVisibility(View.VISIBLE);
					playOrPause.setEnabled(true);
					isExpanded = false;
				}else if(p2 > 0.0f && p2 < 1.0f){
					if(isExpanded){
						playOrPause.setAlpha(0.f + p2);
					}else{
						playOrPause.setAlpha(1.f - p2);
					}
				}
			}

			@Override
			public void onPanelStateChanged(View p1, SlidingUpPanelLayout.PanelState p2, SlidingUpPanelLayout.PanelState p3)
			{
				// TODO: Implement this method
				if(slideOffset<0.2&& p2 == SlidingUpPanelLayout.PanelState.DRAGGING)
				{
					layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
				}
				else if(slideOffset == 1&& p2 == SlidingUpPanelLayout.PanelState.DRAGGING)
				{
					layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
				}
			}
		});

		
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
			case R.id.mainImageView1:
				if(isExpanded == false){
					Toast.makeText(getApplicationContext(),"PlayPauseEvent",2000).show();
				}
				break;
			case R.id.botombarxmlImageView1:
				layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
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
				Toast.makeText(getApplicationContext(),"Permission Granted",2000).show();
			}else{
				Toast.makeText(getApplicationContext(),"Permission Denied",2000).show();
				ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_REQUEST_CODE);
			}
		}
	}

}
