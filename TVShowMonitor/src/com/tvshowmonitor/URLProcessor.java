package com.tvshowmonitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class URLProcessor {
	
	private static final String UTF8 = "UTF-8";

	abstract String getPlayTimes(URL filmURL) throws IOException;
	
	protected String getPageContent(URL filmURL) throws IOException {
		URLConnection conn = filmURL.openConnection();
		InputStream in = conn.getInputStream();
		String encoding = conn.getContentEncoding();
		encoding = encoding == null ? UTF8 : encoding;
		String body = readContent(in, encoding);
		return body;
	}
	
	private String readContent(InputStream in, String encoding) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int len =  0;
		while ((len = in.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		return new String(baos.toByteArray(), encoding);
	}

}
