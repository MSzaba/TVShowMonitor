package com.tvshowmonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TvShowMonitor {
	
	private static final int STOPPED_DUE_INVALID_INPUT_PARAMETERS = -1;
	private static final String COMMENT = "#";

	public static void main(String[] args) {

		File file = null;
		try {
			String fileName = getFileNameFromParameter(args);
			System.out.println("Processing file: " + fileName);
			file = openFile(fileName);
			List<URL> urlList = getURLList(file);
			System.out.println("Number of valid URLs: " + urlList.size());
			for (URL filmURL : urlList) {
				String whenItWillBeplayed = whenItWillBeplayed(filmURL);
				if (whenItWillBeplayed != null) {
					System.out.println(whenItWillBeplayed);
				}
			}
			System.out.println("Bye");
		} catch (Exception e) {
			System.out.println("Error occcurred during processing: "  + e.getMessage());
			System.exit(STOPPED_DUE_INVALID_INPUT_PARAMETERS);
		}
		
	}

	private static String whenItWillBeplayed(URL filmURL) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<URL> getURLList(File file) throws IOException {
		List<URL> urlList =  new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        if (line.startsWith(COMMENT)) {
		        	//no need to process comment lines
					continue;
				}
		        URL url = null;
		        try {
					url = new URL(line);
				} catch (MalformedURLException e) {
					System.out.println("Skipping malformed URL: " + line);
					continue;
				}
		        urlList.add(url);
		    }
		} catch (IOException x) {
			//process the exceptions in the main function
		    throw x;
		}
		return null;
	}

	private static File openFile(String fileName) throws Exception {
		File f = new File(fileName);
		if(!f.exists()) { 
			throw new Exception("File " + fileName + " does not exists");
		}
		if(f.isDirectory()) { 
			throw new Exception("File " + fileName + " is a directory");
		}
		return f;
	}

	private static String getFileNameFromParameter(String[] args) throws Exception {
		if (args == null || args.length == 0) {
			throw new Exception("Missing input parameter");
		}
		String fileName = args[0];
		
		return fileName;
	}

}
