package com.mult.thread;

import java.net.URL;
import java.net.URLConnection;

public class ConnectionUtil {
    public static void connect() throws Exception {
		final String urlStr="http://localhost:8080/order/createSeckillOrder/1";
		URL url=new URL(urlStr);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.connect();
		urlConnection.getInputStream();
	}
}
