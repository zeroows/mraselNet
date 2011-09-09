/**
 * 
 */
package net.mrasel.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * @author zeroows
 * 
 */
public class Mrasel {
	// site links
	private final String siteLink = "http://www.mrasel.net/api‬‬";
	private final String msgStatusPage = "sendstatus.php‬‬";
	private final String creditPage = "credit.php";
	private final String sendPage = "";

	// API varibles
	private final String apiUsername = "‬‬username";
	private final String apiPassword = "‬‬password";
	private final String apiMobile = "mobile";
	private final String apiMessage = "message";
	private final String apiSender = "sender";
	private final String apiMassageID = "mid";

	// class var.
	private final String TAG = "MraselClass";
	private String username = null;
	private String password = null;
	private boolean isConnected = false;
	private String siteResult;
	private String userCredit;
	private String siteCreditUrl;

	/*
	 * To init the mrasel class
	 * 
	 * @param username site username
	 * 
	 * @param password site password
	 */
	public Mrasel(String username, String password) throws IOException {
		this.username = username;
		this.password = password;
		Log.i(TAG, "insid. class cons. "+ siteCreditUrl);

//		URL mraselUrl = new URL(siteCreditUrl);
//		BufferedReader in = new BufferedReader(new InputStreamReader(mraselUrl
//				.openStream()));
//		String inputLine = in.readLine();
//		this.siteResult = inputLine;
//		in.close();
//
//		Log.i(TAG, inputLine);
	}

	public String getSiteCreditUrl() {
		return siteCreditUrl;
	}

	public String getSiteCredit() throws MalformedURLException, IOException {
		String urlLink = "http://www.mrasel.net/api/credit.php?username="+username+"&password="+password;
		
		String strLine = "-1";

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlLink);
		HttpResponse HttpResponse = httpclient.execute(httpGet);
		HttpEntity entity = HttpResponse.getEntity();
		int ResponseCode = HttpResponse.getStatusLine().getStatusCode();
		
		if (entity != null) {
			strLine = EntityUtils.toString(entity);
		}
		Log.i(TAG, "HttpResponseCode is:" + ResponseCode);
		
		HttpResponse = null;
		httpclient = null;
		httpGet = null;
		entity = null;
		
		this.userCredit = strLine;
		Log.i(TAG, "User Credit is "+userCredit+" point");
		return userCredit;
	}

	public String getSiteResult() {
		return this.siteResult;
	}
}
