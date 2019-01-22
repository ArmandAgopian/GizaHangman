package ago.gizahangman;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBQandA {
	
    public static final String QUESTION = "question"; 
    public static final String PREFIX = "prefix"; 
    public static final String ANSWER = "answer"; 
    public static final String SUFFIX = "suffix"; 
    public static final String ASKED = "asked"; 

    public static final String DATABASE_TABLE = "khufu"; 

    public static final String CREATE_TABLE = "create table if not exists " + DATABASE_TABLE + " (" 
    + "_id INTEGER PRIMARY KEY NOT NULL,"
    + DBQandA.QUESTION + " TEXT," //$NON-NLS-1$ 
    + DBQandA.PREFIX + " TEXT,"     
    + DBQandA.ANSWER + " TEXT,"     
    + DBQandA.SUFFIX + " TEXT," 
    + DBQandA.ASKED + " INTEGER  NOT NULL  DEFAULT (0));"; //$NON-NLS-1$ 
 
    
 
    //private DatabaseHelper mDbHelper; 
    private SQLiteDatabase mDb; 
 
    //private final Context mCtx; 
 

    
    /** 
     * Constructor - takes the context to allow the database to be 
     * opened/created 
     *  
     * @param ctx 
     *            the Context within which to work 
     */ 
    public DBQandA(SQLiteDatabase db) 
    { 
    	this.mDb = db;
    } 
    /** 
     * close return type: void 
     */ 
    //public void close() { 
    //    this.mDbHelper.close(); 
    //} 
 
    /** 
     * Create a new MoOperType. If the Mo is successfully created return the new 
     * rowId for that Mo, otherwise return a -1 to indicate failure. 
     *  
     * @param name 
     * @param model 
     * @param year 
     * @return rowId or -1 if failed 
     */ 
    
    /*
    public DBQandA create (OneQandA oper)
    { 
        ContentValues initialValues = new ContentValues(); 
        initialValues.put(NAME, oper.MoOper.name()); 
        initialValues.put(MAX_LEVEL, oper.MaxLevel.name()); 
        
        oper.rowid = this.mDb.insert(DATABASE_TABLE, null, initialValues);
        return oper;
    } 
	*/

    /*
    public DBMoOper updateOrCreateIfNeeded (OneQandA moOperType, LEVEL maxLevel)
    { 
    	DBMoOper  retOper;
    	
    	// How many of this oper are there?  There should be only one.
    	ArrayList<DBMoOper> opers = this.getAll(moOperType);
    	if (opers.size() >= 1)
    	{
    		// remove all but the first.
    		int excess = opers.size() - 1;
    		while(excess > 0)
    		{
    			this.delete(opers.get(excess));
    			excess--;
    		}
    		
    		// use the rowid of the item in the database to update it with the values of the filter_oper
    		DBMoOper existing_oper = opers.get(0);
    		existing_oper.MoOper = moOperType;
    		existing_oper.MaxLevel = maxLevel;
    		this.update(existing_oper);
    		
    		
    		retOper = existing_oper;
    	}
    	
    	else
    	{
    		ContentValues initialValues = new ContentValues(); 
        	initialValues.put(NAME, moOperType.name()); 
        	initialValues.put(MAX_LEVEL, maxLevel.name()); 
        
        	long rowid = this.mDb.insert(DATABASE_TABLE, null, initialValues);

        	retOper = new DBMoOper();
        	retOper.rowid = rowid;
        	retOper.MoOper = moOperType;
        	retOper.MaxLevel = maxLevel;
    	
    	}
        return retOper;
    } 
 */

    /** 
     * Delete the Mo with the given rowId 
     *  
     * @param rowId 
     * @return true if deleted, false otherwise 
     */ 
    
    public boolean delete (OneQandA oper) { 
 
    	return true; 
        // never deletereturn this.mDb.delete(DATABASE_TABLE, "rowid = " + oper.rowid, null) > 0; //$NON-NLS-1$ 
    } 
 
    /** 
     * Return a Cursor over the list of all cars in the database 
     *  
     * @return Cursor over all cars 
     */ 

    /*
    private ArrayList<DBMoOper> getAll () { 
 
    	ArrayList<DBMoOper> retList = new ArrayList<DBMoOper>();
    	
        Cursor mCursor =  this.mDb.query(DATABASE_TABLE, new String[] { "rowid", NAME, MAX_LEVEL }, null, null, null, null, null); 
        
        if (mCursor == null)	return retList;
        mCursor.moveToFirst();
        
        while(! mCursor.isAfterLast() )
        {
        	DBMoOper oper = new DBMoOper();
        	oper.rowid = mCursor.getLong(0);
        	oper.MoOper = MO_OPER_TYPE.valueOf(mCursor.getString(1));
            oper.MaxLevel = LEVEL.valueOf(mCursor.getString(2));
        	
            retList.add(oper);
            mCursor.moveToNext();
        }
        
        return retList;
    } 
 */
    
    public ArrayList<Long> getUnaskedIDs () { 
    	 
    	ArrayList<Long> retList = new ArrayList<Long>();
    	
    	try
    	{
	        Cursor mCursor =  this.mDb.query(DATABASE_TABLE, new String[] { "_id"}, 
	        							" asked = ? ", new String[] {"0"},
	        							null, null, null, null); 
	        
	        if (mCursor == null)	return retList;
	        mCursor.moveToFirst();
	        
	        while(! mCursor.isAfterLast() )
	        {
	        	Long lng = mCursor.getLong(0);
	        	
	            retList.add(lng);
	            mCursor.moveToNext();
	        }
	        
	        mCursor.close();
    	}
    	catch (Exception ex)
    	{
    		retList.clear(); // return an empty list, this will reset all questions answered so far.
    	}
    	
        return retList;
    } 
    

    /* 
     * Return a Cursor positioned at the car that matches the given rowId 
     * @param rowId 
     * @return Cursor positioned to matching car, if found 
     * @throws SQLException if car could not be found/retrieved 
     */ 

    
    public OneQandA get (long ID) throws SQLException 
    { 
    	OneQandA qa;
    	
    	try
    	{
    	 
	        Cursor mCursor = 
	        this.mDb.query (DATABASE_TABLE, new String[] { "_id", QUESTION, PREFIX, ANSWER, SUFFIX}, 
	        							"_id = ? " , new String[] {String.valueOf(ID)}
	        							, null, null, null, null); 
	        if (mCursor == null)	return null;
	
	        mCursor.moveToFirst(); 
	        
	        if (mCursor.isAfterLast())	{ mCursor.close(); return null;}
	        
	        qa = new OneQandA(mCursor.getLong(0)
	        		,mCursor.getString(1)
	        		,mCursor.getString(2)
	        		,mCursor.getString(3)
	        		,mCursor.getString(4));
	        
	    	mCursor.close();
    	}
    	catch (Exception ex)
    	{
	        qa = new OneQandA(0
	        		,"How many pyramids are next to Khufu's?"
	        		,""
	        		,"5"
	        		,"");

    	}
    	
    	return qa;
    }

    public boolean setAsked (long id) { 
        
    	try
    	{
	    	ContentValues args = new ContentValues(); 
	        args.put(ASKED, 1); 
	 
	        
	        int ret_val = this.mDb.update(DATABASE_TABLE, args, "_id = " + String.valueOf(id), null);
	    	return ret_val > 0;
    	}
    	catch (Exception ex)
    	{
    		// set it again later.
    	}
    	
    	return false;
    }

    public boolean clearAsked () { 
    	try
    	{
	        ContentValues args = new ContentValues(); 
	        args.put(ASKED, 0); 
	 
	        int ret_val = this.mDb.update(DATABASE_TABLE, args, null, null);
	        return ret_val > 0;
    	}
    	catch (Exception ex)
    	{ 
    		// clear them later 
    	}
    	
    	return false;
    }
    
    /*

    public OneQandA getWithDefault (MO_OPER_TYPE moOperType) 
    { 
    	 
    	DBMoOper oper = this.get (moOperType);
    	if (oper == null)
    	{
    		oper = new DBMoOper (moOperType, LEVEL.One);
    		oper = this.create(oper);
    	}
    	
         
        return oper;
    } 
*/
    
    /** 
     * Update the Mo
     *  
     * @param rowId 
     * @param name 
     * @param model 
     * @param year 
     * @return true if the note was successfully updated, false otherwise 
     */ 
    
    /*
    public boolean update (OneQandA oper){ 
        ContentValues args = new ContentValues(); 
        args.put(NAME, oper.MoOper.name()); 
        args.put(MAX_LEVEL, oper.MaxLevel.name()); 
 
        int ret_val = this.mDb.update(DATABASE_TABLE, args, "rowid = " + oper.rowid, null);
        return ret_val > 0;  
    } 
 
*/
}
