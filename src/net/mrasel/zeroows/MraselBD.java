package net.mrasel.zeroows;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MraselBD {

	// To use in Log
	private static final String TAG = MraselBD.class.getSimpleName();;

	// Database Name and version
	protected static final String DATABASE = "mraselNet.db";
	protected static final int DATABASE_VERSION = 1;

	// Tables name
//	protected static final String CONFIG_TABLE_NAME = "config";
	protected static final String PHONES_TABLE_NAME = "phones";

	protected static final String ID_ROW = "_id";

	// Config Table Rows
//	protected static final String CONFIG_TABLE_KEY_ROW = "key";
//	protected static final String CONFIG_TABLE_VALUE_ROW = "value";

	// Phone Table Rows
	protected static final String PHONES_TABLE_PHONE_NUMBER_ROW = "PhoneNumber";
	protected static final String PHONES_TABLE_NAME_ROW = "Name";
	// private static final String PHONES_TABLE_NAME_ROW = "Name";

//	private static final String CONFIG_TABLE_CREATE = "CREATE TABLE "
//			+ CONFIG_TABLE_NAME + " (" + ID_ROW + " INTEGER PRIMARY KEY ASC, "
//			+ CONFIG_TABLE_KEY_ROW + " TEXT NOT NULL, "
//			+ CONFIG_TABLE_VALUE_ROW + " TEXT NOT NULL);";

	private static final String PHONES_TABLE_CREATE = "CREATE TABLE "
			+ PHONES_TABLE_NAME + " (" + ID_ROW + " INTEGER PRIMARY KEY ASC, "
			+ PHONES_TABLE_PHONE_NUMBER_ROW + " TEXT NOT NULL, "
			+ PHONES_TABLE_NAME_ROW + " TEXT NOT NULL);";

	// DbHelper implementations
	class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG, "open onCreate: Creating all the tables"); // Showing a
																	// message
																	// in the
																	// log..
			try {
//				db.execSQL(CONFIG_TABLE_CREATE); // executing SQL to create
//													// tables..
				db.execSQL(PHONES_TABLE_CREATE); // executing SQL to create
													// tables..
			} catch (SQLiteException ex) {
				Log.v(TAG, "open exception caught: " + ex.getMessage());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
//			db.execSQL("DROP TABLE IF EXISTS " + CONFIG_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + PHONES_TABLE_NAME);
			onCreate(db);
		}
	}

	final DbHelper dbHelper;

	public MraselBD(Context context) {
		this.dbHelper = new DbHelper(context);
		Log.d(TAG, "Initialized data");
	}

	public void close() {
		this.dbHelper.close();
	}

	public void insertOrIgnore(ContentValues values) {
		Log.d(TAG, "insertOrIgnore on " + values);
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
		try {
			db.insertWithOnConflict(PHONES_TABLE_NAME, null, values,
					SQLiteDatabase.CONFLICT_IGNORE);
		} finally {
			db.close();
		}
	}

	/**
	 * 
	 * @return Cursor where the columns are going to be id, created_at, user,
	 *         txt
	 */
	public Cursor getStatusUpdates() {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		return db.query(PHONES_TABLE_NAME, null, null, null, null, null, null);
	}

	public long getLatestStatusCreatedAtTime() {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		try {
			Cursor cursor = db.query(PHONES_TABLE_NAME, null, null, null,
					null, null, null);
			try {
				return cursor.moveToNext() ? cursor.getLong(0) : Long.MIN_VALUE;
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
	}

	public String getStatusTextById(long id) {
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		try {
			Cursor cursor = db.query(PHONES_TABLE_NAME, null, ID_ROW + "=" + id,
					null, null, null, null);
			try {
				return cursor.moveToNext() ? cursor.getString(0) : null;
			} finally {
				cursor.close();
			}
		} finally {
			db.close();
		}
	}

	/**
	 * Deletes ALL the data
	 */
	public void delete() {
		// Open Database
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Delete the data
		db.delete(PHONES_TABLE_NAME, null, null);

		// Close Database
		db.close();
	}
}
