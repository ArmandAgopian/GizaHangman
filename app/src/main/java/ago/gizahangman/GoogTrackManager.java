package ago.gizahangman;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;


public class GoogTrackManager 
{
    protected static boolean forNOOK = false;
	protected static GoogTrackManager INSTANCE; 
	
	protected GoogleAnalyticsTracker tracker;
	 
    protected int activityCount = 0; 
    protected Integer dispatchIntervalSecs = 10; 
    protected String apiKey; 
    protected Context context; 
    
    //protected static boolean isSdk;

    Date	start_time;
    
    protected GoogTrackManager( Application ctxt ) 
    {
    	if (GoogTrackManager.forNOOK) return;
    	this.apiKey = "UA-29298742-5";
        this.context = ctxt; 
        this.activityCount = 0;
        
        /*
        Toast.makeText(ctxt.getApplicationContext(), "Brand " + Build.BRAND, Toast.LENGTH_LONG).show();
        Toast.makeText(ctxt.getApplicationContext(), "Device " + Build.DEVICE, Toast.LENGTH_LONG).show();
        Toast.makeText(ctxt.getApplicationContext(), "Display " + Build.DISPLAY, Toast.LENGTH_LONG).show();
        Toast.makeText(ctxt.getApplicationContext(), "Manufacturer " + Build.MANUFACTURER, Toast.LENGTH_LONG).show();
        Toast.makeText(ctxt.getApplicationContext(), "Hardware " + Build.HARDWARE, Toast.LENGTH_LONG).show();
        Toast.makeText(ctxt.getApplicationContext(), "Model " + Build.MODEL, Toast.LENGTH_LONG).show();
        Toast.makeText(ctxt.getApplicationContext(), "User " + Build.USER, Toast.LENGTH_LONG).show();
        */
    
        //isSdk = (Build.MODEL.compareToIgnoreCase("sdk") == 0);
    } 
    
    /** 
     * This should be called once in onCreate() for each of your activities that use GoogleAnalytics. 
     * These methods are not synchronized and don't generally need to be, so if you want to do anything 
     * unusual you should synchronize them yourself. 
     */ 
    protected void incrementActivityCount(Application context) { 

    	//if (GoogTrackManager.isSdk) return;
    	if (GoogTrackManager.forNOOK) return;
    	
        if( activityCount==0 ) 
        {
          	tracker = GoogleAnalyticsTracker.getInstance();
          	tracker.startNewSession(this.apiKey, this.dispatchIntervalSecs, this.context);

        
			// ----------------------- record the app start time -----------
          	start_time = new Date();
          	          	
			int hours = start_time.getHours();
			int minutes = start_time.getMinutes();
			int min_10 = ((minutes) / 10) * 10;
			
			
			String time_10min = String.format("%02d",hours) + ":" + String.format("%02d",min_10);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String actual_date = dateFormat.format(start_time);
			
			tracker.trackEvent("AppStart", time_10min, actual_date, 0);
			// ----------------------- record the app start time -----------
        
        }
        
        //    if( dispatchIntervalSecs==null ) 
        //        GoogleAnalyticsTracker.getInstance().start(apiKey,context); 
        //    else 
        //        GoogleAnalyticsTracker.getInstance().start(apiKey,dispatchIntervalSecs,context); 
 
    	
    	
        ++activityCount; 
        
    	return;
    } 
    
    /** 
     * This should be called once in onDestrkg() for each of your activities that use GoogleAnalytics. 
     * These methods are not synchronized and don't generally need to be, so if you want to do anything 
     * unusual you should synchronize them yourself. 
     */ 
    protected void decrementActivityCount() { 
    	//if (GoogTrackManager.isSdk) return;
    	if (GoogTrackManager.forNOOK) return;

        activityCount = Math.max(activityCount-1, 0); 
 
        if( activityCount==0 ) 
        {
        	// ----------------- end session -----------------
          	Date end_time = new Date();
          	
			int hours = end_time.getHours();
			int minutes = end_time.getMinutes();
			int min_10 = ((minutes) / 10) * 10;
			
			String time_10min = String.format("%02d",hours) + ":" + String.format("%02d",min_10);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String actual_end_date = dateFormat.format(end_time);
			
			
			long delta_ms = end_time.getTime() - start_time.getTime();
			long seconds_played = delta_ms / 1000;
			int secs10_played = (int) (((seconds_played + 5) / 10) * 10);
			
			tracker.trackEvent("AppEnd", time_10min, actual_end_date, secs10_played);
        	// ----------------- end session -----------------

			// dispatch all remaining activities.
			tracker.dispatch();
        	
			tracker.stopSession();
        }
            //GoogleAnalyticsTracker.getInstance().stop(); 
    } 

    public static void CommenceActivity (Application context)
    {
    	if (GoogTrackManager.forNOOK) return;
    	try
    	{
    		if (INSTANCE == null)
    		{
    			INSTANCE = new GoogTrackManager(context);
    			
    		}
        	INSTANCE.incrementActivityCount(context);
    	}
    	catch (Exception ex)
    	{
    		Context ctxt = INSTANCE.context.getApplicationContext();
    		Toast.makeText(ctxt, "TrkCommActv " + ex.getMessage() , Toast.LENGTH_LONG).show();
    	}
    }
 
    public static void EndActivity ()
    {
    	if (GoogTrackManager.forNOOK) return;
    	try
    	{
    		//if (GoogTrackManager.isSdk) return;
    		INSTANCE.decrementActivityCount();
    	}
    	catch (Exception ex)
    	{
    		Context ctxt = INSTANCE.context.getApplicationContext();
    		Toast.makeText(ctxt, "TrkEndActv " + ex.getMessage() , Toast.LENGTH_LONG).show();
    	}
    }

    public static void trackPageView (String arg0)
    {
    	if (GoogTrackManager.forNOOK) return;
    	try
    	{
    		//if (GoogTrackManager.isSdk) return;
    		INSTANCE.tracker.trackPageView(arg0);    	
    	}
    	catch (Exception ex)
    	{
    		Context ctxt = INSTANCE.context.getApplicationContext();
    		Toast.makeText(ctxt, "TrkPageView " + ex.getMessage() , Toast.LENGTH_LONG).show();
    	}
    }
    
    public static void trackEvent (String category, String action, String opt_label, int opt_value)
    {
    	if (GoogTrackManager.forNOOK) return; 
    	try
    	{
    		//if (GoogTrackManager.isSdk) return;
    		INSTANCE.tracker.trackEvent(category, action, opt_label, opt_value);
    	}
    	catch (Exception ex)
    	{
    		Context ctxt = INSTANCE.context.getApplicationContext();
    		Toast.makeText(ctxt, "TrkEvnt " + ex.getMessage() , Toast.LENGTH_LONG).show();
    	}
    }

}
