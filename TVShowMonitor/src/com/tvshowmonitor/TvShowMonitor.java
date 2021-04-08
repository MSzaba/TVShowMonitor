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

	private static String whenItWillBeplayed(URL filmURL) throws Exception {
		
		String hostname = filmURL.getHost();
		URLProcessor processor = URLProcessorFaxtory.getProcessorByHomepage(hostname);
		if (processor == null) {
			throw new Exception("Hostname " + hostname + " is a unknown");
		}
		return processor.getPlayTimes(filmURL);
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
		return urlList;
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
			printHelp();
			System.exit(0);
		}
		String fileName = args[0];
		
		return fileName;
	}

	private static void printHelp() {
		System.out.println("Tv Show monitor 1.0");
		System.out.println("--------------------");
		System.out.println("Usage: call the app with a file as parameter where links of the different TV programs are listed.");
		System.out.println("Lines started with " + COMMENT + " count as comment");
		System.out.println();
		System.out.println("Supported platforms:");
		System.out.println("-port.hu");
		
	}

}
