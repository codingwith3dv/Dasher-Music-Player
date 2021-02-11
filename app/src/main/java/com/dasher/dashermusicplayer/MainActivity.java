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

		layout= (SlidingUpPanelLayout)
			findViewById(R.id.sliding_layout);
		layout.setDragView(R.id.maininclude);
		
		playOrPause = (ImageView)findViewById(R.id.mainImageView1);
		playOrPause.setOnClickListener(this);

		slideUp = (ImageView) findViewById(R.id.botombarxmlImageView1);
		slideUp.setOnClickListener(this);

		slideDown = (ImageView)findViewById(R.id.bottombar_xml_expandedImageView);
		slideDown.setOnClickListener(this);

		(findViewById(R.id.maininclude)).setOnClickListener(this);

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
					// TODO: Implement this method
					if(p2 == 0.0){
						isExpanded = false;
						Toast.makeText(getApplicationContext(),"" + isExpanded,2000).show();
						(findViewById(R.id.maininclude)).setVisibility(View.VISIBLE);
						(findViewById(R.id.maininclude2)).setVisibility(View.INVISIBLE);
						tabLayout.setVisibility(View.VISIBLE);
					}else if(p2 == 1.0){
						isExpanded = true;
						Toast.makeText(getApplicationContext(),"" + isExpanded,2000).show();
						(findViewById(R.id.maininclude)).setVisibility(View.INVISIBLE);
						(findViewById(R.id.maininclude2)).setVisibility(View.VISIBLE);
						tabLayout.setVisibility(View.GONE);
					}else if(p2 > 0.0 && p2 < 1.0){
						if(isExpanded == true){
							//(findViewById(R.id.maininclude)).setVisibility(View.VISIBLE);
							(findViewById(R.id.maininclude)).setVisibility(View.VISIBLE);
							(findViewById(R.id.maininclude2)).setVisibility(View.INVISIBLE);
							tabLayout.setVisibility(View.VISIBLE);
						}
						else{
							(findViewById(R.id.maininclude)).setVisibility(View.INVISIBLE);
							(findViewById(R.id.maininclude2)).setVisibility(View.VISIBLE);
							tabLayout.setVisibility(View.GONE);
						}
					}
				}

				@Override
				public void onPanelStateChanged(View p1, SlidingUpPanelLayout.PanelState p2, SlidingUpPanelLayout.PanelState p3)
				{
					// TODO: Implement this method
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
				Toast.makeText(getApplicationContext(),"PlayPauseEvent",2000).show();
				break;
			case R.id.botombarxmlImageView1:
				layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
				break;
			case R.id.bottombar_xml_expandedImageView:
				layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
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
