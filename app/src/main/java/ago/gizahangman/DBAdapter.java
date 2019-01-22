package ago.gizahangman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Application;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

public class DBAdapter {

	protected static DBAdapter INSTANCE; 

    public static final String DATABASE_NAME = "giza_hangman.db";
    
    public static final int DATABASE_VERSION = 1; 
 
    protected int activityCount = 0; 
    private Context context;  
    
    
    private DatabaseHelper DBHelper; 
    private SQLiteDatabase db = null; 
    

 
 
    /** 
     * Constructor 
     * @param ctx 
     * @throws IOException 
     */ 
    public DBAdapter(Application ctxt) 
    { 
    	try
    	{
	        this.context = ctxt; 
	        this.DBHelper = new DatabaseHelper(this.context); 
	
	        File file = Environment.getDataDirectory();
	        String filePath = file.getAbsolutePath();
	        
	        // if necessary, copy the database.
	        String packageName = ctxt.getPackageName();
			String destPath = filePath + "/data/" + packageName + "/databases/" + DATABASE_NAME;
			String tempPath = filePath + "/data/" + packageName + "/databases/" + "T"+DATABASE_NAME;

			// pre-copy it here.   Delete it if it already exists
			File tempF = new File(tempPath);
			if (tempF.exists())
			{
				tempF.delete();
			}
			
			InputStream input = ctxt.getAssets().open(DATABASE_NAME);
			OutputStream tempOutput = new FileOutputStream(tempPath);
			long dbSize = CopyDB (input, tempOutput);
			
			// destination missing?
			File destF = new File(destPath);
			if (!destF.exists())
			{
				tempF.renameTo(destF);
			}
			else //if (destF.length() != dbSize)
			{
				destF.delete();
				tempF.renameTo(destF);
			}
			
    		this.db = SQLiteDatabase.openDatabase(destPath, null, SQLiteDatabase.OPEN_READWRITE);

    	}
    	catch (Exception ex)
    	{
    		
    	}


    } 
    
    private long CopyDB (InputStream input, OutputStream output)
        	throws IOException
    {
    	long finalCount = 0;
    		byte[] buffer = new byte[1024];
    		int length;
    		while ((length = input.read(buffer)) > 0)
    		{
    			finalCount += length;
    			output.write(buffer, 0, length);
    		}
    		
    		input.close();
    		output.close();    
    		
    		return finalCount;
    }
    
    
    public static void CommenceActivity (Application ctxt)
    {
    	try
    	{
    		if (INSTANCE == null)
    		{
    			INSTANCE = new DBAdapter(ctxt);
    			
    		}
    	}
    	catch (Exception ex)
    	{
    		Context ctxtApp = INSTANCE.context.getApplicationContext();
    		//Toast.makeText(ctxtApp, "TrkCommActv " + ex.getMessage() , Toast.LENGTH_LONG).show();
    	}
    	
    	//-->hos INSTANCE.incrementActivityCount();
    }
 
    public static void EndActivity ()
    {
    	try
    	{
    		//-->hos INSTANCE.decrementActivityCount();
    	}
    	catch (Exception ex)
    	{
    		Context ctxt = INSTANCE.context.getApplicationContext();
    		//Toast.makeText(ctxt, "TrkEndActv " + ex.getMessage() , Toast.LENGTH_LONG).show();
    	}
    }

    /** 
     * open the db 
     * @return this 
     * @throws SQLException 
     * return type: DBAdapter 
     */ 
    
    public static SQLiteDatabase mDB() throws SQLException  
    { 
        if (INSTANCE.db == null) 
        	INSTANCE.db = INSTANCE.DBHelper.getWritableDatabase();
        
        return INSTANCE.db; 
    } 
 
    /** 
     * close the db  
     * return type: void 
     */ 
    //public void close()  
    //{ 
    //    this.DBHelper.close(); 
    //} 

    
    /** 
     * This should be called once in onCreate() for each of your activities that use GoogleAnalytics. 
     * These methods are not synchronized and don't generally need to be, so if you want to do anything 
     * unusual you should synchronize them yourself. 
     */ 
    protected void incrementActivityCount() { 
        if( activityCount==0 ) 
        {

        	db = this.DBHelper.getWritableDatabase(); 
        	
        
        }
        
        //    if( dispatchIntervalSecs==null ) 
        //        GoogleAnalyticsTracker.getInstance().start(apiKey,context); 
        //    else 
        //        GoogleAnalyticsTracker.getInstance().start(apiKey,dispatchIntervalSecs,context); 
 
    	
    	
        ++activityCount; 
    } 

    /** 
     * This should be called once in onDestrkg() for each of your activities that use GoogleAnalytics. 
     * These methods are not synchronized and don't generally need to be, so if you want to do anything 
     * unusual you should synchronize them yourself. 
     */ 
    protected void decrementActivityCount() { 
        activityCount = Math.max(activityCount-1, 0); 
 
        if( activityCount==0 ) 
        {
        	this.DBHelper.close();
        }
    } 


 
    private static class DatabaseHelper extends SQLiteOpenHelper  
    { 
        DatabaseHelper(Context context)  
        { 
            super(context, DATABASE_NAME, null, DATABASE_VERSION); 
        } 
 
        @Override 
        public void onCreate(SQLiteDatabase db)  
        { 
        	try
        	{
        		db.execSQL(DBQandA.CREATE_TABLE);
        	}
        	catch (Exception ex)
        	{
        		ex.printStackTrace();
        	}
        } 
 
        @Override 
        public void onUpgrade(SQLiteDatabase db, int oldVersion,  
        int newVersion)  
        {                
            // Adding any table mods to this guy here 
        	//if (oldVersion == 1)
        	//{
        	//}
        	//else
        	//{
        	//	onCreate (db);
        	//}

    		//db.execSQL("DROP TABLE IF EXISTS " + DBQandA.DATABASE_TABLE);
    		//onCreate (db);
        } 
    }  
 

}
