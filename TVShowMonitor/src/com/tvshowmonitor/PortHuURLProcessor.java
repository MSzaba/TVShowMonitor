package com.tvshowmonitor;

import java.io.IOException;
import java.net.URL;

public class PortHuURLProcessor extends URLProcessor {

	@Override
	public String getPlayTimes(URL filmURL) throws IOException {
		String body = getPageContent(filmURL);
		System.out.println(body);
		return null;
	}

}
