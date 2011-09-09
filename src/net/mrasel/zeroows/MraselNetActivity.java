package net.mrasel.zeroows;

import java.io.UnsupportedEncodingException;

import net.mrasel.api.Mrasel;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MraselNetActivity extends Activity {
	MraselApplication mraselApp;
	// Log Tag
	private static final String TAG = MraselNetActivity.class.getSimpleName();
	// view
	EditText editText;

	// Mrasel
	Mrasel mrasel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mraselApp = (MraselApplication) getApplication();
		Log.d(TAG, "MraselNetActivity Started");
		

		editText = (EditText) findViewById(R.id.linkTextField);
		editText.setText("البطل");
//		String temp = "البطل";
//		try {
//			temp = new String(temp.getBytes(), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Log.d(TAG, "البطل ="+temp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemPrefs:
			startActivity(new Intent(this, PrefsActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		case R.id.loginInfo:
			String username = mraselApp.getUsername().trim();
			String password = mraselApp.getPassword().trim();
			if (username == null && password == null) {
				Toast
						.makeText(
								MraselNetActivity.this,
								"Please go to setting and enter your username and password",
								Toast.LENGTH_LONG).show();
				Log.i(TAG, "Username or password are null");
			} else {
				new ConnectMrasel().execute(username, password);
				Log.i(TAG, "Connecting is done");
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void ProcessResponse(String response) {
		if (!response.equalsIgnoreCase("error=007"))
			Toast.makeText(MraselNetActivity.this,
					" Please check your username and password",
					Toast.LENGTH_LONG).show();
		else {
			editText = (EditText) findViewById(R.id.linkTextField);
			editText.setText(response);
			Toast.makeText(MraselNetActivity.this,
							" User credit is " + response + " point",
							Toast.LENGTH_LONG).show();
		}
	}

	private class ConnectMrasel extends AsyncTask<String, Integer, String> {

		protected String doInBackground(String... siteVars) {
			try {
				mrasel = new Mrasel(siteVars[0], siteVars[1]);
				Log.i(TAG, "Calling getSiteCredit()");
				return mrasel.getSiteCredit();
			} catch (Exception e) {
				Log.e(TAG, "doInBackground Exception:" + e.getMessage());
				Log.e(TAG, "doInBackground var: username:" + siteVars[0]
						+ " pass: " + siteVars[1]);
				return "";
			}
		}

		protected void onPostExecute(String result) {
			Log.d(TAG, "Response:" + result);
			ProcessResponse(result);
		}
	}
}