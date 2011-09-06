package ze.zeroows.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class MyDBhelper extends SQLiteOpenHelper {

	// To use in Log
	private static final String TAG = "MyDBhelper.class";

	// Database Name and version
	protected static final String DATABASE_NAME = "mraselNet";
	protected static final int DATABASE_VERSION = 1;
	
	// Tables name
	protected static final String CONFIG_TABLE_NAME = "config";
	protected static final String PHONES_TABLE_NAME = "phones";

	protected static final String ID_ROW = "id";

	// Config Table Rows
	protected static final String CONFIG_TABLE_KEY_ROW = "key";
	protected static final String CONFIG_TABLE_VALUE_ROW = "value";

	// Phone Table Rows
	protected static final String PHONES_TABLE_PHONE_NUMBER_ROW = "PhoneNumber";
	protected static final String PHONES_TABLE_NAME_ROW = "Name";
	// private static final String PHONES_TABLE_NAME_ROW = "Name";

	private static final String CONFIG_TABLE_CREATE = "CREATE TABLE "
			+ CONFIG_TABLE_NAME + " (" + ID_ROW
			+ " INTEGER PRIMARY KEY ASC, " + CONFIG_TABLE_KEY_ROW + " TEXT NOT NULL, "
			+ CONFIG_TABLE_VALUE_ROW + " TEXT NOT NULL);";

	private static final String PHONES_TABLE_CREATE = "CREATE TABLE "
			+ PHONES_TABLE_NAME + " (" + ID_ROW
			+ " INTEGER PRIMARY KEY ASC, " + PHONES_TABLE_PHONE_NUMBER_ROW
			+ " TEXT NOT NULL, " + PHONES_TABLE_NAME_ROW + " TEXT NOT NULL);";

	public MyDBhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v(TAG,"open onCreate: Creating all the tables"); // Showing a message in the log..
		try {
			db.execSQL(CONFIG_TABLE_CREATE); // executing SQL to create tables..
			db.execSQL(PHONES_TABLE_CREATE); // executing SQL to create tables..
		} catch (SQLiteException ex) {
			Log.v(TAG, "open exception caught: " + ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(TAG, "Upgrading from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + CONFIG_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PHONES_TABLE_NAME);
		onCreate(db);
	}

}
