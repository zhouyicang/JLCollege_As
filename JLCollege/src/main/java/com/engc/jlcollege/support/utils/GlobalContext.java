package com.engc.jlcollege.support.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.Display;
import android.view.WindowManager;

import com.engc.jlcollege.bean.AccountBean;



/**
 * User: Jiang Qi
 * Date: 12-7-27
 */
@SuppressLint("NewApi")
public final class GlobalContext extends Application {

    //singleton
    private static GlobalContext globalContext = null;
    private SharedPreferences sharedPref = null;

    //image size
    private Activity activity = null;
    private DisplayMetrics displayMetrics = null;

    //image memory cache
    private LruCache<String, Bitmap> avatarCache = null;

    //current account info
    private AccountBean accountBean = null;

    public boolean startedApp = false;


    private Map<String, String> emotions = null;

    private Map<String, Bitmap> emotionsPic = new HashMap<String, Bitmap>();

    
 
	
	public static final int NUM_PAGE = 6;// 总共有多少页
	public static int NUM = 20;// 每页20个表情,还有最后一个删除button
	private Map<String, Integer> mFaceMap = new LinkedHashMap<String, Integer>();
	
	private MediaPlayer mMediaPlayer;
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private static final int CACHE_TIME = 60 * 60000;// 缓存失效时间

	//获取屏幕窗口参数
	 private WindowManager.LayoutParams windowParams=new WindowManager.LayoutParams();
	 public  WindowManager.LayoutParams getWindowParams(){
		 return windowParams;
	 }

   
    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        buildCache();
       // CrashHandler.getInstance().init(this);
		
    }

    public static GlobalContext getInstance() {
        return globalContext;
    }

 



	public NotificationManager getNotificationManager() {
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}



	
	public Map<String, Integer> getFaceMap() {
		if (!mFaceMap.isEmpty())
			return mFaceMap;
		return null;
	}

	

    public DisplayMetrics getDisplayMetrics() {
        if (displayMetrics != null) {
            return displayMetrics;
        } else {
            Activity a = getActivity();
            if (a != null) {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                this.displayMetrics = metrics;
                return metrics;
            } else {
                //default screen is 800x480
                DisplayMetrics metrics = new DisplayMetrics();
                metrics.widthPixels = 480;
                metrics.heightPixels = 800;
                return metrics;
            }
        }
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }





 

    public synchronized LruCache<String, Bitmap> getAvatarCache() {
        if (avatarCache == null) {
            buildCache();
        }
        return avatarCache;
    }

  

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void buildCache() {
        int memClass = ((ActivityManager) getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();

//        int cacheSize = 1024 * 1024 * memClass / 5;

        int cacheSize = 1024 * 1024 * 8;

        avatarCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                return bitmap.getByteCount();
            }
        };
    }

    public synchronized Map<String, Bitmap> getEmotionsPics() {
        if (emotionsPic != null && emotionsPic.size() > 0) {
            return emotionsPic;
        } else {
            
            return emotionsPic;
        }
    }


   
    
    

}

