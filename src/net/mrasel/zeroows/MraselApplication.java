package net.mrasel.zeroows;

import net.mrasel.api.Mrasel;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class MraselApplication extends Application implements
		OnSharedPreferenceChangeListener {

	private static final String TAG = MraselApplication.class.getSimpleName();
	public Mrasel mrasel;
	public MraselBD mraselBD;
	private SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.prefs.registerOnSharedPreferenceChangeListener(this);
		this.mraselBD = new MraselBD(this);
		Log.i(TAG, "Mrasel Application started");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		this.mrasel = null;
	}

	public String getUsername() {
		return prefs.getString("username", null);
	}

	public String getPassword() {
		return prefs.getString("password", null);
	}
	
	/** 
	 * To set the credit of the user in preference 
	 * @param credit
	 */
	public void setCredit(String credit) {
		Editor editor = prefs.edit();
		editor.putString("credit", credit);
		editor.commit();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		this.mraselBD.close();
		Log.i(TAG, "Mrasel Application terminated");
	}
}
