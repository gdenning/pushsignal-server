package com.pushsignal.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class HttpExecutor {
	public static HttpResponse executePost(final String targetURL,
			final Map<String, String> requestProperties, 
			final Map<String, String> params) throws IOException  {
		StringBuilder urlParameters = new StringBuilder();
		for (String key : params.keySet()) {
			if (urlParameters.length() > 0) {
				urlParameters.append("&");
			}
			try {
				urlParameters.append(key + "=" + URLEncoder.encode(params.get(key), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// Do nothing
			}
		}
		return executePost(targetURL, requestProperties, urlParameters.toString());
	}

	public static HttpResponse executePost(final String targetURL,
			final Map<String, String> requestProperties, 
			final String postBody) throws IOException {
		URL url;
		HttpURLConnection connection = null;
		HttpsURLConnection.setDefaultHostnameVerifier(new FakeHostnameVerifier()); 
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(postBody.getBytes().length));
			if (requestProperties != null) {
				for (String key : requestProperties.keySet()) {
					connection.setRequestProperty(key, requestProperties.get(key));
				}
			}
			
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(postBody);
			wr.flush();
			wr.close();

			int responseCode = connection.getResponseCode();
	        
	        // Get Response
			List<String> responseLines = new ArrayList<String>();
			// HttpURLConnection class getInputStream() method throws IOException on HTTP status codes >=400
			if (responseCode < 400) {
				InputStream is = connection.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is));
				String line;
				while ((line = rd.readLine()) != null) {
					responseLines.add(line);
				}
				rd.close();
			}

	        return new HttpResponse(responseCode, responseLines);

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static class FakeHostnameVerifier implements HostnameVerifier { 
	    public boolean verify(String hostname, SSLSession session) { 
	        return true; 
	    } 
	} 
}
