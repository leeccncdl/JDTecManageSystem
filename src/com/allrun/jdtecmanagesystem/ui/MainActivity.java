package com.allrun.jdtecmanagesystem.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.allrun.jdtecmanagesystem.AppLogger;
import com.allrun.jdtecmanagesystem.R;

public class MainActivity extends Activity {
	
	private AppLogger log = AppLogger.getLogger(MainActivity.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		DisplayMetrics dm = new DisplayMetrics();  
		dm = getResources().getDisplayMetrics();  
		float density  = dm.density;        // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）  
		int densityDPI = dm.densityDpi;     // 屏幕密度（每寸像素：120/160/240/320）  
		float xdpi = dm.xdpi;             
		float ydpi = dm.ydpi;  
		int screenWidth  = dm.widthPixels;      // 屏幕宽（像素，如：480px）  
		int screenHeight = dm.heightPixels;     // 屏幕高（像素，如：800px）  
		if(log.isDebugEnabled()) {
			
			log.debug("xdpi=" + xdpi + "; ydpi=" + ydpi);  
			log.debug("density=" + density + "; densityDPI=" + densityDPI);  
			log.debug("screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);
		}
		  
	}

}
