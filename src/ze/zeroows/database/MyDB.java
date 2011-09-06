package ze.zeroows.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class MyDB extends MyDBhelper{
	// To use in Log
	private static final String TAG = "MyDB.class";
	
	
	private SQLiteDatabase db;
	private final Context context;
	private final MyDBhelper dbhelper;
	
	public MyDB(Context c){
		super(c, DATABASE_NAME, null, DATABASE_VERSION); // Check this >>><<<<
		context = c;
		dbhelper = new MyDBhelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void close()
	{
		db.close();
	}
	public void open() throws SQLiteException
	{
		try{
			db = dbhelper.getWritableDatabase();
		}catch(SQLiteException ex)
		{
			Log.v(TAG, "open exception caught: "+ ex.getMessage());
			db = dbhelper.getReadableDatabase();
		}
	}
	public long insertConfig(String key, String value)
	{
		try{

			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(CONFIG_TABLE_KEY_ROW, key);
			newTaskValue.put(CONFIG_TABLE_VALUE_ROW, value);
//			newTaskValue.put(Constants.DATE_NAME, java.lang.System.currentTimeMillis());
			return db.insert(CONFIG_TABLE_NAME, null, newTaskValue);
			
		}catch(SQLiteException ex)
		{
			Log.v(TAG, "open exception caught: "+ ex.getMessage());
			return -1;
		}	
	}
	public Cursor getConfig()
	{
		Cursor c =  db.query(CONFIG_TABLE_NAME, null, null, null, null, null, null);
		//c.deactivate();
		return c;
	}
}
