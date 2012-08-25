package com.pushsignal.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpResponse {
	private int statusCode;
	private List<String> responseLines;
	
	public HttpResponse(int statusCode, List<String> responseLines) {
		this.statusCode = statusCode;
		this.responseLines = responseLines;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public List<String> getResponseLines() {
		return responseLines;
	}
	
	public Map<String, String> getResponseAsKeyValuePairs() {
		Map<String, String> result = new HashMap<String, String>();
		for (String line : responseLines) {
			String keyValue[] = line.split("=");
			result.put(keyValue[0], keyValue[1]);
		}
		return result;
	}
}
