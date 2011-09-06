package net.mrasel.zeroows;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

// import the database package
import ze.zeroows.database.MyDB;

public class MraselNetActivity extends Activity {
    /** Called when the activity is first created. */
	// 
	private static final String TAG = "MraselMainActivity";
	// init. database adopter
	MyDB dba;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dba = new MyDB(this);
        
//        Log.v(TAG, "Opening the DB.");
//        dba.open();        
//        long newRowId = dba.insertConfig("username", "zeroows");
//        Log.v(TAG, "Inserting some values in Config table (row id):" + newRowId);        
//        Log.v(TAG, "Closing the DB.");
//        dba.close();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.loginInfo:
        	Toast.makeText(MraselNetActivity.this, "Selected!!", Toast.LENGTH_SHORT).show();
        	return true;
        }
        return super.onContextItemSelected(item);
    }
}