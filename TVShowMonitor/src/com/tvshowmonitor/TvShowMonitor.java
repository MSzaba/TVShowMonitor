package com.tvshowmonitor;

import java.io.File;
import java.util.List;

public class TvShowMonitor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = null;
		try {
			String fileName = getFileNameFRomParameter(args);
			file = openFile(fileName);
		} catch (Exception e) {
			// TODO: handle exception
		}
		List<String> urlList = getURLList(file);
		
		for (String filmURL : urlList) {
			String whenItWillBeplayed = whenItWillBeplayed(filmURL);
			if (whenItWillBeplayed != null) {
				System.out.println(whenItWillBeplayed);
			}
		}
	}

	private static String whenItWillBeplayed(String filmURL) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<String> getURLList(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	private static File openFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getFileNameFRomParameter(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
