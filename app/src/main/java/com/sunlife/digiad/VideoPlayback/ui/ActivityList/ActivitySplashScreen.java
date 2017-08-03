package com.sunlife.digiad.VideoPlayback.ui.ActivityList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.sunlife.digiad.VideoPlayback.R;


public class ActivitySplashScreen extends Activity
{
    
    private static long SPLASH_MILLIS = 450;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CreateNewFile();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
            R.layout.splash_screen, null, false);
        
        addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            
            @Override
            public void run()
            {
                
                Intent intent = new Intent(ActivitySplashScreen.this,
                    AboutScreen.class);
                intent.putExtra("ACTIVITY_TO_LAUNCH",
                    "app.VideoPlayback.VideoPlayback");
                intent.putExtra("ABOUT_TEXT_TITLE", "GnC DigiAd");
                intent.putExtra("ABOUT_TEXT", "VideoPlayback/VP_about.html");
                startActivity(intent);
                
            }
            
        }, SPLASH_MILLIS);
    }

    public void CreateNewFile()
    {
        try {
            SharedPreferences settings =  this.getSharedPreferences("setts",Context.MODE_PRIVATE);
            String ExistingHash = settings.getString("LastHash","0");
            String url = "http://arservice.somee.com/Home/GetHash";
            String NewHash = new HttpAsyncTask().execute(url).get();
            if(!ExistingHash.equals(NewHash)) {
                String url1 = "http://arservice.somee.com/data/SunlifeAR2.xml";
                String url2 = "http://arservice.somee.com/data/SunlifeAR2.dat";
                String url3 = "http://arservice.somee.com/data/TargetVideoMapping.xml";
                Object object = new DownloadFilesTask(this).execute(url1, url2, url3).get();
                SharedPreferences.Editor editor= settings.edit();
                editor.putString("LastHash",NewHash);
                editor.commit();
            }
        } catch (Exception ex) {
            return;
        }
    }
    
}
